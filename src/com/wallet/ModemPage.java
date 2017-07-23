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

/*
    public void getNewSms(){
        driver.get(smsUrl);
        checkBalans.
    }
*/




    public void waitForNotVisible(int timeout, boolean isThrowException) throws Exception{
        waitForCondition(Method.IS_VISIBLE, false, timeout, isThrowException);
    }

    /**
     * Wrapper method to be called from methods of BasePage that implement waiting for something
     * @param method - method name
     * @param value - value to compare methodName's return value to
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    private void waitForCondition(Method method, boolean value, int timeout, boolean isThrowException) {
        if(locator == null){
            setLocator();
        }

        long finishTime = System.currentTimeMillis() + timeout;
        while(System.currentTimeMillis() < finishTime){
            boolean b = false;
            switch
                    (method){
                case IS_PRESENT:
                    b = isPresent();
                    break;
                case IS_VISIBLE:
                    b = isVisible();
                    break;
                default:
            }
            if(b & value){
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        if(isThrowException){
            throw new RuntimeException("Timed out after "+ (timeout / 1000) +" seconds waiting for '"+ locator +"' element");
        }
    }


    /**
     * Check if page exists
     * @return true if page exists and false otherwise
     * @throws Exception
     */
    public boolean isPresent() {
        if(locator == null){
            setLocator();
        }

        try {

            Selenium.getWebdriver().findElement(locator);
            return true;
        } catch (NoSuchElementException e){
            return false;
        } catch (TimeoutException e) {
            return false;
        }
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


    /**
     * Check if page visible
     * @return true if page visible and false otherwise
     * @throws Exception
     */
    public boolean isVisible() {
        if(locator == null){
            setLocator();
        }
        try {


            WebElement element = Selenium.getWebdriver().findElement(locator);
            return element.getSize().getHeight() > 0 && !element.getAttribute("style").toLowerCase().contains("display: none");
        } catch (NoSuchElementException e){
            return false;
        } catch (TimeoutException e2){
            return false;
        } finally {

        }
    }

}

