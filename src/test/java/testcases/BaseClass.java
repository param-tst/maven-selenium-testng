package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import factories.BrowserFactory;
import factories.DataProviderFactory;

public class BaseClass {

	WebDriver driver;

	@BeforeSuite
	public void startBrowser() {

		this.driver = BrowserFactory.startBrowser(DataProviderFactory.getConfig().getBrowser(),
				 DataProviderFactory.getConfig().getURL());
	}

	@AfterSuite
	public void closeBrowser() {

		this.driver.quit();

	}
}
