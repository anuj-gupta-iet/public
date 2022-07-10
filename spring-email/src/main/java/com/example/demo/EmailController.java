package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestBody EmailRequestModel emailRequest) {
		String status = emailService.sendEmail(emailRequest);
		return status;
	}
}
