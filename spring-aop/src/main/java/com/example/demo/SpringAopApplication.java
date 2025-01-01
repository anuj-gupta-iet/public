package com.example.demo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * here there are 2 aspects RestEndpointsLoggingAspect and RestExceptionsLoggingAspect
 * 
 * RestEndpointsLoggingAspect will be called for all controller's methods annotated with @LogRestEndpoint
 * RestExceptionsLoggingAspect will be called for all controllers inside package com.example.demo
 */

@SpringBootApplication
public class SpringAopApplication {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void init() {
	List<EmployeeModel> employees = Arrays.asList(new EmployeeModel("Anuj Gupta", "IT", 10000),
		new EmployeeModel("Harsha", "IT", 20000), new EmployeeModel("Versha", "Food", 22000),
		new EmployeeModel("Deepali", "Admin", 25000), new EmployeeModel("Shivang", "Marketing", 15000),
		new EmployeeModel("Charu", "Medicine", 18000), new EmployeeModel("PapaG", "Finance", 30000));
	employeeRepository.saveAll(employees);
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringAopApplication.class, args);
    }

}

@RestController
class MyController {

    @Autowired
    private EmployeeRepository repository;

    @LogRestEndpoint
    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeModel> createEmployee(@RequestBody EmployeeModel employee) {
	employee = repository.save(employee);
	return ResponseEntity.ok(employee);
    }

    @LogRestEndpoint
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeModel>> getAllEmployees() {
	List<EmployeeModel> employees = repository.findAll();
	return ResponseEntity.ok(employees);
    }

    @LogRestEndpoint
    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable Integer id) {
	Optional<EmployeeModel> employee = repository.findById(id);
	if (employee.isPresent()) {
	    return ResponseEntity.ok(employee.get());
	} else {
	    throw new RuntimeException("Invalid Employee Id: " + id);
	}
    }

    @PostMapping("/updateEmployee")
    public String updateEmployeeById(@RequestBody EmployeeModel employeeInput) {
	Optional<EmployeeModel> employee = repository.findById(employeeInput.getId());
	if (employee.isPresent()) {
	    repository.save(employeeInput);
	    return "Employee Updated Successfully";
	} else {
	    throw new RuntimeException("Invalid Employee Id: " + employeeInput.getId());
	}

    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    public String updateEmployeeById(@PathVariable Integer id) {
	repository.deleteById(id);
	return "Employee deleted with id: " + id;
    }

}

@Aspect
@Component
class RestExceptionsLoggingAspect {

    @Autowired
    private RestExceptionRepository restExceptionRepository;

    @AfterThrowing(pointcut = "execution(* com.example.demo.*.*(..))", throwing = "ex")
    public void logRestExceptions(JoinPoint jp, Exception ex) throws JsonProcessingException {
	String methodName = jp.getSignature().getName();
	String className = jp.getTarget().getClass().getName();
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ex.printStackTrace(pw);
	String sStackTrace = sw.toString();
	ObjectMapper objectMapper = new ObjectMapper();
	Object[] array = jp.getArgs();
	String requestJson = objectMapper.writeValueAsString(array);
	RestExceptionModel entity = new RestExceptionModel(methodName, className, requestJson, sStackTrace);
	restExceptionRepository.save(entity);
    }
}

@Aspect
@Component
class RestEndpointsLoggingAspect {

    @Autowired
    private RestLogRepository restLogRepository;

    @Around("@annotation(com.example.demo.LogRestEndpoint)")
    public Object logRestEndpoints(ProceedingJoinPoint pjp) throws Throwable {
	/*
	 * pjp.proceed() will actually call the target method in bean class.
	 */
	ObjectMapper objectMapper = new ObjectMapper();
	String methodName = pjp.getSignature().getName();
	String className = pjp.getTarget().getClass().getName();
	Object[] array = pjp.getArgs();
	String requestJson = objectMapper.writeValueAsString(array);
	Object response = pjp.proceed();
	String responseJson = objectMapper.writeValueAsString(response);
	RestLogModel entity = new RestLogModel(methodName, className, requestJson, responseJson);
	restLogRepository.save(entity);
	return response;
    }
}

interface RestLogRepository extends JpaRepository<RestLogModel, Integer> {
}

interface RestExceptionRepository extends JpaRepository<RestExceptionModel, Integer> {
}

interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

    Optional<EmployeeModel> findById(Integer id);

    List<EmployeeModel> findByDept(String dept);

    @Query(value = "select * from employee where salary between ?1 and ?2", nativeQuery = true)
    List<EmployeeModel> findBetweenSalary(Integer fromSalary, Integer toSalary);

}

@Entity
@Table(name = "rest_log")
@Data
@NoArgsConstructor
class RestLogModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String method;
    private String clazz;
    @Lob
    private String request;
    @Lob
    private String response;
    private Date createDt;

    public RestLogModel(String method, String clazz, String request, String response) {
	super();
	this.method = method;
	this.clazz = clazz;
	this.request = request;
	this.response = response;
	this.createDt = new Date();
    }

}

@Component
@Retention(RetentionPolicy.RUNTIME)
//Allow to use only on methods
@Target(ElementType.METHOD)
@interface LogRestEndpoint {

}

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
class EmployeeModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;

    public EmployeeModel(String name, String dept, Integer salary) {
	super();
	this.name = name;
	this.dept = dept;
	this.salary = salary;
    }
}

@Entity
@Table(name = "rest_log_exception")
@Data
@NoArgsConstructor
class RestExceptionModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String method;
    private String clazz;
    @Lob
    private String request;
    @Lob
    private String exception;
    private Date createDt;

    public RestExceptionModel(String method, String clazz, String request, String exception) {
	super();
	this.method = method;
	this.clazz = clazz;
	this.request = request;
	this.exception = exception;
	this.createDt = new Date();
    }

}
