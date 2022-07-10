package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class ProdProfileConfig {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers() {
		List<UserModel> devUsers = Stream.of(
			new UserModel("anuj", "anuj", "PROD_MAKER"),
			new UserModel("papaG", "papaG", "PROD_CHECKER,PROD_CLIENT_OWNER"),
			new UserModel("mummy", "mummy", "PROD_CHECKER,PROD_CLIENT_OWNER_MANAGER"),
			new UserModel("deepali", "deepali", "PROD_CHECKER"),
			new UserModel("versha", "versha", "PROD_CLIENT_OWNER_MANAGER"),
			new UserModel("shivang", "shivang", "PROD_MAKER,PROD_CHECKER")
		).collect(Collectors.toList());
		repository.saveAll(devUsers);
	}
}
