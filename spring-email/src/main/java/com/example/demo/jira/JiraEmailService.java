package com.example.demo.jira;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JiraEmailService {

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
						+ emailModel.getCreatedBy() + "." 
						+ "\nThis creation happened on " + emailModel.getCreatedDt();
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
