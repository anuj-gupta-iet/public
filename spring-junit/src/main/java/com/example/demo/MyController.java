package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@Autowired
	private MyService myService;

	// usage: http://localhost:8080/sayHello?name=anuj
	@GetMapping("/sayHello")
	public String sayHello(@RequestParam String name) {
		name = myService.sanitize(name);
		String msg = "Hello '" + name + "'";
		return msg;
	}
}
