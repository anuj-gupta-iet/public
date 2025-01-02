package com.example.xls.file.demo;

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

/*
 * this is a decision table example
 * why we use this? becasue using .drl file needs technical knowledge
 * but in this version any business guy can also do changes in rules using excel file
 */

@SpringBootApplication
@RestController
public class SpringDroolsDecisionTableApplication {

	@Autowired
	private KieSession kieSession;

	@PostMapping("/orderNow")
	public Order orderNow(@RequestBody Order order) {
		kieSession.insert(order);
		kieSession.fireAllRules();
		return order;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDroolsDecisionTableApplication.class, args);
	}

}

@Configuration
class DroolsConfig {
	@Bean
	public KieContainer getKieContainer() throws IOException {
		KieServices kieServices = KieServices.Factory.get();
		
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource("offer.xlsx"));
		
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

	// another way to provide KieSession bean
	/*@Bean
	public InternalKnowledgeBase getKieContainer() throws IOException {
		DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
		configuration.setInputType(DecisionTableInputType.XLSX);
		
		KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		knowledgeBuilder.add(ResourceFactory.newClassPathResource("rules_folder/offer.xlsx"), ResourceType.DTABLE,
				configuration);
		
		if(knowledgeBuilder.hasErrors()) {
			System.out.println(knowledgeBuilder.getErrors());
		}
		
		InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addPackages(knowledgeBuilder.getKnowledgePackages());

		return knowledgeBase;
	}

	@Bean
	public KieSession getKieSession(InternalKnowledgeBase knowledgeBase) throws IOException {
		return knowledgeBase.newKieSession();
		//return kieContainer.newKieSession();

	}*/
}