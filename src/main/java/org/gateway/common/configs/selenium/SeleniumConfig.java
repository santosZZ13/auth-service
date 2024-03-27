package org.gateway.common.configs.selenium;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

//	@Bean
//	public WebDriver webDriver() {
//		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//		WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver();
////		driver.navigate().to("https://8xbet249.com/lottoTicket#/lottery/privnvnffc?lotteryCategoryID=13201");
//		return driver;
//	}

//	@Bean()
//	public WebDriver aiscoreWebDriver() {
//		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//		WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver();
//		driver.navigate().to("https://www.aiscore.com/");
//		return driver;
//	}


//	@Bean("webDriverWait")
//	public Wait<WebDriver> webDriverWait() {
//		return new FluentWait<>(aiscoreWebDriver())
//				.withTimeout(Duration.ofSeconds(10))
//				.pollingEvery(Duration.ofSeconds(1))
//				.ignoring(NoSuchElementException.class)
//				.ignoring(StaleElementReferenceException.class)
//				.ignoring(TimeoutException.class)
//				.ignoring(WebDriverException.class)
//				.withMessage("Element was not found by locator");
//	}

}
