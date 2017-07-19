package com.wallet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.HtmlElement;


/**
 * Created by user on 03.04.2017.
 */

@FindBy (css=".sms_list_tr")
public class SmsBlock extends HtmlElement {
  //  private WebDriver driver;


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

}
