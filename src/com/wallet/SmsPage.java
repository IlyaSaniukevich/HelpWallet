package com.wallet;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.remote.ErrorCodes.TIMEOUT;

/**
 * Created by user on 03.04.2017.
 */

@FindBy(css="body")
public class SmsPage {

    private static String smsUrl = "http://192.168.8.1/html/smsinbox.html";


    private WebDriver driver;

    @FindBy(css = "#span_del_msg_btn")
    private WebElement smsDeleteButton;

    @FindBy(css = "#pop_confirm")
    private WebElement smsDeleteConfirmButton;



       @FindBy(css=".sms_list_tr")
    public List<WebElement> smsList;

    public SmsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.get(smsUrl);
    }


    public void readSMS()  {
        String phoneNumber;
        String TextMessage;
        String ReceiveTime;
        WebElement checkBox;
        Date date = new Date();
if(smsList.size()<1)return;
        for (WebElement sms : smsList) {

            phoneNumber = sms.findElement(By.cssSelector(".sms_phone_number")).getText();
            TextMessage = sms.findElement(By.cssSelector(".sms_content.clr_blue_a")).getText();
            ReceiveTime = sms.findElement(By.cssSelector(".td_pl.sms_date_width>label")).getText();
System.out.print("Get new sms from '"+phoneNumber+"' "+TextMessage+" DATE: '"+ReceiveTime+"'");

            try{
            DBUtils.saveSms(phoneNumber, TextMessage, ReceiveTime);
            System.out.println("Sms saved. Delete it");
            checkBox =  sms.findElement(By.cssSelector(".sms_td>input"));
            checkBox.click();

            }
            catch (SQLException e){
                System.out.println("Sms wasn't saved Exception "+e);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
           smsDeleteButton.click();
        waitUntilElementDisplayed(smsDeleteConfirmButton,driver);
        smsDeleteConfirmButton.click();
    }

    public void waitUntilElementDisplayed(final WebElement webElement, WebDriver driver) {

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        ExpectedCondition elementIsDisplayed = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver arg0) {
                try {
                    webElement.isDisplayed();
                    return true;
                }
                catch (NoSuchElementException e ) {
                    return false;
                }
                catch (StaleElementReferenceException f) {
                    return false;
                }
            }
        };
        wait.until(elementIsDisplayed);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}




