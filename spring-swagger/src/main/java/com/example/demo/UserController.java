package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/user")
@Api(tags = {"User related operations"})
public class UserController {

	// initializing Map in one line
	private Map<String, User> userMap = new HashMap<String, User>() {
		{
			put("1", new User("1", "Anuj"));
			put("2", new User("2", "PapaG"));
		}
	};

	@ApiOperation(value = "Get a User by ID")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfull"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/getUserById/{id}")
	public User getUser(@PathVariable String id) {
		return userMap.get(id);
	}

	@ApiOperation(value = "Create a User")
	@PutMapping("/createUser")
	public User putUser(@RequestBody User user) {
		return userMap.put(user.getId(), user);
	}

	@ApiOperation(value = "Update a User")
	@PostMapping("/updateUser")
	public User postUser(@RequestBody User user) throws Exception {
		if (userMap.get(user.getId()) != null) {
			return userMap.put(user.getId(), user);
		} else {
			throw new Exception("User not Found with id: " + user.getId());
		}
	}

	@ApiOperation(value = "Delete a User")
	@DeleteMapping("/deleteUserById/{id}")
	public User deleteUser(@PathVariable String id) throws Exception {
		if (userMap.get(id) != null) {
			return userMap.remove(id);
		} else {
			throw new Exception("User not Found with id: " + id);
		}
	}

	@ApiOperation(value = "Get all Users")
	@GetMapping("/getAllUsers")
	public List<User> getAllUser() {
		return userMap.values().stream().collect(Collectors.toList());
	}

	@ApiOperation(value = "Health Check")
	@GetMapping("/health")
	public String health() {
		return "User service is up and running";
	}

}
