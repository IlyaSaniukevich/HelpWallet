package main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by ilya_saniukevich on 30/03/2017.
 */
public class mts {

    @FindBy(css = ".popup-small")
    public WebElement popUpRegion;


    private static String MTS_URL="https://dengi.mts.by/";
    private String firstWinHandle = null;
    private String secondWinHandle = null;
    public void openHohePage(WebDriver driver){
        driver.get(MTS_URL);
    }

    public boolean paidReward(WebDriver driver){
        firstWinHandle = driver.getWindowHandle();

        driver.get(MTS_URL);
      //  return true;

      //  driver.get();
        return false;
    }


}
