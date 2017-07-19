

package com.wallet;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class Selenium {


	
	
	private static ThreadLocal<WebDriver> DRIVER = new ThreadLocal<WebDriver>();
	
	public static boolean nativeEvents = false;

    public static WebDriver getWebdriver() {
    	return DRIVER.get();
    }

    /**
	 * By Default your Test which extends BaseTest will call this method to
	 * start Primary instance of WebDriver.
	 */
	public static void start(boolean nativeEvents) {
        try {
        	Selenium.nativeEvents = nativeEvents;

        	if (DRIVER.get() == null) {
                DRIVER.set(Driver.create());
            }
        } catch (Exception e) {

    		e.printStackTrace();
            throw new RuntimeException(e);
        }
        Browser.setWindowHandle();
	}

	public static void start() {
		start(nativeEvents);
	}

    /**
     * Your Test will call this method for close all opened browser's windows, stop working of browser and services.
     */
	public static void stop() {
		//if (isRunning) {  //Was used in case of simple test run
			try {
                //getWebdriver().quit();//Was used in case of simple test run
				if (DRIVER.get() != null) {
					DRIVER.get().quit();
			        DRIVER.remove();
			    }
			} catch (WebDriverException e) {
				//logger.warn("Error while quit driver: " + e.getMessage());
                if (System.getProperty("os.name").startsWith("Windows")/* && Config.remoteWebDriverURL == null*/) {
                    Process proc = null;
                    try {
                    	String browserProcessName = "firefox";
                    /*	switch (Config.browserType.toLowerCase()) {
                    	case "ie":
                    		browserProcessName = "iexplore";
                    		break;
                    	case "ch":
                    		browserProcessName = "chrome";
                    	}*/
                        proc = Runtime.getRuntime().exec(
                                String.format("taskkill /F /IM %1s.exe", browserProcessName));
                        proc.waitFor();
                        //Issue 3314:	WebDriver: Plugin Container for Firefox has stopped working.
                        proc = Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
                        proc.waitFor();
                        proc = Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
                        proc.waitFor();
                    } catch (Exception e1) {
                        throw new RuntimeException(e1);
                    }
                }
			}
		//	isRunning = false;
		//}
	}



	/**
	 * Delegator method, to be used instead of webdriver.get() as a workaround
	 * for http://code.google.com/p/selenium/issues/detail?id=4606
	 *
	 * @param url - URL to load
	 */
	public static void get(String url) {
	//	try {
			
            getWebdriver().get(url);
            
		/*} catch (TimeoutException e) {
			// stop FirefoxDriver and hell may care what happens next
			//if (Browser.isFF()) {
              //  stop();
            //}
			//throw e;
		//TODO try to avoid ChromeDriver issue - https://bugs.chromium.org/p/chromedriver/issues/detail?id=848	
		  // logger.error("Catching timeout exception! " + e.getMessage() + e.getStackTrace());
		   getWebdriver().navigate().refresh();
		}*/
	}



	/**
	 * By Default your Test which extends BaseTest will call this method to
	 * start Primary instance of WebDriver.
	 */
	public static void start(WebDriver driver) {
		try {
			DRIVER.set(driver);
		} catch (Exception e) {
			//logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		Browser.setWindowHandle();
	}

}

