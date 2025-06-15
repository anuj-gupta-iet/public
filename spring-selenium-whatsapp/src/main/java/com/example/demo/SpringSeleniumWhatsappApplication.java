package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * http://localhost:8080/start
 */

@SpringBootApplication
@RestController
public class SpringSeleniumWhatsappApplication {

    // @Autowired
    private ChromeDriver driver;

    // @GetMapping("/start/{number}/{txtMsg}")
    // public void start(@PathVariable String number, @PathVariable String txtMsg)
    // throws Exception {
    @GetMapping("/start")
    public void start() throws Exception {
	System.out.println("Message Sending Started");

	List<ContactDto> contactDtos = getContactDtos();
	Actions actions = new Actions(driver);

	contactDtos.forEach(contactDto -> {
	    String mobileNumber = contactDto.getMobileNumber();
	    String txtMsg = contactDto.getTxtMsg();
	    System.out.println("Mobile Number: " + mobileNumber + " , Text Message: " + txtMsg);
	    // Step 1. Clear Search Number text input field if any text is present in it
	    {
		try {
		    WebElement clearIcon = driver
			    .findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div[2]/span/button/span"));
		    actions.moveToElement(clearIcon).click().build().perform();
		} catch (Exception e) {
		    System.out.println("Not Able to Clear Search Input for: " + mobileNumber);
		    // e.printStackTrace();
		}
	    }

	    // 2. Then enter mobile number in Search Number text input field
	    {
		WebElement searchNumberElement = driver
			.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div[2]/div/div/div/p"));
		searchNumberElement.sendKeys(mobileNumber);
	    }

	    // 3. Click on that Searched Contact
	    {
		WebElement searchedContactElement = driver.findElement(
			By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div/div[2]/div/div/div/div[2]/div[1]/div[1]"));
		actions.moveToElement(searchedContactElement).click().build().perform();
	    }

	    // 4. on Right Hand Side Bottom a Message Window Opens, type text message in it
	    {
		WebElement messageInputElement = driver.findElement(
			By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div[1]/div[2]/div[1]/p"));
		messageInputElement.sendKeys(txtMsg);
	    }

	    // 5. Now click on Send Message Button. thats it :)
	    {
		WebElement sendMessageBtnElement = driver.findElement(
			By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div[2]/button/span"));
		actions.moveToElement(sendMessageBtnElement).click().build().perform();
	    }
	});

	System.out.println(
		"Done============================================================================================================");
    }

    private List<ContactDto> getContactDtos() {
	return Arrays.asList(new ContactDto("7503931534", "hi"), //
		new ContactDto("7503931534", "hello"), //
		new ContactDto("7503931534", "bye")//
	);
    }

    @PostConstruct
    private void init() {
	System.out.println("Init Started============");
	System.setProperty("webdriver.chrome.driver", "F:\\DocumentsInF\\wa\\chromedriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("https://web.whatsapp.com/");
	System.out.println("Init Ending============");
	// driver.get("https://www.google.com/");
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringSeleniumWhatsappApplication.class, args);
    }

}

@Data
@AllArgsConstructor
class ContactDto {
    private String mobileNumber;
    private String txtMsg;
}