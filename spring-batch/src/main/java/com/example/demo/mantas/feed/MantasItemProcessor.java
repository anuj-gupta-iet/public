package com.example.demo.mantas.feed;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.User;

@Component
public class MantasItemProcessor implements ItemProcessor<User, User> {

	@Override
	public User process(User item) throws Exception {
		if ("APPROVED".equals(item.getStatus())) {
			return item;
		} else {
			return null;
		}
	}

}
