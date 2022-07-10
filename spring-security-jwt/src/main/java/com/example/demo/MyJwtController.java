package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyJwtController {

	@Autowired
	private MyUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/generateToken")
	public String generateToken(@RequestBody UserRequest userRequest) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
				userRequest.getPassword());
		Authentication authentication2 = null;
		try {
			authentication2 = authenticationManager.authenticate(authentication);
			System.out.println(authentication2);
		} catch (Exception e) {
			return "Authenticaltion Failed: " + e.getMessage();
		}
		String token = JwtUtil.generateToken(userRequest.getUsername());
		
		return "JWT Token Generated for : " + userRequest.getUsername() + " :" + token;
		//return "JWT Token Generated for : " + userRequest.getName() + " :" + token;
	}
	
	@GetMapping("/admin/hello")
	public String helloAdmin() {
		System.out.println("inside helloAdmin");
		String name = SecurityUtil.getLoggedinUserName();
		return "Hello Admin: " + name;
	}
	
	@GetMapping("/user/hello")
	public String helloUser() {
		System.out.println("inside helloAdmin");
		String name = SecurityUtil.getLoggedinUserName();
		return "Hello User: " + name;
	}
	
	/*@PostMapping("/create")
	public String create(@RequestBody UserRequest userRequest) {
		MyUserDetails userDetails = new MyUserDetails(userRequest, passwordEncoder);
		//userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		userRepository.save(userDetails);
		return "User Created";
	}*/
	
	/*@PostMapping("/update")
	public String update(@RequestBody UserRequest userRequest) {
		MyUserDetails userDetails = userRepository.findByUsername(userRequest.getUsername());
		userDetails.setAuthorities(userRequest.getAuthorities());
		userRepository.save(userDetails);
		return "User Updated";
	}*/
	
	
}
