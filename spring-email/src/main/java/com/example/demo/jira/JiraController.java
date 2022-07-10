package com.example.demo.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JiraController {

	@Autowired
	private JiraTaskRepository taskRepository;

	@Autowired
	private JiraEmailRepository emailRepository;

	@PostMapping("/createTask")
	public String createTask(@RequestBody JiraTaskModel jiraTaskModel) {
		try {
			taskRepository.save(jiraTaskModel);
			JiraEmailModel jiraEmailModel = new JiraEmailModel(jiraTaskModel.getTaskName(),
					jiraTaskModel.getCreatedBy(), false);
			emailRepository.save(jiraEmailModel);
			return "Task Created Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	@PostMapping("/updateTask")
	public String updateTask(@RequestBody JiraTaskModel jiraTaskModel) {
		try {
			JiraTaskModel jiraTaskFromDb = taskRepository.getById(jiraTaskModel.getId());
			JiraEmailModel jiraEmailModel = new JiraEmailModel(
					jiraTaskFromDb.getTaskName() + "-" + jiraTaskModel.getTaskName(),
					jiraTaskFromDb.getCreatedBy() + "-" + jiraTaskModel.getCreatedBy(), true);
			jiraTaskFromDb.setTaskName(jiraTaskModel.getTaskName());
			jiraTaskFromDb.setCreatedBy(jiraTaskModel.getCreatedBy());
			taskRepository.save(jiraTaskFromDb);
			emailRepository.save(jiraEmailModel);
			return "Task Updated Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}
	
}
