package com.example.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Component
@Retention(RetentionPolicy.RUNTIME)
//Allow to use only on class types:
@Target(ElementType.TYPE)
public @interface Findable {

	/**
	 * User friendly name of annotated class.
	 */
	BeanType name();
}
