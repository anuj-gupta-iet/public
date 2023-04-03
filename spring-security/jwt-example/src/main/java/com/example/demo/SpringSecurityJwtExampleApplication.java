package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//custom-user-table

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
			authentication = authenticationManager.authenticate(authentication);
			System.out.println(authentication);
		} catch (Exception e) {
			return "Authenticaltion Failed: " + e.getMessage();
		}
		return "Authenticaltion Successfull";

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtExampleApplication.class, args);
	}

}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("anuj").password(encoder.encode("anuj")).roles("USER");
		auth.inMemoryAuthentication().withUser("papag").password(encoder.encode("papag")).roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/generateToken").permitAll();
		http.authorizeRequests().antMatchers("/user").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN");
		http.httpBasic();
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}