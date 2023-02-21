package com.example.demo.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	@Id
	private String id;
	private String username;

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}

}
