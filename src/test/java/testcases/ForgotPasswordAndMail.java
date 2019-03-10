package testcases;

import java.io.FileNotFoundException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.eclipsesource.json.JsonObject;
import pageObjects.ComposeMail;
import pageObjects.Inbox;
import pageObjects.LoginPage;
import utility.JsonReader;

public class ForgotPasswordAndMail extends BaseClass {
	private JsonObject testData;

	public ForgotPasswordAndMail() {
		this.testData = JsonReader.jsonReader(this.getClass().getSimpleName()).asObject();
	}

	@DataProvider
	public Object[][] loginData() throws FileNotFoundException {
		Object[][] data = JsonReader.jsonObjectToObjectArray(this.testData, "invalidLogin");
		return data;
	}

	
	@Test(dataProvider = "loginData")
	public void loginWithForgotPassword(String username, String wrongPassword, String password) {
		LoginPage login = new LoginPage(driver);
		try {
			login.forgotPassword(username, wrongPassword,password);
			Assert.assertTrue(driver.getPageSource().contains("psgagneza"), username + "Loginned");
			Assert.assertTrue(driver.getTitle().contains("Gmail"), "Title expected Loginned");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] mailSend() throws FileNotFoundException {
		Object[][] data = JsonReader.jsonObjectToObjectArray(this.testData, "email");
		return data;
	}

	@Test(dependsOnMethods = { "loginWithForgotPassword" }, dataProvider = "mailSend")
	public void sendMail(String to, String subject, String body) throws InterruptedException {
		Inbox in = new Inbox(driver);
		in.navigateToCompose();
		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		ComposeMail cmp = new ComposeMail(driver);
		cmp.writeAndSendMail(to, subject, body);
		cmp.verifyMail(to, subject);

	}
}
