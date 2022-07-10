package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaPublisher {

	@Autowired
	@Qualifier("stringKafkaTemplate")
	private KafkaTemplate<String, String> stringKafkaTemplate;

	@Autowired
	@Qualifier("rawObjectKafkaTemplate")
	private KafkaTemplate<String, User> rawObjectKafkaTemplate;

	@GetMapping("/publishStringMessage/{message}")
	public String publishStringMessage(@PathVariable String message) {
		ListenableFuture<SendResult<String, String>> future = stringKafkaTemplate
				.send(SpringKafkaApplication.stringKafkaTopicName, message);

		try {
			SendResult<String, String> result = future.get();
			System.out.println(result);
		} catch (Exception e) {
			return "Error while sending Message: " + e.getMessage();
		}

		return "Message Published Successfully";
	}

	@PostMapping("/publishRawObjectMessage")
	public String publishRawObjectMessage(@RequestBody User user) {
		rawObjectKafkaTemplate.send(SpringKafkaApplication.rawObjectKafkaTopicName, user);
		return "Message Published Successfully";
	}
}
