package com.wallet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class ModemPage {
    private WebDriver driver;

    private static String homeUrl="http://192.168.8.1/html/home.html";
    private static  String smsUrl ="http://192.168.8.1/html/smsinbox.html";
    private static  String ussdUrl ="http://192.168.8.1/html/ussd.html";

    @FindBy(css=".content_right_sms")
    private SmsPage smsPage;

    @FindBy(css = ".popup-small")
    private WebElement popUpRegion;

    /*@FindBy(css = ".sms_list_tr")
    private SmsBlock smsBlock;*/

/*    public ModemPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }*/

    public void openHomePage(){
        driver.get(homeUrl);
    }

    public void getNewSms(){
        driver.get(smsUrl);

    }


}
