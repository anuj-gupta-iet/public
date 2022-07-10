package com.example.demo.jira;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiraConfig {

	@Autowired
	private JiraTaskRepository taskRepository;
	
	@PostConstruct
	public void init() {
		List<JiraTaskModel> jiraTasks = Arrays.asList(
			new JiraTaskModel("Task 1", "anuj"),
			new JiraTaskModel("Task 2", "deepali"),
			new JiraTaskModel("Task 3", "papaG")
		);
		taskRepository.saveAll(jiraTasks);
	}
}
