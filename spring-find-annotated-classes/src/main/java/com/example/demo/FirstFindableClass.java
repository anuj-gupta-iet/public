package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

@Findable(name = BeanType.TYPE_B)
public class FirstFindableClass implements IFindable {

	@Autowired
	private ServiceClass service;
	
	public void method() {
		System.out.println("First Findable class method");
		service.service("First Class");
	}
}
