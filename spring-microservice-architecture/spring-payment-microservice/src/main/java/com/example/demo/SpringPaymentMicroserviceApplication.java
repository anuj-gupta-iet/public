package com.example.demo;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/payment")
public class SpringPaymentMicroserviceApplication {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/processPayment")
	public String processPayment(@RequestParam(required = false) String paymentServiceName) {
		System.out.println("paymentServiceName: " + paymentServiceName);
		if (paymentServiceName == null) {
			Random random = new Random();
			int paymentId = random.nextInt(1000);
			return "Payment Processed with Id: " + paymentId;
		} else {
			if ("gpay".equals(paymentServiceName)) {
				String response = restTemplate.getForObject("http://GPAY-SERVICE/gpay/pay", String.class);
				return response;
			} else if ("paypal".equals(paymentServiceName)) {
				String response = restTemplate.getForObject("http://PAYPAL-SERVICE/paypal/pay", String.class);
				return response;
			} else {
				return paymentServiceName + " is not a valid name";
			}
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringPaymentMicroserviceApplication.class, args);
	}

}
