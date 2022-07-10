package com.example.demo.p1;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.BeanType;
import com.example.demo.Findable;
import com.example.demo.IFindable;
import com.example.demo.ServiceClass;

@Findable(name = BeanType.TYPE_A)
public class SecondFindableClass implements IFindable{
	
	@Autowired
	private ServiceClass service;
	
	public void method() {
		System.out.println("Second Findable class method");
		service.service("Second Class");
	}
}
