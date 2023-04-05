package com.example.demo;

import java.util.List;

import org.example.soap.api.loaneligibility.Acknowledgement;
import org.example.soap.api.loaneligibility.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@SpringBootApplication
@Endpoint
public class SpringSoapServerApplication {

	public static final String NAMESPACE = "http://www.example.org/soap/api/loanEligibility";
	@Autowired
	private LoanEligibilityService loanEligibilityService;

	@PayloadRoot(namespace = NAMESPACE, localPart = "CustomerRequest")
	@ResponsePayload
	public Acknowledgement getLoanStatus(@RequestPayload CustomerRequest request) {
		return loanEligibilityService.checkLoanEligibility(request);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringSoapServerApplication.class, args);
	}

}

@Service
class LoanEligibilityService {

	public Acknowledgement checkLoanEligibility(CustomerRequest request) {
		Acknowledgement acknowledgement = new Acknowledgement();
		
		List<String> mismatchCriteriaList = acknowledgement.getCriteriaMismatch();

		if (request.getAge() < 30 || request.getAge() > 60) {
			mismatchCriteriaList.add("Person age should in between 30 to 60");
		}
		
		if (request.getYearlyIncome() < 200000) {
			mismatchCriteriaList.add("Person Minimum income should be more than 200000");
		}
		
		if (request.getCibilScore() < 500) {
			mismatchCriteriaList.add("Low CIBIL Score please try after 6 month");
		}

		if (mismatchCriteriaList.size() > 0) {
			acknowledgement.setApprovedAmount(0);
			acknowledgement.setIsEligible(false);
		} else {
			acknowledgement.setApprovedAmount(500000);
			acknowledgement.setIsEligible(true);
		}
		
		return acknowledgement;
	}

}

@Configuration
@EnableWs
class SoapServerConfig {

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/ws/*");
	}

	@Bean(name = "loanEligibility") // this name will be used to see wsdl file http://localhost:8080/ws/loanEligibility.wsdl
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("LoanEligibilityindicator");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace(SpringSoapServerApplication.NAMESPACE);
		defaultWsdl11Definition.setSchema(schema);
		return defaultWsdl11Definition;
	}

	@Bean
	public XsdSchema schema() {
		return new SimpleXsdSchema(new ClassPathResource("loan_eligibility.xsd"));
	}

}