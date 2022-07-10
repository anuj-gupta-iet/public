package com.example.demo.jira;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CMN_JIRA_EMAIL")
public class JiraEmailModel {

	@Id
	@GeneratedValue
	private Integer id;
	private String taskName;
	private String createdBy;
	private Date createdDt = new Date();
	private Boolean isMailSent;
	private Boolean isUpdated;

	public JiraEmailModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JiraEmailModel(String taskName, String createdBy, Boolean isUpdated) {
		super();
		this.taskName = taskName;
		this.createdBy = createdBy;
		this.createdDt = new Date();
		this.isMailSent = false;
		this.isUpdated = isUpdated;
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

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Boolean getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(Boolean isMailSent) {
		this.isMailSent = isMailSent;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public String toString() {
		return "JiraEmailModel [id=" + id + ", taskName=" + taskName + ", createdBy=" + createdBy + ", createdDt="
				+ createdDt + ", isMailSent=" + isMailSent + ", isUpdated=" + isUpdated + "]";
	}

}
