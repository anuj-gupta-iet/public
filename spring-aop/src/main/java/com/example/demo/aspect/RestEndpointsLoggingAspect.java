package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.RestLogModel;
import com.example.demo.RestLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class RestEndpointsLoggingAspect {

	@Autowired
	private RestLogRepository repository;

	@Around("@annotation(com.example.demo.LogRestEndpoint)")
	public Object logRestEndpoints(ProceedingJoinPoint pjp) throws Throwable {
		/*
		 * pjp.proceed() will actually call the target method in bean class.
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().getName();
		Object[] array = pjp.getArgs();
		String requestJson = objectMapper.writeValueAsString(array);
		Object response = pjp.proceed();
		String responseJson = objectMapper.writeValueAsString(response);
		RestLogModel entity = new RestLogModel(methodName, className, requestJson, responseJson);
		repository.save(entity);
		return response;
	}
}
