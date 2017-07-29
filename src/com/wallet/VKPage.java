package com.wallet;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.lang.annotation.Annotation;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class VKPage {
    private WebDriver driver;

    private static String homeUrl="https://vk.com/";
    private static  String groupUrl ="https://vk.com/belarusmtshelp";


    private enum Method{
        IS_PRESENT, IS_VISIBLE;
    }

    private By locator;


    @FindBy(css="#page_wrap")
    private VKPage vkPage;

    @FindBy(css = "#index_email")
    private WebElement loginInput;

    @FindBy(css = "#index_pass")
    private WebElement passwInput;

    @FindBy(css = "#index_login_button")
    private WebElement loginButton;

    @FindBy(css = "#send_post")
    private WebElement sendPost;

    @FindBy(css = "#post_field")
    private WebElement postInput;


    public VKPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
        driver.get(homeUrl);
    }






 public void login(){
      loginInput.sendKeys(Passw.loginVK);
     passwInput.sendKeys(Passw.passwVK);
     loginButton.click();
     MTSPage.waitUntilElementNotDisplayed(passwInput,driver);

 }


    public void postInGroup(String postText) {
      driver.get(groupUrl);
     postInput.sendKeys(postText);
        sendPost.click();
    }
}

