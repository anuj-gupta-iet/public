package com.example.demo;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/gpay")
public class SpringGPayMicroserviceApplication {

	@GetMapping("/pay")
	public String processPayment(@RequestParam(required = false) String paymentServiceName) {
		Random random = new Random();
		int paymentId = random.nextInt(1000);
		return "Payment Processed with Google Pay with Id: " + paymentId;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringGPayMicroserviceApplication.class, args);
	}

}
