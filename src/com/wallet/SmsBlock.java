package com.wallet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by user on 03.04.2017.
 */

@FindBy (css=".sms_list_tr")
public class SmsBlock extends HtmlElement {
  //  private WebDriver driver;
    public static String IssaPhoneNumber="0870";

    @FindBy (css=".sms_td")
    WebElement checkBox;

    @FindBy (css=".td_pl.sms_content_width")
    WebElement smsContent;

    @FindBy (css=".td_pl.sms_date_width")
    WebElement smsDate;


   /* public SmsBlock (WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }*/

    public  String getSmsText(){
        return smsContent.getText();
    }

    public  String getSmsDate(){
        return smsDate.getText();
    }

    public void selectSms(){
        checkBox.click();
    }

    public static String getNumberFromSMS(String sms){
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(sms);
        if (matcher.find()){return matcher.group();}
        return null;
    }

    public static int getSummaFromSMS(String sms){
        Pattern pattern = Pattern.compile("\\d(?=rub)");
        Matcher matcher = pattern.matcher(sms);
        if (matcher.find()){return  Integer.parseInt(matcher.group())*100;}
        return 0;
    }

    public static Boolean isSmsCorrect(String sms){
        if ((getSummaFromSMS(sms)!=0)&&(getNumberFromSMS(sms)!=null))
            return true;
        return false;
    }
}
