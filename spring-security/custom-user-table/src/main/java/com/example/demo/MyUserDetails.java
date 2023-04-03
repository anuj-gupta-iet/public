package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER_MASTER")
public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 4898911790885494915L;

	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String authorities;

	public MyUserDetails(String username, String password, String name,
			String authorities) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.authorities = authorities;
	}

	public MyUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
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

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> list = new ArrayList<>();
		for (String authority : authorities.split(",")) {
			SimpleGrantedAuthority ob = new SimpleGrantedAuthority(authority.trim());
			list.add(ob);
		}
		return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "MyUserDetails [id=" + id + ", username=" + username
				+ ", password=" + password + ", authorities=" + authorities
				+ "]";
	}
}
