package com.example.drl.file.demo;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDroolsDrlFileApplication {

	@Autowired
	private KieSession kieSession;

	@PostMapping("/orderNow")
	public Order orderNow(@RequestBody Order order) {
		kieSession.insert(order);
		kieSession.fireAllRules();
		return order;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDroolsDrlFileApplication.class, args);
	}

}

@Configuration
class DroolsConfig {
	@Bean
	public KieContainer getKieContainer() throws IOException {
		KieServices kieServices = KieServices.Factory.get();
		
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource("offer.drl"));
		
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer;
	}

	@Bean
	public KieSession getKieSession(KieContainer kieContainer) throws IOException {
		return kieContainer.newKieSession();

	}
}