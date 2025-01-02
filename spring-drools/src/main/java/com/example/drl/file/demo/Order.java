package com.example.drl.file.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
	private String name;
	private String cardType;
	private Integer price;
	private Integer discount;
}
