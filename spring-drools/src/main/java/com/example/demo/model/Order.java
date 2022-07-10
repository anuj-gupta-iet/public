package com.example.demo.model;

public class Order {
	private String name;
	private String cardType;
	
	public Order(String name, String cardType) {
		super();
		this.name = name;
		this.cardType = cardType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	
}
