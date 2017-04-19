package main;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import main.MTSPage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;


public class main {

	private static WebDriver driver;
	private String modemWinHandle = null;
	private static MTSPage mtsPage;
	private static ModemPage modemPage;

	private static SmsPage smsPage;



	public static void main(String[] args) {
		System.out.println("Start app");

		BDUtils.connectToBD();
		DataBase.testDatabase();


	driver = webDriver.getWebDriver();
	//	while (true) {
			//modem.getNewSms(driver);
		smsPage= new SmsPage(driver);
		smsPage.readSMS();

	/*		mtsPage= new MTSPage(driver);
			mtsPage.openHomePage();
*/

	//	}

		driver.quit();

	}


}
