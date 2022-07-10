package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twilio.Twilio;

@SpringBootApplication
public class SpringSmsApplication {

	@Value("${twilio.account.sid}")
	private String username;
	
	@Value("${twilio.auth.token}")
	private String password;

	@PostConstruct
	public void initTwilio() {
		Twilio.init(username, password);
		System.out.println("Twilio Initialized...");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSmsApplication.class, args);
	}

}
