package com.example.demo.db;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Profile("security-db")
public interface MyUserRepository extends JpaRepository<MyUserDetails, Integer>{

	MyUserDetails findByUsername(String username);

}
