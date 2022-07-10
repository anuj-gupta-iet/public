package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaPublisherConfig {

	@Bean(name = "stringKafkaTemplate")
	public KafkaTemplate<String, String> stringKafkaTemplate() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(configs);
		
		return new KafkaTemplate<String, String>(producerFactory);
	}
	
	@Bean(name = "rawObjectKafkaTemplate")
	public KafkaTemplate<String, User> rawObjectkafkaTemplate() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		DefaultKafkaProducerFactory<String, User> producerFactory = new DefaultKafkaProducerFactory<>(configs);

		return new KafkaTemplate<String, User>(producerFactory);
	}
}
