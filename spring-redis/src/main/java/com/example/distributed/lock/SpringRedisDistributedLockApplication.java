package com.example.distributed.lock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringRedisDistributedLockApplication {

	@Autowired
	private MyReentrantLockService reentrantLockService;
	@Autowired
	private MyRedisLockService redisLockService;

	@GetMapping("/testReentrant")
	public void testReentrantEndpoint() throws InterruptedException {
		reentrantLockService.doSomething();
	}

	@GetMapping("/testRedis")
	public void testRedisEndpoint() throws InterruptedException {
		redisLockService.doSomething("ANUJ_LOCK_KEY");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisDistributedLockApplication.class, args);
	}

}

@Service
class MyRedisLockService {
	@Autowired
	private ExpirableLockRegistry lockRegistry;
	@Autowired
	private Environment environment;

	public void doSomething(String lockKey) throws InterruptedException {
		Lock lock = lockRegistry.obtain(lockKey);
		try {
			System.out.println("Entering Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			boolean success = lock.tryLock(60, TimeUnit.SECONDS);
			if (!success) {
				System.out.println("Unable to get the Lock. " + Arrays.toString(environment.getActiveProfiles()));
				return;
			}
			System.out.println("Entered Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			Thread.sleep(20000);
		} finally {
			System.out.println("Exiting Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			lock.unlock();
			System.out.println("Exited Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
		}
	}
}

@Service
class MyReentrantLockService {
	private ReentrantLock reentrantLock = new ReentrantLock();
	@Autowired
	private Environment environment;

	public void doSomething() throws InterruptedException {
		try {
			System.out.println("Entering Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			reentrantLock.lock();
			System.out.println("Entered Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			Thread.sleep(20000);
		} finally {
			System.out.println("Exiting Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			reentrantLock.unlock();
			System.out.println("Exited Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
		}
	}
}

@Configuration
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