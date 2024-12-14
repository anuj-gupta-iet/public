package com.example.dynamic.scheduling.demo;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

/**
 * in this example we are dynamically adding/deleting/rescheduling jobs without
 * server restart
 * 
 * reference:
 * https://medium.com/@2015-2-60-004/dynamic-scheduling-in-spring-boot-with-quartz-a-practical-approach-6c6408a99b39
 */

@SpringBootApplication
@RestController
public class SpringDynamicSchedulerQuartzApplication {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/updateJob")
    private void updateJob(@RequestBody JobInfo jobInfo) throws SchedulerException {
	schedulerService.reSchedulerJob(jobInfo.getCronExp());
    }

    @DeleteMapping("/deleteJob")
    private void deleteJob() throws SchedulerException {
	schedulerService.deleteJob();
    }

    @GetMapping("/scheduleNewJob")
    private void scheduleNewJob() throws SchedulerException {
	schedulerService.scheduleNewJob();
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringDynamicSchedulerQuartzApplication.class, args);
    }

}

@Service
class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    // job will start by default with this cron exp
    @PostConstruct
    private void init() throws SchedulerException {
	String cronExpression = "*/10 * * * * ?"; // every 10 sec
	JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("myJobName").build();
	Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTriggerName")
		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	scheduler.scheduleJob(jobDetail, trigger);
    }

    public void scheduleNewJob() throws SchedulerException {
	if (isJobAlreadyPresent()) {
	    System.out.println("Job Already Present");
	    return;
	}
	String cronExpression = "*/10 * * * * ?"; // every 10 sec
	JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("myJobName").build();
	Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTriggerName")
		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	scheduler.scheduleJob(jobDetail, trigger);
    }

    private boolean isJobAlreadyPresent() throws SchedulerException {
	JobKey jobKey = JobKey.jobKey("myJobName");
	return scheduler.checkExists(jobKey);
    }

    public void deleteJob() throws SchedulerException {
	JobKey jobKey = JobKey.jobKey("myJobName");
	scheduler.deleteJob(jobKey);
    }

    public void reSchedulerJob(String cronExpression) throws SchedulerException {
	TriggerKey triggerKey = TriggerKey.triggerKey("myTriggerName");
	Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity("myTriggerName")
		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	scheduler.rescheduleJob(triggerKey, newTrigger);
    }
}

//this configuration will allow customized Scheduler which allows dynamic rescheduling of jobs 
@Configuration
class QuartzConfig {

    @Autowired
    private CustomSpringBeanJobFactory springBeanJobFactory;

    // this bean provides customization of this class @Autowired Scheduler
    // scheduler;
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
	SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	schedulerFactoryBean.setJobFactory(springBeanJobFactory);
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