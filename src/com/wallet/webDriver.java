package com.wallet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;


public class webDriver {

    private static WebDriver driver;
	
	public static WebDriver getWebDriver(){

	    if (driver==null){
	        return driver = createDriver();
        }else{
	        return driver;
        }

	}
/*

	public static WebDriver createDriverCh(){
        DesiredCapabilities capabilities;
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
      //  chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("chrome.switches","--disable-extensions");
        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return new ChromeDriver(capabilities);
    }
*/




    public static WebDriver createDriver(){
        //  if (System.getProperty("os.name" ).contains("win"))
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        // else System.setProperty("webdriver.gecko.driver", "geckodriver");

        ProfilesIni profile = new ProfilesIni();
        //  FirefoxProfile firefoxProfile =profile.getProfile("Default1");
        FirefoxProfile firefoxProfile =new FirefoxProfile();

        firefoxProfile.setPreference("network.proxy.type", 1);
        firefoxProfile.setPreference("network.proxy.http", "192.168.1.13");
        firefoxProfile.setPreference("network.proxy.http_port", 3128);
        firefoxProfile.setPreference("network.proxy.default", true);


        //   firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.8.4"); // Avoid startup screen

        driver = new FirefoxDriver(firefoxProfile);
        //  driver = new FirefoxDriver();

        return driver;
    }


    public void stopDriver(){
	 if (driver!=null)
	     driver.quit();
    }
}
