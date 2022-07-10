package com.example.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	@GetMapping("/loadMantasUsers")
	public BatchStatus loadMantasUsers() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> parameters = new HashMap<>();
		parameters.put("time", new JobParameter(new Date()));
		JobParameters jobParameters = new JobParameters(parameters);
		
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		
		while(jobExecution.isRunning()) {
			System.out.println("Job Still running...");
		}
		
		return jobExecution.getStatus();
	}
	
	@GetMapping("/loadUsers")
	public BatchStatus loadUsers() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> parameters = new HashMap<>();
		parameters.put("time", new JobParameter(new Date()));
		JobParameters jobParameters = new JobParameters(parameters);
		
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		
		while(jobExecution.isRunning()) {
			System.out.println("Job Still running...");
		}
		
		return jobExecution.getStatus();
	}
}
