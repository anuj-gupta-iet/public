package com.example.demo;


import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // so that each test case will rut in its own transaction and changes will be
				// rollbacked after it finishes
class SpringIntegrationTestsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deleteUserByIdTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUserById/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("User deleted with id: 1"));
		
		try {
			ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/readUserById/1"));
			System.out.println(resultActions);
		} catch (Exception e) {
			String msg = e.getMessage();
			Assertions.assertThat(msg).endsWith("Invalid User Id: 1");
		}
		
	}
	
	@Test
	void updateUserTest() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserModel user = new UserModel("anujguptanew", "MISC", 35000);
		user.setId(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/updateUser")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(user)));
		mockMvc.perform(MockMvcRequestBuilders.get("/readUserById/1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.username",CoreMatchers.is("anujguptanew")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.dept",CoreMatchers.is("MISC")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.salary",CoreMatchers.is(35000)));
		
	}
	
	@Test
	void readUserBetweenSalaryTest() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new UserModel("meethi", "MISC", 30000))));
		mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserModel("charu", "MISC", 40000))));
		mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserModel("shivang", "MISC", 60000))));
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/readUserBetweenSalary/35000/55000"))
			.andDo(MockMvcResultHandlers.print());

		UserModel[] responseUsers = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),
				UserModel[].class);
		org.junit.jupiter.api.Assertions.assertEquals(2, responseUsers.length);
		for (UserModel user : responseUsers) {
			org.junit.jupiter.api.Assertions.assertTrue(user.getUsername().equals("anujgupta") || user.getUsername().equals("charu"));
			org.junit.jupiter.api.Assertions.assertTrue(user.getDept().equals("IT") || user.getDept().equals("MISC"));
			org.junit.jupiter.api.Assertions.assertTrue(user.getSalary().equals(52000) || user.getSalary().equals(40000));
		}
	}
	
	@Test
	void readUserByDeptTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/readUserByDept/IT"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",CoreMatchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].username",CoreMatchers.is("anujgupta")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].dept",CoreMatchers.is("IT")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary",CoreMatchers.is(52000)));
	}
	
	@Test
	void readUserByIdTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/readUserById/1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id",CoreMatchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.username",CoreMatchers.is("anujgupta")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.dept",CoreMatchers.is("IT")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.salary",CoreMatchers.is(52000)));
	}
	
	@Test
	void createUserTest() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/createUser")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new UserModel("harsha", "IT", 80000)));
		ResultActions resultActions = mockMvc.perform(postRequest)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
		String response = resultActions.andReturn().getResponse().getContentAsString();
		Assertions.assertThat(response).startsWith("User saved with id:");
	}
	
	@Test
	void readAllUsersTest1() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserModel("harsha", "IT", 80000))));
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/readAllUsers"))
			.andDo(MockMvcResultHandlers.print());
		UserModel[] responseUsers = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),
				UserModel[].class);
		System.out.println(responseUsers);
		org.junit.jupiter.api.Assertions.assertEquals(2, responseUsers.length);
		for (UserModel user : responseUsers) {
			org.junit.jupiter.api.Assertions.assertTrue(user.getUsername().equals("anujgupta") || user.getUsername().equals("harsha"));
			org.junit.jupiter.api.Assertions.assertTrue(user.getDept().equals("IT"));
			org.junit.jupiter.api.Assertions.assertTrue(user.getSalary().equals(52000) || user.getSalary().equals(80000));
		}
	}
	
	@Test
	void readAllUsersTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/readAllUsers"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",CoreMatchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].username",CoreMatchers.is("anujgupta")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].dept",CoreMatchers.is("IT")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary",CoreMatchers.is(52000)));
	}
	
	@Test
	void employeeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/employee"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id",CoreMatchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is("Anuj")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.age",CoreMatchers.is(36)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.salaryModel.salary",CoreMatchers.is(50000)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.salaryModel.currency",CoreMatchers.is("GBP")));
	}
	
	@Test
	void helloTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello/anuj");
		ResultMatcher matcher = MockMvcResultMatchers.status().isOk();
		mockMvc.perform(requestBuilder)
			.andExpect(matcher)
			.andExpect(MockMvcResultMatchers.content().string("Hello anuj"));
	}

}
