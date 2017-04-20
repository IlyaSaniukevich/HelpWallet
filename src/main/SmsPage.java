package main;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 03.04.2017.
 */

@FindBy(css="body")
public class SmsPage {

    private static String smsUrl = "http://192.168.8.1/html/smsinbox.html";


    private WebDriver driver;

    @FindBy(css = "#span_del_msg_btn")
    private WebElement smsDeleteButton;

    @FindBy(css = ".sms_td>input")
    private WebElement smsCheckBox;

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
        Date date = new Date();
        //   driver.get(smsUrl);
        //  SmsPage
        // smsList.get(0).getSmsText();
     //   smsDeleteButton.click();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (WebElement sms : smsList) {

            phoneNumber = sms.findElement(By.cssSelector(".sms_phone_number")).getText();
            TextMessage = sms.findElement(By.cssSelector(".sms_content.clr_blue_a")).getText();
            ReceiveTime = sms.findElement(By.cssSelector(".td_pl.sms_date_width>label")).getText();

            try
            {
                date = simpleDateFormat.parse(ReceiveTime);
                System.out.println("date : "+simpleDateFormat.format(date));
            }
            catch (ParseException ex)
            {
                System.out.println("Exception "+ex);
            }
            try{
            DBUtils.saveSms(phoneNumber, TextMessage, date);}
            catch (SQLException e){
                System.out.println("Exception "+e);
            }

        }
    }


}




