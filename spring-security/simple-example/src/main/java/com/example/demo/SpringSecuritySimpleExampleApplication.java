package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSecuritySimpleExampleApplication {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@GetMapping("/user")
	public String helloUser() {
		return "Hello User";
	}
	
	@GetMapping("/admin")
	public String helloAdmin() {
		return "Hello Admin";
	}
	
	@GetMapping("/guest")
	public String helloGuest() {
		return "Hello Guest";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecuritySimpleExampleApplication.class, args);
	}

}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("anuj").password(encoder.encode("anuj")).roles("USER");
		auth.inMemoryAuthentication().withUser("papag").password(encoder.encode("papag")).roles("ADMIN");
		auth.inMemoryAuthentication().withUser("deepali").password(encoder.encode("deepali")).roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN");
		
		// here we are using both form based authentication (browser login form)
		// and basic authentication (postman)
		http.formLogin();
		http.httpBasic();
		
		//shortcut way
		/*http
			.authorizeRequests().antMatchers("/user").hasAnyRole("USER").and()
			.authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN").and()
			.formLogin().and()
			.httpBasic();*/
		
		// here authentication status is not saved in session
		// it is handled in stateless way means user needs to provide credentials in every request 
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}
}