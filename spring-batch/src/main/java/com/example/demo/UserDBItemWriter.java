package com.example.demo;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDBItemWriter implements ItemWriter<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void write(List<? extends User> items) throws Exception {
		userRepository.saveAll(items);
	}


}
