package com.example.demo.db;

public class UserRequest {
	private String username;
	private String password;
	private String name;
	private String authorities;

	public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRequest(String username, String password, String name, String authorities) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

}
