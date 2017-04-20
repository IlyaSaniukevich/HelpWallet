package main;


import org.openqa.selenium.WebDriver;


public class main {

	private static WebDriver driver;
	private String modemWinHandle = null;
	private static MTSPage mtsPage;
	private static ModemPage modemPage;

	private static SmsPage smsPage;



	public static void main(String[] args) throws Exception {
		System.out.println("Start app");

	//	DBUtils.connectToBD();



	driver =webDriver.createDriverFF();
			//webDriver.getWebDriver();
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
