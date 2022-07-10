package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> handleUserException(UserException ex) {
		ErrorDetails error = new ErrorDetails(ex.getMessage());
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.NOT_FOUND);
	}

}
