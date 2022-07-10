package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeModel {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String dept;
	private Integer salary;


	public EmployeeModel(String name, String dept, Integer salary) {
		super();
		this.name = name;
		this.dept = dept;
		this.salary = salary;
	}
	
	public EmployeeModel() {
		super();
	}
	
	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}
