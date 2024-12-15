package com.example.demo;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Repository;

import lombok.Data;

@SpringBootApplication
public class SpringRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

}

@Repository
class EmployeeCacheDao {
	private static final String ANUJ_TEST_CACHE_KEY = "ANUJ_TEST_CACHE_KEY";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void save(Employee employee) {
		redisTemplate.opsForHash().put(ANUJ_TEST_CACHE_KEY, employee.getId(), employee);
	}

	public List<Employee> getAll() {
		List<Employee> employees = redisTemplate.opsForHash().values(ANUJ_TEST_CACHE_KEY).stream()
				.map(e -> (Employee) e).collect(Collectors.toList());
		return employees;
	}

	public Employee getById(Integer id) {
		return (Employee) redisTemplate.opsForHash().get(ANUJ_TEST_CACHE_KEY, id);
	}

	public void delete(Integer id) {
		redisTemplate.opsForHash().delete(ANUJ_TEST_CACHE_KEY, id);
	}

}

@Configuration
@EnableRedisRepositories
class RedisConfig {

	@Bean
	public RedisConnectionFactory connectionFactory() {
		// for single redis host
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName("<redis-hostname>");
		configuration.setPort(6379);
		configuration.setPassword("<password>");

		return new LettuceConnectionFactory(configuration, LettuceClientConfiguration.builder().useSsl().build());

		// for clustered redis hosts
		// RedisClusterConfiguration configuration = new
		// RedisClusterConfiguration(Collections
		// .singletonList("<redis-hostname>:<port>"));
		// configuration.setPassword("<password>");
		// return new LettuceConnectionFactory(configuration,
		// LettuceClientConfiguration.builder().useSsl().build());
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		return template;
	}

}

@Data
class Employee implements Serializable {
	private static final long serialVersionUID = -1492890180601675479L;
	private Integer id;
	private String name;
}