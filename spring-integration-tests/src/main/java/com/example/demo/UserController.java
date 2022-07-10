package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;

	@PostMapping("/createUser")
	public String createUser(@RequestBody UserModel user) {
		repository.save(user);
		return "User saved with id: " + user.getId();
	}

	@GetMapping("/readAllUsers")
	public List<UserModel> readAllUsers() {
		return repository.findAll();
	}

	@GetMapping("/readUserById/{id}")
	public UserModel readUserById(@PathVariable Integer id) {
		Optional<UserModel> user = repository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new RuntimeException("Invalid User Id: " + id);
		}
	}

	@GetMapping("/readUserByDept/{dept}")
	public List<UserModel> readUserById(@PathVariable String dept) {
		List<UserModel> employees = repository.findByDept(dept);
		return employees;
	}
	
	@GetMapping("/readUserBetweenSalary/{fromSalary}/{toSalary}")
	public List<UserModel> readUserBetweenSalary(@PathVariable Integer fromSalary,
			@PathVariable Integer toSalary) {
		List<UserModel> employees = repository.findBetweenSalary(fromSalary, toSalary);
		return employees;
	}

	@PostMapping("/updateUser")
	public String updateUserById(@RequestBody UserModel employeeInput) {
		Optional<UserModel> user = repository.findById(employeeInput.getId());
		if (user.isPresent()) {
			repository.save(employeeInput);
			return "User Updated Successfully";
		} else {
			throw new RuntimeException("Invalid User Id: " + employeeInput.getId());
		}

	}

	@DeleteMapping("/deleteUserById/{id}")
	public String updateUserById(@PathVariable Integer id) {
		repository.deleteById(id);
		return "User deleted with id: " + id;
	}
}
