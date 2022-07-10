package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSService {

	@Value("${twilio.trial.number}")
	private String trialNumber;

	public String sendSMS(SMSRequestModel smsRequest) {
		PhoneNumber to = new PhoneNumber(smsRequest.getTo());// should always be +447544546110 for trial account
		PhoneNumber from = new PhoneNumber(this.trialNumber);
		try {
			Message message = Message.creator(to, from, smsRequest.getBody()).create();
			System.out.println(message);
			return "SMS sent";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error while SMS Sent: " + e.getMessage();
		}
	}
}
