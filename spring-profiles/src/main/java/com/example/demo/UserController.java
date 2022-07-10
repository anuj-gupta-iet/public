package com.example.demo;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping("/getUsers")
	public List<UserModel> getUsers() {
		List<UserModel> users = repository.findAll();
		users = users.stream().map(user -> {
			String password = user.getPassword();
			password = new String(Base64.getDecoder().decode(password));
			user.setPassword(password);
			return user;
		}).collect(Collectors.toList());
		return users;
	}
}
