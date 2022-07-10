package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringExceptionhandlingApplication {

	@Autowired
	private UserService userService;

	@GetMapping("/getUsers")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable Integer id) throws Throwable {
		Optional<User> user = userService.getUserById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserException("User Not found with id:" + id);
		}
	}

	@PostMapping("/createUser")
	public String createUser(@RequestBody User user) throws Exception {
		userService.createUser(user);
		return "User Added Successfully: " + user.getName();

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringExceptionhandlingApplication.class, args);
	}

}
