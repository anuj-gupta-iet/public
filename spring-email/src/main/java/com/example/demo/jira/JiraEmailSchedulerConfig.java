package com.example.demo.jira;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class JiraEmailSchedulerConfig {

	@Autowired
	private JiraEmailService emailService;

	@Autowired
	private JiraEmailRepository emailRepository;

	// Fire every 2 min
	@Scheduled(fixedRate = 120 * 1000L)
	public void runEmailScheduler() {
		System.out.println("Running Jira Email Scheduler at: " + new Date());
		List<JiraEmailModel> emailModels = emailRepository.findByIsMailSent(false);
		System.out.println(emailModels.size());
		System.out.println(emailModels);
		emailService.sendEmail(emailModels);
		for (JiraEmailModel emailModel : emailModels) {
			emailModel.setIsMailSent(true);
		}
		System.out.println(emailModels);
		emailRepository.saveAll(emailModels);
	}
}
