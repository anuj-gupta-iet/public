package com.example.newexample.two.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

/*
 * 
 * in this example, there are 2 steps
 * first step assign roll number to a student (runs in batch of 5)
 * and second step assign class A, B, C, D to each student (runs in batch of 10)
 * 
 */

@SpringBootApplication
public class SpringBatchNewExampleTwoApplication {

    public static void main(String[] args) {
	SpringApplication.run(SpringBatchNewExampleTwoApplication.class, args);
    }

}

@Component
class RollNumberStepItemReader implements ItemReader<String> {

    @Autowired
    private SharedQueue sharedQueue;

    @Override
    public String read() throws Exception {
	String name = sharedQueue.getNameQueue().poll();
	Thread.sleep(1000);
	return name;
    }

}

@Component
class RollNumberStepItemProcessor implements ItemProcessor<String, Student> {

    @Override
    public Student process(String name) throws Exception {
	Integer rollNumber = Math.abs(UUID.randomUUID().hashCode());
	Student student = Student.builder().name(name).rollNumber(rollNumber).build();
	return student;
    }

}

@Component
class RollNumberStepItemWriter implements ItemWriter<Student> {

    @Autowired
    private SharedQueue sharedQueue;

    @Override
    public void write(List<? extends Student> items) throws Exception {
	System.out.println(getClass().getSimpleName() + ": Chunk Started ======");
	items.stream().forEach(System.out::println);
	System.out.println(getClass().getSimpleName() + ": Chunk Finished======");
	sharedQueue.getStudentQueue().addAll(items);
    }

}

@Component
class ClassStepItemReader implements ItemReader<Student> {

    @Autowired
    private SharedQueue sharedQueue;

    @Override
    public Student read() throws Exception {
	Student student = sharedQueue.getStudentQueue().poll();
	Thread.sleep(1000);
	return student;
    }

}

@Component
class ClassStepItemProcessor implements ItemProcessor<Student, Student> {

    private Random random = new Random();

    @Override
    public Student process(Student student) throws Exception {
	Integer r = random.nextInt(5) + 1;
	ClassName className;
	if (r.equals(1)) {
	    className = ClassName.A;
	} else if (r.equals(2)) {
	    className = ClassName.B;
	} else if (r.equals(3)) {
	    className = ClassName.C;
	} else {
	    className = ClassName.D;
	}
	student.setClassName(className);
	return student;
    }

}

@Component
class ClassStepItemWriter implements ItemWriter<Student> {

    @Override
    public void write(List<? extends Student> items) throws Exception {
	System.out.println(getClass().getSimpleName() + ": Chunk Started ======");
	items.stream().forEach(System.out::println);
	System.out.println(getClass().getSimpleName() + ": Chunk Finished======");
    }

}

@Configuration
@EnableBatchProcessing
class SpringBatchConfig {

    @Bean
    public Step rollNumberAssignStep(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
	    RollNumberStepItemReader reader, RollNumberStepItemProcessor processor, RollNumberStepItemWriter writer) {
	Step step = stepBuilderFactory.get("ROLL-NUMBER-ASSIGN-STEP").<String, Student>chunk(5).reader(reader)
		.processor(processor).writer(writer).build();
	return step;
    }

    @Bean
    public Step classAssignStep(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
	    ClassStepItemReader reader, ClassStepItemProcessor processor, ClassStepItemWriter writer) {
	Step step = stepBuilderFactory.get("CLASS-ASSIGN-STEP").<Student, Student>chunk(10).reader(reader)
		.processor(processor).writer(writer).build();
	return step;
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, @Qualifier("rollNumberAssignStep") Step rollNumberAssignStep,
	    @Qualifier("classAssignStep") Step classAssignStep) {
	return jobBuilderFactory.get("MY-JOB").start(rollNumberAssignStep).next(classAssignStep).build();
    }

}

@Data
@Builder
class Student {
    private Integer rollNumber;
    private String name;
    private ClassName className;
}

enum ClassName {
    A, B, C, D
}

@Component
@Data
class SharedQueue {
    private ConcurrentLinkedQueue<String> nameQueue = new ConcurrentLinkedQueue<>(
	    Arrays.asList("barjraj", "ramdin verma", "sharat chandran", "birender mandal", "amit", "kushal", "kasid",
		    "shiv prakash", "vikram singh", "sanjay", "abhi", "ram dutt gupta", "khadak singh", "gurmit singh",
		    "chanderpal", "aman", "khursid", "rajeev", "durgesh", "nahar singh"));
    private ConcurrentLinkedQueue<Student> studentQueue = new ConcurrentLinkedQueue<>();
}