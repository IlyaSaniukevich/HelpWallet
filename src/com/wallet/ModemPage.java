package com.wallet;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.Button;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.remote.ErrorCodes.TIMEOUT;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class ModemPage {
    private WebDriver driver;

    private static String homeUrl="http://192.168.8.1/html/home.html";
    private static  String smsUrl ="http://192.168.8.1/html/smsinbox.html";
    private static  String ussdUrl ="http://192.168.8.1/html/ussd.html";

    private enum Method{
        IS_PRESENT, IS_VISIBLE;
    }

    private By locator;


    @FindBy(css=".content_right_sms")
    private SmsPage smsPage;

    @FindBy(css = ".popup-small")
    private WebElement popUpRegion;

    /*@FindBy(css = ".sms_list_tr")
    private SmsBlock smsBlock;*/

   public ModemPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }




    public void openHomePage(){
        driver.get(homeUrl);
    }


    /**
     * Set locator for page by value in @FindBy annotation
     * @throws Exception
     */
    private void setLocator() {
        Annotation[] annotations = this.getClass().getAnnotations();

        for(Annotation annotation : annotations){
            if (annotation instanceof FindBy){
                FindBy findBy = ((FindBy) annotation);

                if(!findBy.id().isEmpty()) {
                    locator = By.id(findBy.id());
                } else if(!findBy.linkText().isEmpty()) {
                    locator = By.linkText(findBy.linkText());
                } else if(!findBy.partialLinkText().isEmpty()) {
                    locator = By.partialLinkText(findBy.partialLinkText());
                } else if(!findBy.name().isEmpty()) {
                    locator = By.name(findBy.name());
                } else if(!findBy.tagName().isEmpty()) {
                    locator = By.tagName(findBy.tagName());
                } else if(!findBy.xpath().isEmpty()){
                    locator = By.xpath(findBy.xpath());
                } else if(!findBy.className().isEmpty()) {
                    locator = By.className(findBy.className());
                } else if(!findBy.css().isEmpty()) {
                    locator = By.cssSelector(findBy.css());
                } else {
                    throw new RuntimeException("Not supported locator type");
                }
            }
        }
    }




}

