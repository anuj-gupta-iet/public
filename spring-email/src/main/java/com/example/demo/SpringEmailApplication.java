package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@SpringBootApplication
public class SpringEmailApplication {

    public static void main(String[] args) {
	SpringApplication.run(SpringEmailApplication.class, args);
    }

}

@RestController
class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequestModel emailRequest) {
	String status = emailService.sendEmail(emailRequest);
	return status;
    }
}

@Service
class EmailService {

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

@Data
class EmailRequestModel {

    private String to;
    private String subject;
    private String body;
}
