package com.example.demo;

import java.util.List;

public class UserDto {
	private String username;
	private String password;
	private String name;
	private List<String> authorities;
	public UserDto() {
		super();
	}
	public UserDto(String username, String password, String name,
			List<String> authorities) {
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
	public List<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
}
