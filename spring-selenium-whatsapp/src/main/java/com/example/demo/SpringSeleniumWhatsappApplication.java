package com.example.demo;

import java.time.Duration;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SpringSeleniumWhatsappApplication {

    //@Autowired
    private ChromeDriver driver;

    @PostConstruct
    private void init() {
	System.out.println("Inside init============");
	System.setProperty("webdriver.chrome.driver", "F:\\DocumentsInF\\wa\\chromedriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("https://www.google.com/");
	//driver.get("https://web.whatsapp.com/");
	System.out.println(1111);
	Dimension dimension = new Dimension(700, 650);
	System.out.println(2222);
	driver.manage().window().setSize(dimension);
	
	PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");

        Sequence actions = new Sequence(mouse, 0)
                .addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 50, 100));
        

        ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));
        
	//System.out.println(3333);
	//new Actions(driver).moveByOffset(300, 500).perform();
	System.out.println(4444);
	//Coordinates where = new ;
	//driver.getMouse().mouseMove(where);
	WebDriverWait wait = new WebDriverWait(driver, 1);
	wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("/html/body/div[1]/div/div/div[3]/div/div[3]/div/div[1]/div/div[2]/div/div/div/p")));
	System.out.println("Completing init============");
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringSeleniumWhatsappApplication.class, args);
    }

}

/*@Configuration
class Config{
    @Bean
    public ChromeDriver getChromeDriver() {
	return new ChromeDriver();
    }
    
}*/