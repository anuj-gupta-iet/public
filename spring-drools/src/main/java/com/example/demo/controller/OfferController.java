package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Order;

@RestController
public class OfferController {
	
	@GetMapping("/order")
	public Order getOrder() {
		return new Order("Mobile", "ICICI");
	}
}
