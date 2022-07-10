package com.example.demo;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SpringSchedulerApplication {

	// Fire at 10:15 AM every Monday, Tuesday, Wednesday, Thursday and Friday
	@Scheduled(cron = "0 15 10 ? * MON-FRI")
	public void task6() {
		System.out.println(new Date());
	}

	// Fire at 10:15 AM on the last day of every month
	@Scheduled(cron = "0 15 10 L * ?")
	public void task5() {
		System.out.println(new Date());
	}

	// Fire at 10:15 PM every day
	@Scheduled(cron = "0 15 22 * * ?")
	public void task4() {
		System.out.println(new Date());
	}

	// Fire at 10:15 AM every day
	@Scheduled(cron = "0 15 10 * * ?")
	public void task3() {
		System.out.println("Task 3 " + new Date());
	}

	// Fire at 12:00 PM (noon) every day
	@Scheduled(cron = "0 0 12 * * ?")
	public void task2() {
		System.out.println(new Date());
	}

	// Fire every 2 secs
	// @Scheduled(fixedRate = 2000L)
	public void task1() {
		System.out.println("Task 1 " + new Date());
	}

	@PostConstruct
	public void init() {
		System.out.println("Spring Container Initialized");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("Spring Container Destroyed");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSchedulerApplication.class, args);
	}

}
