package com.example.demo;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("inside preHandle");
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			System.out.println(test);
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("inside postHandle");
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

}
