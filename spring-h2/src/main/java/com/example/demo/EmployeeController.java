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
public class EmployeeController {

	@Autowired
	private EmployeeRepository repository;

	@PostMapping("/createEmployee")
	public String createEmployee(@RequestBody EmployeeModel employee) {
		repository.save(employee);
		return "Employee saved with id: " + employee.getId();
	}

	@GetMapping("/readAllEmployees")
	public List<EmployeeModel> readAllEmployees() {
		return repository.findAll();
	}

	@GetMapping("/readEmployeeById/{id}")
	public EmployeeModel readEmployeeById(@PathVariable Integer id) {
		Optional<EmployeeModel> employee = repository.findById(id);
		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RuntimeException("Invalid Employee Id: " + id);
		}
	}

	@GetMapping("/readEmployeeByDept/{dept}")
	public List<EmployeeModel> readEmployeeById(@PathVariable String dept) {
		List<EmployeeModel> employees = repository.findByDept(dept);
		return employees;
	}
	
	@GetMapping("/readEmployeeBetweenSalary/{fromSalary}/{toSalary}")
	public List<EmployeeModel> readEmployeeBetweenSalary(@PathVariable Integer fromSalary,
			@PathVariable Integer toSalary) {
		List<EmployeeModel> employees = repository.findBetweenSalary(fromSalary, toSalary);
		return employees;
	}

	@PostMapping("/updateEmployee")
	public String updateEmployeeById(@RequestBody EmployeeModel employeeInput) {
		Optional<EmployeeModel> employee = repository.findById(employeeInput.getId());
		if (employee.isPresent()) {
			repository.save(employeeInput);
			return "Employee Updated Successfully";
		} else {
			throw new RuntimeException("Invalid Employee Id: " + employeeInput.getId());
		}

	}

	@DeleteMapping("/deleteEmployeeById/{id}")
	public String updateEmployeeById(@PathVariable Integer id) {
		repository.deleteById(id);
		return "Employee deleted with id: " + id;
	}

}
