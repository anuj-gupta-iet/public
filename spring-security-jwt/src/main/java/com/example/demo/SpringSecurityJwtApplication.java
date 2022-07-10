package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableWebSecurity
public class SpringSecurityJwtApplication {

	@Autowired
	private MyUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	private void init() {
		System.out.println(passwordEncoder);
		List<MyUserDetails> users = Arrays.asList(
			new MyUserDetails("anuj", passwordEncoder.encode("anuj"), "Anuj Gupta", "ROLE_USER,ROLE_ADMIN"),
			new MyUserDetails("papag", passwordEncoder.encode("papag"), "S C Gupta", "ROLE_USER")
		);
		userRepository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

}
