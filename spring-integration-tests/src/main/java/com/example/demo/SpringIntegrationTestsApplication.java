package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIntegrationTestsApplication {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void init() {
		List<UserModel> users = Arrays.asList(
			new UserModel("anujgupta", "IT", 52000));
		repository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationTestsApplication.class, args);
	}

}
