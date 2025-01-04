package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
@RestController
public class SpringExceptionhandlingApplication {

    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
	return userService.getUsers();
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Integer id) throws Throwable {
	Optional<User> user = userService.getUserById(id);
	if (user.isPresent()) {
	    return user.get();
	} else {
	    throw new UserException("User Not found with id:" + id);
	}
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody User user) throws Exception {
	userService.createUser(user);
	return "User Added Successfully: " + user.getName();

    }

    public static void main(String[] args) {
	SpringApplication.run(SpringExceptionhandlingApplication.class, args);
    }

}

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> handleUserException(UserException ex) {
	ErrorDetails error = new ErrorDetails(ex.getMessage());
	return new ResponseEntity<ErrorDetails>(error, HttpStatus.NOT_FOUND);
    }

}

@Data
class ErrorDetails {

    private int errorCode;
    private String message;
    private Date timeStamp;

    public ErrorDetails(String message) {
	this.errorCode = UUID.randomUUID().hashCode();
	this.message = message;
	this.timeStamp = new Date();
    }

}

@Data
@AllArgsConstructor
class User {
    private int id;
    private String name;
}

class UserException extends Throwable {

    private static final long serialVersionUID = 4367651368006093908L;

    public UserException(String string) {
	super(string);
    }

}

@Service
class UserService {

    private List<User> users;

    @PostConstruct
    public void initUsers() {
	List<User> list = new ArrayList<User>();
	list.add(new User(1, "anuj"));
	list.add(new User(2, "papaG"));
	this.users = list;
    }

    public List<User> getUsers() {
	return this.users;
    }

    public Optional<User> getUserById(Integer id) {
	Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
	return user;
    }

    public void createUser(User user) throws Exception {
	if (users.stream().anyMatch(u -> u.getName().equals(user.getName()) || u.getId() == user.getId())) {
	    throw new Exception("UserName/Id Already Exists");
	} else {
	    users.add(user);
	}
    }
}
