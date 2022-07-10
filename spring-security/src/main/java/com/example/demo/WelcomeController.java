package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/edit")
	public String edit() {
		return "Edit Resource";
	}

	@GetMapping("/delete")
	public String delete() {
		return "Delete Resource";
	}

	@GetMapping("/secure/url")
	public String secureURL() {
		return "Secure URL";
	}

	@GetMapping("/unsecured/url")
	public String unsecureURL() {
		return "Unsecure URL";
	}
}
