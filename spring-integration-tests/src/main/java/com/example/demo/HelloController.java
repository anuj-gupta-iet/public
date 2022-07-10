package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello/{name}")
	public String sayHello(@PathVariable String name) {
		return "Hello " + name;
	}

	@GetMapping("/employee")
	public EmployeeModel employee() {
		return new EmployeeModel(1, "Anuj", 36, new SalaryModel(50000, "GBP"));
	}
}
