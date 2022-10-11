package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MyControllerTest {

	@Mock
	private MyService myService;

	@InjectMocks
	private MyController myController;

	@BeforeEach
	public void setup() {
		// this will initilize all dependecies of class annotated with @InjectMocks i.e.
		// MyController using @Autowired annotations
		// their setter are no longer needed
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSayHello() {
		Mockito.when(myService.sanitize("Anuj")).thenReturn("AnujGupta");
		String msg = myController.sayHello("Anuj");
		assertEquals("Hello 'AnujGupta'", msg);
	}
}
