package com.example.demo;

import java.util.Date;

import javax.annotation.PostConstruct;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * in spring scheduler project we can simply use @Scheduled annotation (which
 * comes with spring-boot-starter) to schedule Particular method. the major
 * downside of this approach is jobs are not persistent (it means if 2 instances
 * are running then both instances will run jobs parallelly, also it means if
 * server is down during the time of execution of that job then that turn of job
 * is going to be missed)
 */

@SpringBootApplication
public class SpringSchedulerQuartzApplication {

	@Autowired
	private Scheduler scheduler;

	@PostConstruct
	private void init() throws SchedulerException {
		String cronExpression = "*/10 * * * * ?"; // every 10 sec
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSchedulerQuartzApplication.class, args);
	}

}

@DisallowConcurrentExecution
class SimpleJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println(this.getClass().getSimpleName() + " Job started at "
				+ new Date());
	}

}