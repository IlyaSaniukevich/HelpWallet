package com.wallet;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.remote.ErrorCodes.TIMEOUT;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class MTSPage {
    public static boolean successPaid=true;
    private static WebDriver driver;
    private static String homeUrl="https://dengi.mts.by/";
    private static String payUrl="https://dengi.mts.by/payments";
    private static String loginPhone="298016016";
    private static String loginPassword="6122";

    @FindBy(css = ".popup-small")
    private WebElement popUpRegion;

    @FindBy(css = ".mts-button.--yes")
    private WebElement popUpRegionAgree;

    @FindBy(css = "#login-phone")
    private WebElement inputLogin;

    @FindBy(css = "#login-password")
    private WebElement inputPassword;

    @FindBy(css = ".-max-width.mts-button")
    private WebElement loginButton;

    @FindBy(css = ".form-mask_item.form-group_control.--mask-field")
    private WebElement phoneNumberInput;

    @FindBy(css = ".mts-button.-payments-mts-button")
    private WebElement nextAfterPhoneNumber;

    @FindBy(css = ".form-group_control[name=sum]")
    private WebElement inputSumma;

    @FindBy(css = ".mts-button.-payments-mts-button")
    private WebElement confirmPayment;

    @FindBy(css = ".payment-icon_img[alt='МТС']")
    private WebElement payToMTS;


    @FindBy(css = ".payment-icon.-service.services-list-block_logo[title='МТС по номеру телефона']")
    private WebElement payToNumberMTS;



    String cssLoginButton=".-max-width.mts-button";


    public MTSPage(WebDriver driver) throws InterruptedException {
        PageFactory.initElements(driver,this);
        this.driver=driver;
        driver.get(homeUrl);

        closePopUpRegion();
    }


    private void closePopUpRegion() throws InterruptedException {

       // waitUntilElementDisplayed(popUpRegion,driver);
sleep(2000);
        System.out.println("Check mts popup");
if (driver.findElements(By.cssSelector(".popup-small")).size() > 0)
        //        popUpRegion.isDisplayed())
        {
        try{
            System.out.println("Popup exists close it");
            popUpRegionAgree.click();}
        catch (Throwable e) {
                System.out.println("Exception " + e);

            }
        }
    }

    public void login() throws InterruptedException {
        System.out.println("Login to mts");
        inputLogin.sendKeys(loginPhone);
        inputPassword.sendKeys(loginPassword);
        sleep(1000);
        System.out.println("Click login mts button");
        loginButton.click();
        waitUntilElementNotDisplayed(loginButton,driver);
    }

    public void payToNumberFewTimes(String phoneNumber, int summa) throws SQLException, InterruptedException {

        int winnerNN = DBUtils.saveWinner(phoneNumber,summa);
        DBUtils.reduceFromJackPot(summa);
        DBUtils.deleteFromDeposit(phoneNumber,summa,winnerNN);

        successPaid=false;
        int i=0;
        while ((i<3)&&(!successPaid)) {
            payToNumber(phoneNumber, summa);
            i++;
        }
    }

public boolean payToNumber(String phoneNumber, int summa) throws SQLException, InterruptedException {
    System.out.println("Try to pay");
    double sumInRub=0;
    try {
        driver.get(payUrl);
        payToMTS.click();
        waitUntilElementDisplayed(payToNumberMTS, driver);
        payToNumberMTS.click();
        waitUntilElementDisplayed(phoneNumberInput, driver);

  /*      for (int i=3;i<12;i++){
            phoneNumberInput.sendKeys(phoneNumber.substring(i,i+1));
            sleep(1000);
        }
*/
        phoneNumberInput.sendKeys(phoneNumber.substring(3).trim());



        sleep(1000);
        nextAfterPhoneNumber.click();
        sleep(5000);

        try {
            waitUntilElementDisplayed(inputSumma, driver);
        }catch (Throwable e){
            System.out.println("Error after enter number. Try again");
            phoneNumberInput.click();
            phoneNumberInput.clear();
            for (int i=3;i<12;i++){
                phoneNumberInput.sendKeys(phoneNumber.substring(i,i+1));
                sleep(1000);
            }
            WebElement element = driver.findElement(By.cssSelector(".mts-button.-payments-mts-button"));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);

           // nextAfterPhoneNumber.click();
            sleep(1000);
        }
        try {
        waitUntilElementDisplayed(inputSumma, driver);

        }catch (Throwable e){
            System.out.println("Can't pay "+summa+" to number "+phoneNumber);
        }
        sumInRub = summa;
        inputSumma.sendKeys(String.valueOf(sumInRub));
        sleep(1000);
        waitUntilElementDisplayed(confirmPayment,driver);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmPayment);
        confirmPayment.click();

        String disabled = confirmPayment.getAttribute("disabled");
        int i=0;
        while((disabled ==null)&&(i<50)){
            disabled = confirmPayment.getAttribute("disabled");
            sleep(500);
            i++;
        }
        confirmPayment.click();

        System.out.println("Paid suscessfuly");
    }catch (Throwable e){
        System.out.println("Paid Error");
        successPaid=false;
    }


    return true;
}

    public static void waitUntilElementNotDisplayed(final WebElement webElement, WebDriver driver) {
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
