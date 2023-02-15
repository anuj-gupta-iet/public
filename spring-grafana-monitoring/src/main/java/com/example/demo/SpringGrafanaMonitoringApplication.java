package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringGrafanaMonitoringApplication {

	@GetMapping("/message")
	public String getMessage() {
		return "My Message";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringGrafanaMonitoringApplication.class, args);
	}

}
