package com.example.demo.mantas.feed;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.User;

@Component
public class MantasItemWriter implements ItemWriter<User> {

	@Autowired(required = false)
	private RestTemplate restTemplate;

	@Override
	public void write(List<? extends User> items) throws Exception {
		items.stream().forEach(user -> {
			String response = restTemplate.getForObject("http://localhost:8080/lddEndpoint/" + user.getName(),
					String.class);
			System.out.println("MantasItemWriter=" + response);
		});

	}

}
