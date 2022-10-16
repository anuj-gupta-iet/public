package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPropertyController {

	@Autowired
	private Environment environment;
	
	@GetMapping("/getProperty/{name}")
	public String getProperty(@PathVariable String name) {
		return environment.getProperty(name);
	}
}
