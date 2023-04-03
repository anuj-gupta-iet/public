package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSecurityCustomLoginPageApplication {

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCustomLoginPageApplication.class, args);
	}

}

@Controller
class LoginController {
	@RequestMapping(value = {"/my-login"}, method = RequestMethod.GET)
	public String helloAdmin() {
		return "myLogin";
	}
}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin().loginPage("/my-login")
			.permitAll(); // this is to avoid ERR_TOO_MANY_REDIRECTS
	}
}