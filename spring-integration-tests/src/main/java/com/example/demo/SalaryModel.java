package com.example.demo;

public class SalaryModel {

	private Integer salary;
	private String currency;

	public SalaryModel(Integer salary, String currency) {
		super();
		this.salary = salary;
		this.currency = currency;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
