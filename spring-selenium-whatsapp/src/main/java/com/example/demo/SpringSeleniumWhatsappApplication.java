package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * http://localhost:8080/start
 * 
 * Prerequisite
 * 1. after opening whatsapp web, clear all unwanted popups etc.
 * 2. close Turn off notifications popup
 * 
 * Main Contacts file
 * E:\Program Files\EclipseWorkspace\spring-selenium-whatsapp\src\main\resources\Main_Contacts.xlsx
 * 
 * CSV Contacts file
 * E:\Program Files\EclipseWorkspace\spring-selenium-whatsapp\src\main\resources\contacts.csv
 * 
 * to Start sending msges
 * http://localhost:8080/start
 * 
 * src\main\resources\whatsapp_similie_shortcuts.pdf
 */

@SpringBootApplication
@RestController
public class SpringSeleniumWhatsappApplication {

    private static final String WISH_MESSAGE = "Happy New Year";
    private static final String SIMILIE = ":-)";
    //private static final String WISH_MESSAGE = "Wish you very Happy Diwali";
    
    // @Autowired
    private ChromeDriver driver;

    private List<ContactDto> getContactDtos() throws Exception {
	List<List<String>> records = new ArrayList<>();
	try (BufferedReader br = new BufferedReader(new FileReader(
		"E:\\Program Files\\EclipseWorkspace\\spring-selenium-whatsapp\\src\\main\\resources\\contacts.csv"))) {
	    String line;
	    int count = 0;
	    while ((line = br.readLine()) != null) {
		if (count++ == 0) { // ignoring header
		    continue;
		}
		String[] values = line.split(",");
		records.add(Arrays.asList(values));
	    }
	}
	System.out.println(records);
	List<ContactDto> dtoList = records.stream().filter(e -> !e.isEmpty()).map(e -> {
	    String name = e.get(0);
	    String acronym = e.get(1);
	    String number = e.get(2);
	    ContactDto dto = new ContactDto(name, number, Arrays.asList(WISH_MESSAGE + " " + acronym, "-- Anuj", SIMILIE));
	    return dto;
	}).collect(Collectors.toList());
	System.out.println("Dto List: " + dtoList);
	return dtoList;
    }

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
	    List<String> txtMsgs = contactDto.getTxtMsg();
	    System.out.println("Mobile Number: " + mobileNumber + " , Text Messages: " + txtMsgs);
	    System.out.println("Doing Step 1");
	    
