package com.example.demo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MyUserRepository userRepository;

	@GetMapping("/hello")
	public String hello() {
		String name = SecurityUtil.getLoggedinUserName();
		return "Hello User: " + name;
	}

	@PostMapping("/update")
	public String update(@RequestBody UserRequest userRequest) {
		if (SecurityUtil.isLoggedinUserPrivileged()) {
			MyUserDetails userDetails = userRepository.findByUsername(userRequest.getUsername());
			userDetails.setName(userRequest.getName());
			userRepository.save(userDetails);
			return userRequest.getUsername() + " updated successfully";
		} else {
			return "Logged in user is not Privileged";
		}
	}
}
