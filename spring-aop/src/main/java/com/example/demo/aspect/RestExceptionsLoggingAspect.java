package com.example.demo.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.RestExceptionModel;
import com.example.demo.RestExceptionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class RestExceptionsLoggingAspect {

	@Autowired
	private RestExceptionRepository repository;

	@AfterThrowing(pointcut = "execution(* com.example.demo.controller.*.*(..))", throwing = "ex")
	public void logRestExceptions(JoinPoint jp, Exception ex) throws JsonProcessingException {
		String methodName = jp.getSignature().getName();
		String className = jp.getTarget().getClass().getName();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String sStackTrace = sw.toString();
		ObjectMapper objectMapper = new ObjectMapper();
		Object[] array = jp.getArgs();
		String requestJson = objectMapper.writeValueAsString(array);
		RestExceptionModel entity = new RestExceptionModel(methodName, className, requestJson, sStackTrace);
		repository.save(entity);
	}
}
