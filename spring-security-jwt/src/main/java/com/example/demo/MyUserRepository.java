package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MyUserRepository extends JpaRepository<MyUserDetails, Integer>{

	MyUserDetails findByUsername(String username);

}
