package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Value("spring.mail.username")
	private String fromEmail;

	@Autowired
	private JavaMailSender mailSender;

	public String sendEmail(EmailRequestModel emailRequest) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(emailRequest.getTo());
		message.setSubject(emailRequest.getSubject());
		message.setText(emailRequest.getBody());

		try {
			mailSender.send(message);
			return "Mail Sent";
		} catch (MailException e) {
			e.printStackTrace();
			return "Error while Mail Sent: " + e.getMessage();
		}

	}
}
