package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUserDetails, Integer> {
	MyUserDetails findByUsername(String username);
}
