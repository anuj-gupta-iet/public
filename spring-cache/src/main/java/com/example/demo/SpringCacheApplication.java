package com.example.demo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class SpringCacheApplication {

	@Autowired
	MyService myService;

	// http://localhost:8080/getValue?id=3
	@GetMapping("/getValue")
	public Employee getValue(@RequestParam Integer id) {
		System.out.println("Controller Method Called with id: " + id);
		return myService.getValue(id);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCacheApplication.class, args);
	}

}

@Component
class MyService {
	// this annotation means if we call this method with id=1 and a cache entry
	// is present with key=1 then this method is not called, instead value
	// present in that cache entry is returned
	@Cacheable(cacheNames = "employeeCache", key = "#id")
	public Employee getValue(Integer id) {
		System.out.println("Service Method Called with id: " + id);
		return new Employee(id);
	}
}

class Employee implements Serializable {
	private static final long serialVersionUID = 9176390208949006023L;
	int empId;
	public Employee() {
		super();
	}
	public Employee(int empId) {
		super();
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + empId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (empId != other.empId)
			return false;
		return true;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}

}