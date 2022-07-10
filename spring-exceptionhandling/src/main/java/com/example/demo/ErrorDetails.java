package com.example.demo;

import java.util.Date;
import java.util.UUID;

public class ErrorDetails {

	private int errorCode;
	private String message;
	private Date timeStamp;

	public ErrorDetails(String message) {
		this.errorCode = UUID.randomUUID().hashCode();
		this.message = message;
		this.timeStamp = new Date();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}
