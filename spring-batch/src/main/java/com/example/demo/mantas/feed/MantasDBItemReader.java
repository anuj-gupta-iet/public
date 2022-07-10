package com.example.demo.mantas.feed;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.User;

public class MantasDBItemReader implements ItemReader<User>{

	@Autowired
	
	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		return null;
	}

}
