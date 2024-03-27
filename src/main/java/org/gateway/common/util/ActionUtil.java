package org.gateway.common.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ActionUtil {

	public void scrollDown(WebDriver driver) {

		int numberOfPixelsToDragTheScrollbarDown = 250;
		int numberOfTotalPixelsPresentOnASinglePage = 5000;

		for (int i = 0; i < numberOfTotalPixelsPresentOnASinglePage; i += numberOfPixelsToDragTheScrollbarDown) {
			scroll(driver, 0, numberOfPixelsToDragTheScrollbarDown, Duration.ofSeconds(2));
		}

	}

	public void scrollUp(WebDriver driver) {
		int numberOfPixelsToDragTheScrollbarUp = -250;
		int numberOfTotalPixelsPresentOnASinglePage = 5000;

		for (int i = 0; i < numberOfTotalPixelsPresentOnASinglePage; i += numberOfPixelsToDragTheScrollbarUp) {
			scroll(driver, 0, numberOfPixelsToDragTheScrollbarUp, Duration.ofSeconds(1));
		}
	}

	public void scroll(WebDriver driver, int x, int y, Duration time) {

		try {

			Thread.sleep(time.toMillis());
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(" + x + "," + y + ")");

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
