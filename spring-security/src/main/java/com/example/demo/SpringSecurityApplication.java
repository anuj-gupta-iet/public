package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.db.MyUserDetails;
import com.example.demo.db.MyUserRepository;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableWebSecurity
public class SpringSecurityApplication {

	@Autowired
	private MyUserRepository userRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	private void init() {
		System.out.println(passwordEncoder);
		List<MyUserDetails> users = Arrays.asList(
			new MyUserDetails("anuj", passwordEncoder.encode("anuj"), "Anuj Gupta", "ROLE_USER,ROLE_ADMIN"),
			new MyUserDetails("papag", passwordEncoder.encode("papag"), "S C Gupta", "ROLE_USER"),
			new MyUserDetails("gaurav", passwordEncoder.encode("gaurav"), "Gaurav Sethi", "ROLE_USER,ROLE_USER_PRIVILEGED")
		);
		userRepository.saveAll(users);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}
