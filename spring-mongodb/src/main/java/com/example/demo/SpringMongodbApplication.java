package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMongodbApplication {

	/*
	 * @Autowired private BookRepository bookRepository;
	 * 
	 * @PostConstruct public void init() { List<Book> books = Arrays.asList( new
	 * Book(1, "Anuj", "Java"), new Book(1, "PapaG", "BSNL") );
	 * bookRepository.saveAll(books); }
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}

}
