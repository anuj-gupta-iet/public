package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

	public void service(String name) {
		System.out.println(this + " : " + name);
	}
}