	    // Step 1. Clear Search Number text input field if any text is present in it
	    {
		try {
		    WebElement clearIcon = driver
			    .findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div[2]/span/button/span"));
		    actions.moveToElement(clearIcon).click().build().perform();
		} catch (Exception e) {
		    System.out.println("Error in Step 1");
		    System.out.println("Not Able to Clear Search Input for: " + mobileNumber);
		    // e.printStackTrace();
		}
	    }
	    
	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException e2) {}
	    
	    System.out.println("Doing Step 2");
	    // 2. Then enter mobile number in Search Number text input field
	    {
		try {
		    WebElement searchNumberElement = driver
			    .findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div[2]/div/div/div/p"));
		    searchNumberElement.sendKeys(mobileNumber);
		} catch (Exception e) {
		    System.out.println("Error in Step 2");
		    throw new RuntimeException(
			    "Error Step 2: Error while doing Then enter mobile number in Search Number text input field", e);
		}
	    }
	    
	    try {
		Thread.sleep(1000); // needed
	    } catch (InterruptedException e2) {}
	    
	    System.out.println("Doing Step 3");
	    // 3. Click on that Searched Contact
	    {
		try {
		    By xPath = By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div/div[2]/div/div/div/div[2]/div[1]/div[1]/div/div/span");
		    //By xPath = By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div/div[2]/div/div/div/div[2]/div[1]");
		    //By xPath = By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div/div[2]/div/div/div/div[2]/div[1]/div[1]"); 31dec25
		    WebElement searchedContactElement = driver.findElement(xPath);
		    actions.moveToElement(searchedContactElement).click().build().perform();
		} catch (Exception e) {
		    System.out.println("Error in Step 3");
		    throw new RuntimeException("Error Step 3: Error while doing Click on that Searched Contact", e);
		}
	    }

	    // sending list of msges for a contact
	    txtMsgs.forEach(txtMsg -> {
		System.out.println("Doing Step 4");
		// 4. on Right Hand Side Bottom a Message Window Opens, type text message in it
		{
		    try {
			By xPath = By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span/div/div/div/div[3]/div[1]/p");
			//By xPath = By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div/div[3]/div[1]/p"); //31dec25 
			WebElement messageInputElement = driver.findElement(xPath);
			// By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div[1]/div[2]/div[1]/p")
			// commented on 21 jun

			messageInputElement.sendKeys(txtMsg);
		    } catch (Exception e) {
			System.out.println("Error in Step 4");
			throw new RuntimeException(
				"Error Step 4: Error while doing on Right Hand Side Bottom a Message Window Opens, type text message in it",
				e);
		    }
		}

		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e1) {
		    throw new RuntimeException(e1);
		}
		System.out.println("Doing Step 5");
		// 5. Now click on Send Message Button. thats it :)
		{
		    try {
			By xPath = By.xpath(
				"//*[@id=\"main\"]/footer/div[1]/div/span/div/div/div/div[4]/div/span/button/div/div/div[1]/span");
			//By xPath = By.xpath(
			//	"//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div/div[4]/div/span/div/div/div[1]/div[1]/span"); // 31dec25
			WebElement sendMessageBtnElement = driver.findElement(xPath);
			// "//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div/div[4]/button/span" - 19 oct

			actions.moveToElement(sendMessageBtnElement).click().build().perform();
		    } catch (Exception e) {
			System.out.println("Error in Step 5");
			throw new RuntimeException("Error Step 5: Error while doing Now click on Send Message Button", e);
		    }
		}
	    });
	});

	System.out.println(
		"Done============================================================================================================");
    }

    @PostConstruct
    private void init() {
	System.out.println("Init Started============");
	//System.setProperty("webdriver.chrome.driver", "F:\\DocumentsInF\\wa\\chromedriver_win32\\chromedriver.exe");
	System.setProperty("webdriver.chrome.driver", "E:\\Program Files\\EclipseWorkspace\\spring-selenium-whatsapp\\src\\main\\resources\\chromedriver.exe");
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--start-maximized");
	driver = new ChromeDriver(options);
	driver.get("https://web.whatsapp.com/");
	System.out.println("Init Ending============");
	// driver.get("https://www.google.com/");
    }
    
    //@PostConstruct
    private void init1() throws Exception {
	System.out.println("Start");
	List<List<String>> records = new ArrayList<>();
	try (BufferedReader br = new BufferedReader(new FileReader("D:\\contacts.csv"))) {
	    String line;
	    while ((line = br.readLine()) != null) {
	        String[] values = line.split(",");
	        records.add(Arrays.asList(values));
	    }
	}
	System.out.println(records);
	System.out.println("End");
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringSeleniumWhatsappApplication.class, args);
    }

}

//return Arrays.asList(
//	new ContactDto("","7503931534", Arrays.asList("happy", ":-)")) //
	//, new ContactDto("8588990224", Arrays.asList("sad", ":-(")) //
	//, new ContactDto("7503931534", Arrays.asList("thumb down", "(n)")) //
	//, new ContactDto("8588990224", Arrays.asList("tounge", ":-p")) //
	//, new ContactDto("7503931534", Arrays.asList("dil", "<3")) //
	//, new ContactDto("8588990224", Arrays.asList("straight face", ":-|")) //
	//, new ContactDto("7503931534", Arrays.asList("ajeeb sa face", ":-\\")) //
	//, new ContactDto("8588990224", Arrays.asList("aankh maarna", ";-)")) //
	// , new ContactDto("7503931534", "sad") //
	// , new ContactDto("7503931534", "bye")//
//);

@Data
@AllArgsConstructor
class ContactDto {
    private String name;
    private String mobileNumber;
    private List<String> txtMsg;
}