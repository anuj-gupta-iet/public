package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@GetMapping("/getBookById/{id}")
	public String getUser(@PathVariable String id) {
		return "Some dummy response";
	}
}
