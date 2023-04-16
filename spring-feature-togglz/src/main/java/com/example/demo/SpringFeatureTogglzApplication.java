package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.jdbc.JDBCStateRepository;
import org.togglz.core.util.NamedFeature;

@SpringBootApplication
@RestController
public class SpringFeatureTogglzApplication {

	@Autowired
	private FeatureManager featureManager;

	public static final Feature FEATURE_ONE = new NamedFeature("FEATURE_ONE");

	@GetMapping("/getFeaturesStatus")
	public Map<String, Boolean> getFeaturesStatus() {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (Feature feature : featureManager.getFeatures()) {
			map.put(feature.name(), feature.isActive());
		}
		return map;
	}

	@GetMapping("/getFeatureOneStatus")
	public boolean getFeatureOneStatus() {
		return featureManager.isActive(FEATURE_ONE);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringFeatureTogglzApplication.class, args);
	}

}

// @Profile("jdbcFeaturesRepo")
@Configuration
class JDBCFeaturesRepoConfig {
	@Bean
	public LocalContainerEntityManagerFactoryBean userEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(userDataSource());
		em.setPackagesToScan(new String[]{"com.example.demo"});
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.show_sql", "true");
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean
	public DataSource userDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost:9091/mem:featuresdb");
		// table TOGGLZ will be created in this DB and features will be saved
		// here
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public StateRepository stateRepository(DataSource dataSource) {
		return new JDBCStateRepository(dataSource);
	}
}