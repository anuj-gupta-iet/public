package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaStringListenerContainerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "STRING_GROUP");

		DefaultKafkaConsumerFactory<String, String> consumerFactory = 
				new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer());

		ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory = 
				new ConcurrentKafkaListenerContainerFactory<>();
		listenerContainerFactory.setConsumerFactory(consumerFactory);

		return listenerContainerFactory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> kafkaRawObjectListenerContainerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "RAW_OBJECT_GROUP");

		DefaultKafkaConsumerFactory<String, User> consumerFactory = 
				new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(User.class));

		ConcurrentKafkaListenerContainerFactory<String, User> listenerContainerFactory = 
				new ConcurrentKafkaListenerContainerFactory<>();
		listenerContainerFactory.setConsumerFactory(consumerFactory);

		return listenerContainerFactory;
	}
}
