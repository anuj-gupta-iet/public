package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringKafkaApplication {

	
	public static final String stringKafkaTopicName = "spring-boot-topic-string";
	public static final String rawObjectKafkaTopicName = "spring-boot-topic-raw-object";

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}

}
