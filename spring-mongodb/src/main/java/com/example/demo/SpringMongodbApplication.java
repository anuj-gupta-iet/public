package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringMongodbApplication {

	@Autowired
	private BookRepository bookRepository;

	@PostMapping("/addBook")
	public void addBook(@RequestBody Book book) {
		bookRepository.save(book);
	}
	
	@PostConstruct
	public void init() {
		List<Book> books = Arrays.asList(new Book(1, "Anuj", "Java"),
				new Book(1, "PapaG", "BSNL"));
		bookRepository.saveAll(books);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}

}

interface BookRepository extends MongoRepository<Book, Integer> {
}