package com.example.newexample.demo;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

/*
 * make below property to true in order to start jobs execution as soon as server starts
 * spring.batch.job.enabled=true
 * 
 * in this example, there is a ConcurrentLinkedQueue which contains a list of names
 * and we need to assign roll numbers to those names
 * Names are picked in batches of 5 and assigned a unique rollnumber and printed in console 
 */

@SpringBootApplication
public class SpringBatchNewExampleApplication {

    public static void main(String[] args) {
	SpringApplication.run(SpringBatchNewExampleApplication.class, args);
    }

}

@Component
class MyItemReader implements ItemReader<String> {

    @Autowired
    private SharedQueue sharedQueue;

    @Override
    public String read() throws Exception {
	String name = sharedQueue.getQueue().poll();
	Thread.sleep(1000);
	return name;
    }

}

@Component
class MyItemProcessor implements ItemProcessor<String, Student> {

    @Override
    public Student process(String name) throws Exception {
	Integer rollNumber = Math.abs(UUID.randomUUID().hashCode());
	Student student = Student.builder().name(name).rollNumber(rollNumber).build();
	return student;
    }

}

@Component
class MyItemWriter implements ItemWriter<Student> {

    @Override
    public void write(List<? extends Student> items) throws Exception {
	System.out.println("=====Chunk Started======");
	items.stream().forEach(System.out::println);
	System.out.println("=====Chunk Finished======");
    }

}

@Configuration
@EnableBatchProcessing
class SpringBatchConfig {
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
	    ItemReader<String> reader, ItemProcessor<String, Student> processor, ItemWriter<Student> writer) {

	Step step = stepBuilderFactory.get("MY-STEP").<String, Student>chunk(5).reader(reader).processor(processor)
		.writer(writer).build();

	return jobBuilderFactory.get("MY-JOB").start(step).build();
    }

}

@Data
@Builder
class Student {
    private Integer rollNumber;
    private String name;
}

@Component
@Data
class SharedQueue {
    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>(
	    Arrays.asList("barjraj", "ramdin verma", "sharat chandran", "birender mandal", "amit", "kushal", "kasid",
		    "shiv prakash", "vikram singh", "sanjay", "abhi", "ram dutt gupta", "khadak singh", "gurmit singh",
		    "chanderpal", "aman", "khursid", "rajeev", "durgesh", "nahar singh", "ram kumar", "sunder paal",
		    "maansingh aswal", "rohit", "rohit", "sparsh", "santosh", "santosh", "punit khandelwal", "dinesh"));
}