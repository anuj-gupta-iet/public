package com.example.demo;

import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SpringDatabase1Application {

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initDb() {
		String sqlStatements[] = { "drop table users if exists",
				"create table users(id varchar(255),username varchar(255))", 
				"insert into users values('user1','anuj')",
				"insert into users values('user2','papaG')"
				};
				//"create table users(id serial,username varchar(255))"
				//"insert into employees(first_name, last_name) values('User1','User1')",

		Arrays.asList(sqlStatements).forEach(sql -> {
			jdbcTemplate.execute(sql);
		});

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabase1Application.class, args);
	}

}
