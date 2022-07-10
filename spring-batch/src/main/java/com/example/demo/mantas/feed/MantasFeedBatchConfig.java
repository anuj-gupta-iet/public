package com.example.demo.mantas.feed;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.example.demo.User;
import com.example.demo.UserRepository;

@Configuration
@EnableBatchProcessing
@Profile("mantasfeed")
public class MantasFeedBatchConfig {

	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void initUsers() {
		List<User> users = Arrays.asList(
			new User("Anuj", "APPROVED"),
			new User("PapaG", "APPROVED"),
			new User("Deepali", "NOT_APPROVED"),
			new User("Anukul", "NOT_APPROVED"),
			new User("Harsha", "APPROVED")
		);
		userRepository.saveAll(users);
	}
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<User> reader,
			@Qualifier("mantasItemProcessor") ItemProcessor<User, User> processor,
			@Qualifier("mantasItemWriter") ItemWriter<User> writer) {
		
		Step step = stepBuilderFactory.get("MANTAS-FEED-STEP")
			.<User, User>chunk(100)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
		
		return jobBuilderFactory.get("MANTAS-FEED-JOB")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public ItemReader<User> getItemReader() throws MalformedURLException {
		List<User> users = userRepository.findAll();
		ListItemReader<User> listItemReader = new ListItemReader<>(users);
		
		return listItemReader;
	}
}
