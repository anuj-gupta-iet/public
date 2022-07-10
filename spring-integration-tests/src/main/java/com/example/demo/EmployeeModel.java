package com.example.demo;

public class EmployeeModel {

	private Integer id;
	private String name;
	private Integer age;
	private SalaryModel salaryModel;

	public EmployeeModel(Integer id, String name, Integer age, SalaryModel salaryModel) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salaryModel = salaryModel;
	}

	public SalaryModel getSalaryModel() {
		return salaryModel;
	}

	public void setSalaryModel(SalaryModel salaryModel) {
		this.salaryModel = salaryModel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
