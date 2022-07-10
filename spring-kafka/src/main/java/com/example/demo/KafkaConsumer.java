package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

	@KafkaListener(groupId = "STRING_GROUP", topics = SpringKafkaApplication.stringKafkaTopicName, 
			containerFactory = "kafkaStringListenerContainerFactory")
	public void consumeStringMessage(String message) {
		System.out.println("Consumed String Message: " + message);
	}

	@KafkaListener(groupId = "RAW_OBJECT_GROUP", topics = SpringKafkaApplication.rawObjectKafkaTopicName, 
			containerFactory = "kafkaRawObjectListenerContainerFactory")
	public void consumeRawObjectMessage(User user) {
		System.out.println("Consumed Raw Object Message: " + user.getName() + ", " + user.getAge());
	}
}
