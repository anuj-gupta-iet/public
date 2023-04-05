package com.example.demo;

import javax.annotation.PostConstruct;

import org.example.soap.api.loaneligibility.Acknowledgement;
import org.example.soap.api.loaneligibility.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@SpringBootApplication
public class SpringSoapClientApplication {

	@Autowired
	private WebServiceTemplate webServiceTemplate; // just like RestTemplate used for consuming REST api

	@PostConstruct
	private void init() {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setAge(12);
		customerRequest.setCustomerName("Anuj");
		customerRequest.setEmploymentMode("private");
		customerRequest.setCibilScore(130);
		customerRequest.setYearlyIncome(130000);

		Acknowledgement acknowledgement = (Acknowledgement) webServiceTemplate
				.marshalSendAndReceive("http://localhost:8080/ws", customerRequest);

		acknowledgement.getCriteriaMismatch().forEach(System.out::println);

		System.out.println(acknowledgement.getApprovedAmount());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSoapClientApplication.class, args);
	}

}

@Configuration
class SoapClientConfig {

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("org.example.soap.api.loaneligibility");
		return marshaller;
	}
	
	@Bean
	public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller) {
		return new WebServiceTemplate(jaxb2Marshaller);
	}

}