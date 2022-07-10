package com.example.demo;

import java.util.Base64;

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
	private String userName;
	private String password;
	private String roles;// "DEV_MAKER, DEV_CHECKER, UAT_CHECKER, PROD_CLIENT_OWNER"
	
	public UserModel(String userName, String password, String roles) {
		super();
		this.userName = userName;
		this.password = new String(Base64.getEncoder().encode(password.getBytes()));
		this.roles = roles;
	}
	
	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
