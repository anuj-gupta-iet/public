package com.example.demo.mantas.feed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LDDController {

	@GetMapping("/lddEndpoint/{name}")
	public String lddEndpoint(@PathVariable String name) {
		System.out.println("inside lddEndpoint:" + name);
		return "LDD got:" + name;
	}

}
