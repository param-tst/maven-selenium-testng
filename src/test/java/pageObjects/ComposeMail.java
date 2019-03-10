package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ComposeMail {

	private WebDriver driver;
	private By toLocator = By.xpath("//*[@name=\"to\"]");
	private By subjectLocator = By.xpath("//*[@name=\"subjectbox\"]");
	private By messageBodyLocator = By.xpath("//div[@aria-label=\"Message Body\"]");
	private By sendButtonLocator = By.xpath("//div[contains(text(),\"Send\")]");

	private By mail(String name) {
		return By.xpath("(//span[@email='" + name + "']/ancestor::div[1])[2]");
	}

	private By mailSubject(String subject) {
		return By.xpath("//h2[contains(text(),'" + subject + "')]");
	}

	private By inboxLocator = By.xpath("//a[@title=\"Inbox\"]");

	private WebElement to;
	private WebElement subject;
	private WebElement messageBody;
	private WebElement sendButton;
	private WebElement inbox;
	private WebElement mailReceivedInbox;
	private WebElement subjectInMail;

	public ComposeMail(WebDriver driver) {
		this.driver = driver;
	}

	public void writeAndSendMail(String toStr, String subjectStr, String bodyStr) throws InterruptedException {

		to = driver.findElement(toLocator);
		to.sendKeys(toStr);

		subject = driver.findElement(subjectLocator);
		subject.sendKeys(subjectStr);

		messageBody = driver.findElement(messageBodyLocator);
		messageBody.sendKeys(bodyStr);

		sendButton = driver.findElement(sendButtonLocator);
		sendButton.click();
		driver.switchTo().defaultContent();
	}

	public void verifyMail(String emailStr, String subjectStr) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		inbox = driver.findElement(inboxLocator);

		try {
			
			mailReceivedInbox = wait.until(ExpectedConditions.visibilityOfElementLocated(mail(emailStr)));
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			try {
				wait.until(ExpectedConditions.stalenessOf(mailReceivedInbox));
		}catch (org.openqa.selenium.TimeoutException sub) {
			mailReceivedInbox = wait.until(ExpectedConditions.visibilityOfElementLocated(mail(emailStr)));
		}
	}

		Assert.assertTrue(mailReceivedInbox.isDisplayed(), emailStr + subjectStr + "mail link is not found in inbox");

		mailReceivedInbox.click();
		subjectInMail = driver.findElement(mailSubject(subjectStr));
		Assert.assertTrue(subjectInMail.getText().contains(subjectStr), subjectStr + "mail content doesn't match");
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.stalenessOf(inbox));
		inbox = driver.findElement(inboxLocator);
		inbox.click();

	}

}
