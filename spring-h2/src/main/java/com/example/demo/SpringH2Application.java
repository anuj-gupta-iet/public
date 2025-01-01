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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class SpringH2Application {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    private void init() {
	List<EmployeeModel> employees = Arrays.asList(new EmployeeModel("Anuj Gupta", 10000),
		new EmployeeModel("PapaG", 20000), new EmployeeModel("Harsha", 10000),
		new EmployeeModel("Shivang", 15000), new EmployeeModel("Charu", 18000),
		new EmployeeModel("Charu", 10000));
	employeeRepository.saveAll(employees);
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringH2Application.class, args);
    }

}

@RestController
class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/getAllEmployees")
    public List<EmployeeModel> getAllEmployees() {
	return employeeRepository.findAll();
    }
    
    @PostMapping("/createEmployee")
    public String createEmployee(@RequestBody EmployeeModel employee) {
	employeeRepository.save(employee);
	return "Employee saved with id: " + employee.getId();
    }

    @GetMapping("/getEmployeeById/{id}")
    public EmployeeModel getEmployeeById(@PathVariable Integer id) {
	Optional<EmployeeModel> employee = employeeRepository.findById(id);
	if (employee.isPresent()) {
	    return employee.get();
	} else {
	    throw new RuntimeException("Invalid Employee Id: " + id);
	}
    }

    @GetMapping("/getEmployeesByDept/{deptId}")
    public List<EmployeeModel> readEmployeeById(@PathVariable Integer deptId) {
	List<EmployeeModel> employees = null;// repository.findByDept(dept);
	return employees;
    }

    @GetMapping("/getEmployeesBetweenSalary/{fromSalary}/{toSalary}")
    public List<EmployeeModel> readEmployeeBetweenSalary(@PathVariable Integer fromSalary,
	    @PathVariable Integer toSalary) {
	List<EmployeeModel> employees = null;// repository.findBetweenSalary(fromSalary, toSalary);
	return employees;
    }

    @PostMapping("/updateEmployee")
    public String updateEmployeeById(@RequestBody EmployeeModel employeeInput) {
	Optional<EmployeeModel> employee = employeeRepository.findById(employeeInput.getId());
	if (employee.isPresent()) {
	    employeeRepository.save(employeeInput);
	    return "Employee Updated Successfully";
	} else {
	    throw new RuntimeException("Invalid Employee Id: " + employeeInput.getId());
	}

    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    public String updateEmployeeById(@PathVariable Integer id) {
	employeeRepository.deleteById(id);
	return "Employee deleted with id: " + id;
    }

}

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
class EmployeeModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer deptid;

    public EmployeeModel(String name, Integer deptid) {
	super();
	this.name = name;
	this.deptid = deptid;
    }

}

interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

}

