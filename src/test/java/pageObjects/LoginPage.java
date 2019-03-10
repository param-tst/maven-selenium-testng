package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	private By userIdLocater = By.xpath("//input[@id='identifierId']");
	private By nextButtonLocater = By.xpath("//span[contains(text(),'Next')]");
	private By passwordLocater = By.xpath("//input[@name='password']");
	private By forgotPasswordLocater = By.xpath("//div[@id=\"forgotPassword\"]/parent::*");
	private By continueLocater = By.xpath("//a[contains(text(),'Continue')]");
	private WebElement userId;
	private WebElement nextButton;
	private WebElement password;
	private WebElement forgotPassword;
	private WebElement continueButton;

	public void loginApplication(String user, String pass) throws InterruptedException {
		userId = driver.findElement(userIdLocater);

		userId.click();
		userId.sendKeys(user);
		nextButton = driver.findElement(nextButtonLocater);
		nextButton.click();
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 20);
		password = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocater));
		password.click();
		password.sendKeys(pass);
		wait.until(ExpectedConditions.stalenessOf(nextButton));
		nextButton = driver.findElement(nextButtonLocater);
		nextButton.click();

	}

	public void forgotPassword(String user,String wrongPassword, String pass) throws InterruptedException {
		
		userId = driver.findElement(userIdLocater);
		userId.click();
		userId.sendKeys(user);
		nextButton = driver.findElement(nextButtonLocater);
		nextButton.click();
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		password = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocater));
		password.click();
		password.sendKeys(wrongPassword);
		wait.until(ExpectedConditions.stalenessOf(nextButton));
		nextButton = driver.findElement(nextButtonLocater);
		nextButton.click();
		Actions builder = new Actions(driver);
		try {
			for(int i=0;i<2;i++) {
			forgotPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordLocater));
			builder.moveToElement(forgotPassword).click().build().perform();
			if(driver.getPageSource().contains("Try another way"))
				break;
			}
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			forgotPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordLocater));
			builder.moveToElement(forgotPassword).click().build().perform();
		}
		
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("loaded")
			            || ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
						
			}
		};
		
		try {
			wait.until(ExpectedConditions.stalenessOf(password));
		}
		catch(org.openqa.selenium.TimeoutException ex)
		{
			password = wait.until(ExpectedConditions.elementToBeClickable(passwordLocater));
		    
		}catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			password = wait.until(ExpectedConditions.elementToBeClickable(passwordLocater));
		    
		}finally {
			wait.ignoring(StaleElementReferenceException.class);
			password = wait.until(ExpectedConditions.elementToBeClickable(passwordLocater));
}
		
		builder.moveToElement(password).click().sendKeys(pass).build().perform();
		
		try {
		wait.until(ExpectedConditions.stalenessOf(nextButton));
		}catch(org.openqa.selenium.TimeoutException ex)
		{
			nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(nextButtonLocater));   
		}catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			wait.ignoring(StaleElementReferenceException.class);
			nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(nextButtonLocater));
		    
		}
		
		builder.moveToElement(nextButton).click().build().perform();
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		continueButton = driver.findElement(continueLocater);
		continueButton.click();

	}

}
