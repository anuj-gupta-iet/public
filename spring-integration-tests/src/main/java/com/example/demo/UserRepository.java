package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer>{

	Optional<UserModel> findById(Integer id);

	List<UserModel> findByDept(String dept);

	@Query(value = "select * from cmn_user where salary between ?1 and ?2", nativeQuery = true)
	List<UserModel> findBetweenSalary(Integer fromSalary, Integer toSalary);

}
