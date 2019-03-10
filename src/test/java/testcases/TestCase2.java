package testcases;


import org.testng.annotations.Test;
import com.eclipsesource.json.JsonObject;
import pageObjects.LoginPage;


public class TestCase2 extends BaseClass {
	JsonObject testData;

	
	@Test
	public void apple2() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.forgotPassword("psgagneza","pass", "Patiala@140");
	}
}
