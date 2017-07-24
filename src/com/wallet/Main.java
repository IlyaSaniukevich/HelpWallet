package com.wallet;


import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

	private static WebDriver driver;
	private String modemWinHandle = null;
	private static MTSPage mtsPage;
	private static ModemPage modemPage;
	private static BalansPage balansPage;

	private static SmsPage smsPage;
	private static double WinnersFond=0.8;
	private static double WinnersRate=0.9;



	public static void main(String[] args) throws Exception {

////////// test


		//////////

	//	DBUtils.connectToBD();
//		DBUtils.saveSms("1","2","2017-04-27 15:44:04");


		System.out.println("Start app");

		DBUtils.connectToBD();

		
		

try {
	driver = webDriver.getWebDriver();

	balansPage = new BalansPage(driver);
	int balans = (int) Math.floor(balansPage.getBalans()*WinnersFond);
	int prizFond =  (int) Math.floor(ThreadLocalRandom.current().nextInt(0, (int) balans )/WinnersRate*WinnersRate);
	System.out.println("Priz fund "+prizFond+ " cents");


// get new sms and save into database
	smsPage = new SmsPage(driver);
	smsPage.readSMS();

	DBUtils.processNewSms();
	String winnersNumber = DBUtils.getWinner();

	MTSPage

}
catch (Throwable e){System.out.println(e.getStackTrace());}
finally {
	driver.quit();
}


	}


}
