package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
public class SpringIntegrationTestsApplication {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void init() {
	List<UserModel> users = Arrays.asList(new UserModel("anujgupta", "IT", 52000));
	repository.saveAll(users);
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringIntegrationTestsApplication.class, args);
    }

}

@RestController
class UserController {

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
    public List<UserModel> readUserBetweenSalary(@PathVariable Integer fromSalary, @PathVariable Integer toSalary) {
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

interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findById(Integer id);

    List<UserModel> findByDept(String dept);

    @Query(value = "select * from cmn_user where salary between ?1 and ?2", nativeQuery = true)
    List<UserModel> findBetweenSalary(Integer fromSalary, Integer toSalary);

}

@RestController
class HelloController {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
	return "Hello " + name;
    }

    @GetMapping("/employee")
    public EmployeeModel employee() {
	return new EmployeeModel(1, "Anuj", 36, new SalaryModel(50000, "GBP"));
    }
}

@Data
@AllArgsConstructor
class EmployeeModel {

    private Integer id;
    private String name;
    private Integer age;
    private SalaryModel salaryModel;

}

@Data
@AllArgsConstructor
class SalaryModel {

    private Integer salary;
    private String currency;

}

@Data
@Entity
@Table(name = "CMN_USER")
class UserModel {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String dept;
    private Integer salary;

    public UserModel() {
	super();
    }

    public UserModel(String username, String dept, Integer salary) {
	super();
	this.username = username;
	this.dept = dept;
	this.salary = salary;
    }

}
