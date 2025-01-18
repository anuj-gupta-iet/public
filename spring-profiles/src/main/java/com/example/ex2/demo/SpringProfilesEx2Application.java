package com.example.ex2.demo;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
 * whichever profile you set in application.yml
 * corresponding Service Bean got called
 */

@SpringBootApplication
public class SpringProfilesEx2Application {

    public static void main(String[] args) {
	SpringApplication.run(SpringProfilesEx2Application.class, args);
    }

}

@Service
@Profile("profileOne")
class MyServiceProfileOne {
    @PostConstruct
    private void init() {
	System.out.println("MyService Profile One");
    }
}

@Service
@Profile("profileTwo")
class MyServiceProfileTwo {
    @PostConstruct
    private void init() {
	System.out.println("MyService Profile Two");
    }
}