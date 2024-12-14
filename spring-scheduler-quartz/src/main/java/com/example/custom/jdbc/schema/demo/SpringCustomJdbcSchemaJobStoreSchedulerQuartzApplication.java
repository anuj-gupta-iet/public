package com.example.custom.jdbc.schema.demo;

import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.quartz.CronScheduleBuilder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * in this example we are using custom schema for jobs schering
 * 
 */

@SpringBootApplication
public class SpringCustomJdbcSchemaJobStoreSchedulerQuartzApplication {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    private void init() throws SchedulerException {
	String cronExpression = "*/10 * * * * ?"; // every 10 sec
	JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("myJobName").build();
	Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTriggerName")
		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringCustomJdbcSchemaJobStoreSchedulerQuartzApplication.class, args);
    }

}

//this configuration will allow customized Scheduler which allows dynamic rescheduling of jobs 
@Configuration
class QuartzConfig {

    @Autowired
    private CustomSpringBeanJobFactory springBeanJobFactory;

    @Value("${spring.quartz.properties.org.quartz.jobStore.tablePrefix}")
    private String tablePrefix;
    @Value("${spring.quartz.properties.org.quartz.jobStore.driverDelegateClass}")
    private String driverDelegateClass;

    // this bean provides customization of this class @Autowired Scheduler
    // scheduler;
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
	SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	schedulerFactoryBean.setJobFactory(springBeanJobFactory);

	// when we configure using JDBC store in app.prop using below properties
	// spring.quartz.job-store-type=jdbc
	// quartz.jdbc.initialize-schema=never
	// still if application uses this job store Using job-store
	// 'org.quartz.simpl.RAMJobStore'
	// then do this
	Properties props = new Properties();
	props.put("org.quartz.jobStore.tablePrefix", tablePrefix);
	props.put("org.quartz.jobStore.driverDelegateClass", driverDelegateClass);
	schedulerFactoryBean.setQuartzProperties(props);
	schedulerFactoryBean.setDataSource(dataSource);

	return schedulerFactoryBean;
    }
}

@Component
class CustomSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
	this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
	Object job = super.createJobInstance(bundle);
	beanFactory.autowireBean(job); // Inject dependencies
	return job;
    }
}

@Data
class JobInfo {
    private String cronExp;
}

@DisallowConcurrentExecution
class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
	System.out.println(this.getClass().getSimpleName() + " Job started at " + new Date());
    }

}