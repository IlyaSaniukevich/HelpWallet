package com.wallet;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.Button;

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
public class BalansPage {

    private static String smsUrl = "http://192.168.8.1/html/ussd.html";


    private WebDriver driver;

    @FindBy(css="#bal")
    private WebElement checkBalans;

    @FindBy(css="#general_result")
    private WebElement result;

    @FindBy(css="#wait_table")
    private WebElement waitTable;


    public BalansPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.get(smsUrl);
    }


    public Double getBalans(){

        checkBalans.click();

        waitUntilElementDisplayed(waitTable,driver);
        waitUntilElementNotDisplayed(waitTable,driver);
        //  getBalans(result.getText());
        Double balans= getBalans(result.getText());
        System.out.println("Balans equals "+ balans);
        // WebDriverWait wait = new WebDriverWait(webDriver, timeoutInSeconds);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id<locator>));

        return balans*100;
    }


    public void waitUntilElementDisplayed(final WebElement webElement, WebDriver driver) {
System.out.println();
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


    public void waitUntilElementNotDisplayed(final WebElement webElement, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        ExpectedCondition elementIsDisplayed = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver arg0) {
                try {
                    webElement.isDisplayed();
                    return false;
                }
                catch (NoSuchElementException e ) {
                    return true;
                }
                catch (StaleElementReferenceException f) {
                    return true;
                }
            }
        };
        wait.until(elementIsDisplayed);
    }

    private static double getBalans(String text){
        Pattern pattern = Pattern.compile("=\\d+.\\d+(?=r)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()){
            String rezult=matcher.group();
            rezult=rezult.substring(1);
            return  Double.parseDouble(rezult);}
        return 0;
    }


}




