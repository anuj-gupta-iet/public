package com.example.demo;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/order")
public class SpringOrderMicroserviceApplication {

	@Autowired
	private Tracer tracer;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${microservice.payment-service.endpoints.processPayment.uri}")
	private String processPaymentEndpoint;

	@GetMapping("/bookOrder")
	public String bookOrder(@RequestParam(required = false) String paymentServiceName) {
		// this traceid can be stored in DB for future tracing
		String traceId = tracer.currentSpan().toString();
		System.out.println("Trace Id: " + traceId);
		Random random = new Random();
		int orderId = random.nextInt(100);
		String response = null;
		if (paymentServiceName != null) {
			response = restTemplate.getForObject(processPaymentEndpoint + "?paymentServiceName=" + paymentServiceName,
					String.class);
		} else {
			response = restTemplate.getForObject(processPaymentEndpoint, String.class);
		}
		return "Order Booked with Id: " + orderId + ". " + response + ". Trace id for debugging: " + traceId;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringOrderMicroserviceApplication.class, args);
	}

}
