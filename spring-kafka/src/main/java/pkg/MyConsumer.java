package pkg;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MyConsumer {

	public static void main(String[] args) {
		// Kafka topic
		String topicName = "spring-boot-topic-string";
		Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");
		// consumer group name
		props.put("group.id", "STRING_GROUP");
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "5000");
		props.put("session.timeout.ms", "45000");
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(
				props);

		// Kafka Consumer subscribes list of topics here.
		consumer.subscribe(Arrays.asList(topicName));

		// print the topic name
		System.out.println("Subscribed to topic " + topicName);
		int i = 0;

		System.out.println("====> While loop start");
		while (true) {
			System.out.println("====> While loop Iteration");
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records)

				// print the offset,key and value for the consumer records.
				System.out.printf(
						"====> MyConsumer offset = %d, key = %s, value = %s\n",
						record.offset(), record.key(), record.value());
		}
	}
}
