package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class MyService {

	public String sanitize(String name) {
		return name.trim();
	}

}
