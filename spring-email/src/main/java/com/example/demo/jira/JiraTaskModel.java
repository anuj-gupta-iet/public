package com.example.demo.jira;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CMN_JIRA_TASK")
public class JiraTaskModel {

	@Id
	@GeneratedValue
	private Integer id;
	private String taskName;
	private String createdBy;
	
	public JiraTaskModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JiraTaskModel(String taskName, String createdBy) {
		super();
		this.taskName = taskName;
		this.createdBy = createdBy;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
