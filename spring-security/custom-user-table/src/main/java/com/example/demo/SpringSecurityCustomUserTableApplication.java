package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//custom-user-table

@SpringBootApplication
@RestController
public class SpringSecurityCustomUserTableApplication {

	@GetMapping("/user")
	public String helloUser() {
		return "Hello User";
	}

	@GetMapping("/admin")
	public String helloAdmin() {
		return "Hello Admin";
	}

	@PostMapping("/createUser")
	public String createUser(@RequestBody UserDto userDto) {
		// converting list of string to comma separated value
		String authorities = userDto.getAuthorities().stream().collect(Collectors.joining(","));
		MyUserDetails myUserDetails = new MyUserDetails(userDto.getUsername(),passwordEncoder.encode(userDto.getPassword()),
				userDto.getName(), authorities);
		userRepository.save(myUserDetails);
		return "User Created Successfully";
	}

	@Autowired
	private MyUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void init() {
		List<MyUserDetails> users = Arrays.asList(
				new MyUserDetails("anuj", passwordEncoder.encode("anuj"), "Anuj Gupta", "ROLE_USER"),
				new MyUserDetails("papag", passwordEncoder.encode("papag"), "S C Gupta", "ROLE_ADMIN"));
		userRepository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCustomUserTableApplication.class,
				args);
	}

}