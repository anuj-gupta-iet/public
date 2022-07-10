package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevProfileConfig {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers() {
		List<UserModel> devUsers = Stream.of(
			new UserModel("anuj", "anuj", "DEV_MAKER,DEV_CHECKER"),
			new UserModel("papaG", "papaG", "DEV_CLIENT_OWNER")
		).collect(Collectors.toList());
		repository.saveAll(devUsers);
	}
}
