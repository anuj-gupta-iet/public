package com.example.demo;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileUrlResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<User> reader,
			@Qualifier("userCSVItemProcessor") ItemProcessor<User, User> processor,
			@Qualifier("userDBItemWriter") ItemWriter<User> writer) {
		
		Step step = stepBuilderFactory.get("CSV-DB-STEP")
			.<User, User>chunk(2)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
		
		return jobBuilderFactory.get("CSV-DB-JOB")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}

	@Bean
	public ItemReader<User> getItemReader() throws MalformedURLException {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setStrict(false);
		tokenizer.setNames(new String[] { "name", "dept_code", "salary" });

		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		
		DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		FileUrlResource resource = new FileUrlResource(
				"E:\\Program Files\\EclipseWorkspace\\spring-batch\\src\\main\\resources\\users.csv");

		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setName("CSV-READER");
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper);

		return flatFileItemReader;
	}
}
