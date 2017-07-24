package com.wallet;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class MTSPage {
    private static WebDriver driver;
    private static String homeUrl="https://dengi.mts.by/";
    private static String payUrl="https://dengi.mts.by/payments/pay?id=10000393931";
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

    String cssLoginButton=".-max-width.mts-button";


    public MTSPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
        driver.get(homeUrl);
        closePopUpRegion();
    }


    public void sendMoneyToNumber(String number){


    }

    private void closePopUpRegion(){
        if (popUpRegion.isDisplayed()){
            popUpRegionAgree.click();
        }
    }

    public void login(){
        inputLogin.sendKeys(loginPhone);
        inputPassword.sendKeys(loginPassword);
        loginButton.click();
     //   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 15);
     //   WebElement element = wait.until(ExpectedConditions.invisibilityOf(loginButton));
        driver.get(payUrl);

    }

public boolean payToNumber(String phoneNumber, int summa){
    phoneNumberInput.sendKeys(phoneNumber);
    nextAfterPhoneNumber.click();
}
}
