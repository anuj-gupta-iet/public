package com.example.demo.jira;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JiraEmailRepository extends JpaRepository<JiraEmailModel, Integer>{

	List<JiraEmailModel> findByIsMailSent(Boolean flag);
}
