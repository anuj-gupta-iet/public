package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("security-default")
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("anuj85").password(encoder.encode("anuj")).roles("EDIT");
		auth.inMemoryAuthentication().withUser("papa45").password(encoder.encode("papa")).roles("DELETE");
		auth.inMemoryAuthentication().withUser("deepali85").password(encoder.encode("deepali")).roles("EDIT", "DELETE");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/edit").hasAnyRole("EDIT").and().authorizeHttpRequests()
				.antMatchers("/delete").hasAnyRole("DELETE").anyRequest().authenticated().and().httpBasic();
	}

}
