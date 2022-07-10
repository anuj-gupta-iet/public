package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EmployeeConfig implements WebMvcConfigurer {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(requestInterceptor);
	}

	@PostConstruct
	public void init() {
		List<EmployeeModel> employees = Arrays.asList(
			new EmployeeModel("Anuj Gupta", "IT", 10000),
			new EmployeeModel("Harsha", "IT", 20000),
			new EmployeeModel("Versha", "Food", 22000),
			new EmployeeModel("Deepali", "Admin", 25000),
			new EmployeeModel("Shivang", "Marketing", 15000),
			new EmployeeModel("Charu", "Medicine", 18000),
			new EmployeeModel("PapaG", "Finance", 30000)
		);
		employeeRepository.saveAll(employees);
	}
}
