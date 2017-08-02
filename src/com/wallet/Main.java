package com.wallet;


import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Main {

	private static WebDriver driver;
	private String modemWinHandle = null;
	private static MTSPage mtsPage;
	private static ModemPage modemPage;
	private static BalansPage balansPage;

	private static SmsPage smsPage;
	private static VKPage vkPage;

	private static double WinnersFond=0.8;
	private static double WinnersRate=0.9;



	public static void main(String[] args) throws Exception {

////////// test
/*
		driver = webDriver.getWebDriver();
		vkPage = new VKPage(driver);
		vkPage.login();
		String winnersNumber1="375297808382";
		String postText="Помощь оказана номеру "+winnersNumber1.substring(1,8)+"**"+winnersNumber1.substring(11,12)+" в размере 5";

		vkPage.postInGroup(postText);
*/

		//////////

	//	DBUtils.connectToBD();
//		DBUtils.saveSms("1","2","2017-04-27 15:44:04");


		System.out.println("Start app");

		DBUtils.connectToBD();

		
		

//try {
	driver = webDriver.getWebDriver();

	/*balansPage = new BalansPage(driver);
	int balans = (int) Math.floor(balansPage.getBalans()*WinnersFond);
	int prizFond =  (int) Math.floor(ThreadLocalRandom.current().nextInt(0, (int) balans )/WinnersRate*WinnersRate);
	System.out.println("Priz fund "+prizFond+ " cents");
	*/

	//while (true) {

// get new sms and save into database

		smsPage = new SmsPage(driver);
		smsPage.readSMS();

		DBUtils.processNewSms();
		String winnersNumber = DBUtils.getWinner();


		MTSPage mtsPage = new MTSPage(driver);

		mtsPage.login();
		sleep(1000);
		int jackPot=DBUtils.getJackPot();
		mtsPage.payToNumber(winnersNumber,jackPot);

		vkPage = new VKPage(driver);
		vkPage.login();
		if (MTSPage.successPaid) {
			vkPage.postInGroup("Помощь оказана номеру " + winnersNumber.substring(0, 8) + "**" + winnersNumber.substring(10, 12) + " в размере " + jackPot + " руб");
		} else{
			vkPage.postInGroup("Помощь будет оказана номеру " + winnersNumber.substring(0, 8) + "**" + winnersNumber.substring(10, 12) + " в размере " + jackPot + " руб");
		}
	//}
//}Помощь оказана номеру 7529788**1 в размере 13 руб
//catch (Throwable e){System.out.println(e.getStackTrace());}
//finally {
	driver.quit();
}


	}


//}
