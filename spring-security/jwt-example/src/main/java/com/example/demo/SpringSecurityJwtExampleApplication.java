package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSecurityJwtExampleApplication {

	@GetMapping("/user")
	public String helloUser() {
		return "Hello User";
	}

	@GetMapping("/admin")
	public String helloAdmin() {
		return "Hello Admin";
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/generateToken")
	public String generateToken(@RequestBody UserDto userDto) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDto.getUsername(), userDto.getPassword());
		try {
			// uses in memory users to authenticate username/passwords present
			// in SecurityJwtConfig
			authentication = authenticationManager.authenticate(authentication);
		} catch (Exception e) {
			return "Authenticaltion Failed: " + e.getMessage();
		}
		String jwtToken = JwtUtil.generateToken(userDto.getUsername(),
				authentication.getAuthorities());
		return "Authenticaltion Successfull. Jwt token generated for : "
				+ userDto.getUsername() + " : " + jwtToken;

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtExampleApplication.class, args);
	}

}

