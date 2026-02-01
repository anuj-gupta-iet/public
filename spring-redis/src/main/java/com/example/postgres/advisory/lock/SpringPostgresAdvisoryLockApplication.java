package com.example.postgres.advisory.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringPostgresAdvisoryLockApplication {

	@Autowired
	private MyReentrantLockService reentrantLockService;
	@Autowired
	private MyPostgresLockService postgresLockService;

	@GetMapping("/testReentrant")
	public void testReentrantEndpoint() throws InterruptedException {
		reentrantLockService.doSomething();
	}

	@GetMapping("/testPostgres/{unitIds}")
	public void testRedisEndpoint(@PathVariable List<String> unitIds) throws InterruptedException {
		postgresLockService.doSomething(unitIds);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringPostgresAdvisoryLockApplication.class, args);
	}

}

@Service
class MyPostgresLockService {
	@Autowired
	private PostgresSQLLock postgresSQLLock;
	@Autowired
	private Environment environment;

	public void doSomething(List<String> unitIds) throws InterruptedException {
		try {
			System.out.println("Entering Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			boolean success = postgresSQLLock.tryLock(unitIds);
			if (!success) {
				System.out.println("Unable to get the Lock. " + Arrays.toString(environment.getActiveProfiles()));
				return;
			}
			System.out.println("Entered Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			Thread.sleep(20000);
		} finally {
			System.out.println("Exiting Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
			postgresSQLLock.unlock(unitIds);
			System.out.println("Exited Critical Section. " + Arrays.toString(environment.getActiveProfiles()));
		}
	}
}

@Component
class PostgresSQLLock {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean tryLock(List<String> unitIds) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		long timeoutDuration = 60000;// check for 60 secs

		List<Boolean> successList = new ArrayList<Boolean>(unitIds.size());
		for (int i = 0; i < unitIds.size(); i++) {
			// try lock for first unit id
			// if successfully acquired then go for next unit id
			// otherwise keep waiting till timeout (for 60 sec)
			while (System.currentTimeMillis() - startTime < timeoutDuration) {
				Boolean isSuccess = tryLock(unitIds.get(i));
				if (isSuccess) {
					successList.add(isSuccess);
					break;
				}
				// add small sleep to prevent excessive cpu usage (busy waiting)
				Thread.sleep(1000);
			}
		}

		// chk if timeout happened, i.e. exceeds 60 secs
		// then we need to unlock already acquired locks
		if (successList.size() != unitIds.size()) {
			unlock(unitIds);
			return false;
		}

		// chk if any false is present in successList
		// if yes then it means all unit locks are not acquired
		boolean isAllUnitsLockAcquired = !successList.stream().filter(e -> !e).findFirst().isPresent();

		if (!isAllUnitsLockAcquired) {
			unlock(unitIds);
			return false;
		}

		return true;
	}

	private Boolean tryLock(String unitId) {
		long lockId = Math.abs(unitId.hashCode()) % (1L << 31);// use positive int range

		String sql = "select pg_try_advisory_lock(?)";
		Boolean isSuccess = BooleanUtils.isTrue(jdbcTemplate.queryForObject(sql, Boolean.class, lockId));
		return isSuccess;
	}

	@SuppressWarnings("unused")
	public void unlock(List<String> unitIds) {
		for (String unitId : unitIds) {
			long lockId = Math.abs(unitId.hashCode()) % (1L << 31);// use positive int range

			String sql = "select pg_advisory_unlock(?)";
			Boolean isSuccess = BooleanUtils.isTrue(jdbcTemplate.queryForObject(sql, Boolean.class, lockId));
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

