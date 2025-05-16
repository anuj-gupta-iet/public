package com.example.demo;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import com.example.demo.product.ProductRepository;
import com.example.demo.user.UserRepository;

@SpringBootApplication
public class SpringMultipleDatabaseApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;

	@PostConstruct
	private void init() {
		System.out.println("Users from userdb:" + userRepository.findAll());
		//Users from userdb:[User [id=user1, username=anuj], User [id=user2, username=papaG]]
		System.out.println("Products from productdb:" + productRepository.findAll());
		// Products from productdb:[Product [id=product1, productname=Mattress], Product [id=product2, productname=Bed]]
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMultipleDatabaseApplication.class, args);
	}

}

@Configuration
@EnableJpaRepositories(
		// all Repositories inside this package "com.example.demo.user" e.g. "UserRepository" 
		// will follow this configuration
		basePackages = "com.example.demo.user", 
		entityManagerFactoryRef = "userEntityManager", 
		transactionManagerRef = "userTransactionManager")
class UserDBConfig {
	@Autowired
	private Environment env;

	@Bean
	@Primary
	public DataSource userDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("user.jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		return dataSource;
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean userEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(userDataSource());
		em.setPackagesToScan(new String[] { "com.example.demo.user" });
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		// below 2 properties are to automatically convert camelCase columns in Entity
		// to snake case columns in DB
		// e.g. employeeName column in POJO will be automatically converted to
		// employee_name in DB
		properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
		properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean
	@Primary
	public PlatformTransactionManager userTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(userEntityManager().getObject());
		return transactionManager;
	}
}

@Configuration
@EnableJpaRepositories(
		// all Repositories inside this package "com.example.demo.product" e.g. "ProductRepository" 
		// will follow this configuration
		basePackages = "com.example.demo.product", 
		entityManagerFactoryRef = "productEntityManager", 
		transactionManagerRef = "productTransactionManager")
class ProductDBConfig {
	@Autowired
	private Environment env;

	@Bean
	public DataSource productDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("product.jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean productEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setPackagesToScan(new String[] { "com.example.demo.product" });
		em.setDataSource(productDataSource());
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		// below 2 properties are to automatically convert camelCase columns in Entity
		// to snake case columns in DB
		// e.g. employeeName column in POJO will be automatically converted to
		// employee_name in DB
		properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
		properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean
	public TransactionManager productTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(productEntityManager().getObject());
		return transactionManager;
	}
}