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
public class SpringDatabase2Application {

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9091");
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void initDb() {
		String sqlStatements[] = { "drop table products if exists",
				"create table products(id varchar(255),productname varchar(255))",
				"insert into products values('product1','Mattress')",
				"insert into products values('product2','Bed')"};

		Arrays.asList(sqlStatements).forEach(sql -> {
			jdbcTemplate.execute(sql);
		});

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabase2Application.class, args);
	}

}
