package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer>{

	Optional<EmployeeModel> findById(Integer id);

	List<EmployeeModel> findByDept(String dept);

	@Query(value = "select * from employee where salary between ?1 and ?2", nativeQuery = true)
	List<EmployeeModel> findBetweenSalary(Integer fromSalary, Integer toSalary);

}
