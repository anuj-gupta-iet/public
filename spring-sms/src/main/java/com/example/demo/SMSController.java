package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

	@Autowired
	private SMSService smsService;

	@PostMapping("/sendSMS")
	public String sendSMS(@RequestBody SMSRequestModel smsRequest) {
		String status = smsService.sendSMS(smsRequest);
		return status;
	}
}
