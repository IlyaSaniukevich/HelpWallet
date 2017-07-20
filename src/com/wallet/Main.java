package com.wallet;


import org.openqa.selenium.WebDriver;

public class Main {

	private static WebDriver driver;
	private String modemWinHandle = null;
	private static MTSPage mtsPage;
	private static ModemPage modemPage;

	private static SmsPage smsPage;



	public static void main(String[] args) throws Exception {

	//	DBUtils.connectToBD();
//		DBUtils.saveSms("1","2","2017-04-27 15:44:04");

	/*	System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://mail.ru/");
*/
		System.out.println("Start app");

		DBUtils.connectToBD();

		
		

try {
	driver = webDriver.getWebDriver();


// get new sms and save into database
	smsPage = new SmsPage(driver);
	smsPage.readSMS();

	DBUtils.processNewSms();


}
catch (Throwable e){System.out.println(e.getStackTrace());}
finally {
	driver.quit();
}


	}


}
