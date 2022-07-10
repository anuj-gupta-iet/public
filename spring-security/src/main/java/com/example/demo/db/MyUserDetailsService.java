package com.example.demo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Profile("security-db")
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private MyUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserDetails userDetails = userRepository.findByUsername(username);
		System.out.println("MyUserDetailsService:" + userDetails);
		return userDetails;
	}
	

}
