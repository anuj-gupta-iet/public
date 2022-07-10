package com.example.demo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private MyUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/hello")
	public String hello() {
		System.out.println("inside helloAdmin");
		String name = SecurityUtil.getLoggedinUserName();
		return "Hello Admin: " + name;
	}
	
	@PostMapping("/create")
	public String create(@RequestBody UserRequest userRequest) {
		MyUserDetails userDetails = new MyUserDetails(userRequest, passwordEncoder);
		//userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		userRepository.save(userDetails);
		return "User Created";
	}
	
	@PostMapping("/update")
	public String update(@RequestBody UserRequest userRequest) {
		MyUserDetails userDetails = userRepository.findByUsername(userRequest.getUsername());
		userDetails.setAuthorities(userRequest.getAuthorities());
		userRepository.save(userDetails);
		return "User Updated";
	}
	
	
}
