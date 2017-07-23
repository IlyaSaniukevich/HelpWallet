package com.wallet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class modem{
    private static String START_URL="http://192.168.8.1/html/home.html";

    private WebDriver driver;

   /* public modem(WebDriver driver) {
              PageFactory.initElements(driver, this);
    }*/

    @FindBy(css=".content_right_sms")
    public SmsPage smsPage;

    public static void openModemHomePage(){
       // driver.get(START_URL);
    }

    public void verifyNewSms(){

    }

    public static void getNewSms(WebDriver driver){
        new SmsPage(driver).readSMS();
        //smsPage = new SmsPage(driver);
        //smsPage.readSMS();
    }


}
