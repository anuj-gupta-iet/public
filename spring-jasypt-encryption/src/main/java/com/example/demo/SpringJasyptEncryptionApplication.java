package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * below h2 console can be accessed by providing username=myusername, password=mypassword
 * h2 console url - http://localhost:8080/h2-console 
 * 
 * in application.properties password is hardcoded in plain text which is not a good
 * 
 * run below mvn command to generate encrypted version of mypassword text and replace it in app.prop
 * mvn jasypt:encrypt-value -Djasypt.encryptor.password=mysecretkey -Djasypt.plugin.value=mypassword
 * 
 * now provide mysecretkey as VM arguments like this in order to start the application
 * -Djasypt.encryptor.password=mysecretkey
 */

@SpringBootApplication
public class SpringJasyptEncryptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJasyptEncryptionApplication.class, args);
	}

}
