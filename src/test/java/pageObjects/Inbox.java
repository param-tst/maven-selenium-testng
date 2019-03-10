package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class Inbox {

	WebDriver driver;
	By composeLocator = By.xpath("//div[contains(text(),\"Compose\")]");
	WebElement composeButton;

	public Inbox(WebDriver driver) {
		this.driver = driver;
	}

	public void navigateToCompose() throws InterruptedException {
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		composeButton = driver.findElement(composeLocator);
		composeButton.click();
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
	}

}
