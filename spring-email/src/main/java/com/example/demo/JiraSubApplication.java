package com.example.demo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * this sub application provides endpoints to create/update jira tasks
 * it has one scheduler running which picks all tasks updated in last  min and send mails corresponds to those activities
 */

@RestController
class JiraController {

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

@Configuration
@EnableScheduling
class JiraEmailSchedulerConfig {

    @Autowired
    private JiraEmailService emailService;

    @Autowired
    private JiraEmailRepository emailRepository;

    // note : this is for simple scheduling, better is to go for quartz scheduler
    // Fire every 1 min
    @Scheduled(fixedRate = 60 * 1000L)
    public void runEmailScheduler() {
	System.out.println("Running Jira Email Scheduler at: " + new Date());
	List<JiraEmailModel> emailModels = emailRepository.findByIsMailSent(false);
	System.out.println(emailModels.size());
	System.out.println(emailModels);
	emailService.sendEmail(emailModels);
	for (JiraEmailModel emailModel : emailModels) {
	    emailModel.setIsMailSent(true);
	}
	System.out.println(emailModels);
	emailRepository.saveAll(emailModels);
    }
}

interface JiraTaskRepository extends JpaRepository<JiraTaskModel, Integer> {

}

@Entity
@Table(name = "cmn_jira_email")
@Data
@NoArgsConstructor
class JiraEmailModel {

    @Id
    @GeneratedValue
    private Integer id;
    private String taskName;
    private String createdBy;
    private Date createdDt = new Date();
    private Boolean isMailSent;
    private Boolean isUpdated;

    public JiraEmailModel(String taskName, String createdBy, Boolean isUpdated) {
	super();
	this.taskName = taskName;
	this.createdBy = createdBy;
	this.createdDt = new Date();
	this.isMailSent = false;
	this.isUpdated = isUpdated;
    }

}

interface JiraEmailRepository extends JpaRepository<JiraEmailModel, Integer> {

    List<JiraEmailModel> findByIsMailSent(Boolean flag);
}

@Service
class JiraEmailService {

    @Value("spring.mail.username")
    private String fromEmail;

    @Value("${spring.mail.to}")
    private String toEmail;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(List<JiraEmailModel> emailModels) {

	for (JiraEmailModel emailModel : emailModels) {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setFrom(fromEmail);
	    message.setTo(toEmail);
	    if (emailModel.getIsUpdated()) {
		message.setSubject("Task Updated Notification");
		String oldTaskName = emailModel.getTaskName().split("-")[0];
		String newTaskName = emailModel.getTaskName().split("-")[1];
		String oldAsignee = emailModel.getCreatedBy().split("-")[0];
		String newAsignee = emailModel.getCreatedBy().split("-")[1];
		String body = "Task " + oldTaskName + " has been updated to " + newTaskName + "."
			+ "\nTask owner has been updated from " + oldAsignee + " to " + newAsignee + "."
			+ "\nThis change happened on " + emailModel.getCreatedDt();
		message.setText(body);
	    } else {
		message.setSubject("Task Created Notification");
		String body = "Task " + emailModel.getTaskName() + " has been created." + "\nTask owner is "
			+ emailModel.getCreatedBy() + "." + "\nThis creation happened on " + emailModel.getCreatedDt();
		message.setText(body);
	    }

	    try {
		mailSender.send(message);
		System.out.println("Mail Sent for: " + emailModel);
	    } catch (MailException e) {
		e.printStackTrace();
		System.out.println("Error while Mail Sent for: " + emailModel);
	    }
	}

    }
}

@Entity
@Table(name = "cmn_jira_task")
@Data
@NoArgsConstructor
class JiraTaskModel {

    @Id
    @GeneratedValue
    private Integer id;
    private String taskName;
    private String createdBy;

    public JiraTaskModel(String taskName, String createdBy) {
	super();
	this.taskName = taskName;
	this.createdBy = createdBy;
    }
}
