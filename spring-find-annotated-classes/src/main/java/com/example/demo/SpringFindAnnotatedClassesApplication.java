package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@SpringBootApplication
public class SpringFindAnnotatedClassesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringFindAnnotatedClassesApplication.class, args);
	}

	@Autowired
	private ApplicationContext context;

	@Override
	public void run(String... args) throws Exception {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Findable.class));
		for (BeanDefinition beanDef : provider.findCandidateComponents("com.example.demo")) {
			String beanName = beanDef.getBeanClassName();
			System.out.println(beanName);

			Class<?> clazz = Class.forName(beanDef.getBeanClassName());
			Findable findable = clazz.getAnnotation(Findable.class);
			System.out.println(findable.name());

			IFindable ob = null;
			try {
				ob = (IFindable) context.getBean(clazz);
				ob.method();
			} catch (Exception e) {
				System.out.println("Failed to execute bean: " + beanName + ". Reason: " + e.getMessage());
			}
			System.out.println("=======");
		}
	}

}
