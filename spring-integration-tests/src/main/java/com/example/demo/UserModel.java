package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CMN_USER")
public class UserModel {

	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String dept;
	private Integer salary;

	public UserModel() {
		super();
	}

	public UserModel(String username, String dept, Integer salary) {
		super();
		this.username = username;
		this.dept = dept;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}



}
