package main;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;


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


    public static WebDriver createDriverFF(){
    if (System.getProperty("os.name" ).contains("win"))
        System.setProperty("webdriver.gecko.driver", "src/resources/geckodriver.exe");
    else System.setProperty("webdriver.gecko.driver", "geckodriver");

        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile firefoxProfile = profile.getProfile("default");
        firefoxProfile.setPreference("network.proxy.type", 1);
        firefoxProfile.setPreference("network.proxy.http", "192.168.1.13");
        firefoxProfile.setPreference("network.proxy.http_port", 3128);
        firefoxProfile.setPreference("network.proxy.default", true);

    //   firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.8.4"); // Avoid startup screen

       // driver = new FirefoxDriver(firefoxProfile);
        driver = new MarionetteDriver();

        return driver;
    }

    public static WebDriver createDriverFFNewProfile(){
        if (System.getProperty("os.name" ).contains("win"))
            System.setProperty("webdriver.gecko.driver", "src/resources/geckodriver.exe");
        else System.setProperty("webdriver.gecko.driver", "geckodriver");

        //ProfilesIni profile = new ProfilesIni();
        DesiredCapabilities dc=DesiredCapabilities.firefox();
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("network.proxy.type", 1);
        firefoxProfile.setPreference("network.proxy.http", "192.168.1.13");
        firefoxProfile.setPreference("network.proxy.http_port", 3128);
        firefoxProfile.setPreference("network.proxy.default", true);

        dc.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        dc.setCapability("marionette", true);
        driver = new MarionetteDriver(dc);

        return driver;
    }

    public static WebDriver createDriver(){
    DesiredCapabilities capability = DesiredCapabilities.firefox();
    capability.setCapability("platform", Platform.WINDOWS);
    capability.setCapability("binary", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");


    driver = new FirefoxDriver(capability);
    return driver;
    }


    public void stopDriver(){
	 if (driver!=null)
	     driver.quit();
    }
}
