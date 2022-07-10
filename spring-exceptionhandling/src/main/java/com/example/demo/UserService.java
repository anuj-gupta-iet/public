package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private List<User> users;

	@PostConstruct
	public void initUsers() {
		List<User> list = new ArrayList<User>();
		list.add(new User(1, "anuj"));
		list.add(new User(2, "papaG"));
		this.users = list;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public Optional<User> getUserById(Integer id) {
		Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
		return user;
	}

	public void createUser(User user) throws Exception {
		if (users.stream().anyMatch(u -> u.getName().equals(user.getName()) || u.getId() == user.getId())) {
			throw new Exception("UserName/Id Already Exists");
		} else {
			users.add(user);
		}
	}
}
