package com.wallet;

import com.google.common.base.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.net.HttpURLConnection.*;

/**
 * All common browser related operations
 */
public final class Browser {


    private static long loadingStartTime;
	private static long pageLoadTimeout = 5000;
	//private int pageLoadTimeout=5000;
	private static String host="192.168.8.1";
	private Browser() {
		// Utility class should not have a public or default constructor
	}

	private static String mainWindow = null;
    private static String windowHandle;

    private static String activeHwnd;
    private static int currentWinIndex = 0;

    /**
     * Alternate way of child window selecting. Added as a workaround for issue with closing Preview window.
     * <b>NOTE:</b> use together with {@link #closeCurrentWnd}, use only if {@link #closeWindow(String)} is not working
     */
    public static void selectChildWnd(){
        currentWinIndex = 0;
        activeHwnd = getNextWindow();
        Selenium.getWebdriver().switchTo().window(activeHwnd );
        Browser.waitDocumentReady();
    }

    /**
     * Alternate way of child window closing. Added as a workaround for issue with closing Preview window.
     * <b>NOTE:</b> use together with {@link #selectChildWnd}, use only if {@link #closeWindow(String)} is not working
     */
    public static void closeCurrentWnd(){
       Browser.closeWindow(activeHwnd);
       selectMainWindow();
    }

    public static void closeAllChildWnd(){
        for (int i=1; i < getWindowHandles().length; i++) {
            closeCurrentWnd();
        }
    }

    private static String getNextWindow(){
        currentWinIndex++;
        if (currentWinIndex >= Browser.getWindowHandles().length){
            currentWinIndex = 0;}
        return Browser.getWindowHandles()[currentWinIndex];
    }

	/**
	 * Reloads web page
	 */
	public static void refresh() {
        Selenium.getWebdriver().navigate().refresh();
		Browser.waitDocumentReady();
	}

	/**
	 * Goes Back a page
	 */
	public static void goBack() {
        Selenium.getWebdriver().navigate().back();
		Browser.waitDocumentReady();
	}

	/**
	 * Maximizes browser window
	 */
	public static void maximize() {
//		FrameManager.resetFrame();	//	workaround for WebDriver bug when a frame is selected
		/*if(!Browser.isChrome()){
			Selenium.getWebdriver().manage().window().maximize();
		}*/
	}

	/**
	 * Template for sync after refresh call.
	 */
	public static void waitForPageToLoad() {
		//Selenium.selenium.waitForPageToLoad(getProperty("defaultTimeout"));
		Browser.waitDocumentReady();	//	but it is not an adequate replacement
	}


	/***
	 * Gets the absolute URL of the current page.
	 * @return - the absolute URL of the current page
	 */
	public static String getLocation() {
		String currentUrl = "";
		try{
			currentUrl = Selenium.getWebdriver().getCurrentUrl();
		} catch (NullPointerException e){
		}
		return currentUrl;
	}

	public static String getPageTitle() {
		return Selenium.getWebdriver().getTitle();
	}



	public static void clearCookies() {
        Selenium.getWebdriver().manage().deleteAllCookies();
	}

	/**
	 * Restart the browser. Simulates closing and opening browser.
	 * Saves browser cookies and restore them after selenium webdriver restart.
	 * Can be useful to make logged user session to expire.
	 *
	 * @throws Exception
	 */
	public static void restartBrowser() throws Exception {
		// Current location
		String url = Browser.getLocation();
		// Save cookies
		Set<Cookie> cookies = Selenium.getWebdriver().manage().getCookies();

		// Restart selenium webdriver => close & open the browser
		Selenium.stop();
		Selenium.start();
		
		// Restore cookies
		for (Cookie cookie : cookies) {
			// Except the ones that expired at the end of previous session
			if(cookie.getExpiry() != null)
                Selenium.getWebdriver().manage().addCookie(cookie);
		}

		// Navigate to the same page as before closing the browser
        Selenium.getWebdriver().navigate().to(url);
		Browser.waitDocumentReady();
	}


	/**
	 * Returns is current browser is Internet Explorer 9. <br>
	 * Unlike {@link #isFF()} and {@link #isIE()}, this function
	 * checks browser's user agent string.
	 * @return is IE9
	 */
	public static boolean isIE9() {
		String userAgentStr = (String) ((JavascriptExecutor) Selenium.getWebdriver()).executeScript("return navigator.userAgent;");

		return userAgentStr.contains("Trident/5.0;");
	}

    /**
     * Returns is current browser is Internet Explorer 8. <br>
     * Unlike {@link #isFF()} and {@link #isIE()}, this function
     * checks browser's user agent string.
     * @return is IE8
     */
    public static boolean isIE8() {
        String userAgentStr = (String) ((JavascriptExecutor) Selenium.getWebdriver()).executeScript("return navigator.userAgent;");

        return userAgentStr.contains("Trident/4.0;");
    }

	public static void waitDocumentReady() {
		waitDocumentReady(pageLoadTimeout);
	}

	public static void waitDocumentReady(String timeout) {
		waitDocumentReady(Long.parseLong(timeout));
	}

	/**
	 * Wait for web page is loaded
	 * @param timeout - time for waiting
	 */
	public static void waitDocumentReady(long timeout) {
		loadingStartTime = System.currentTimeMillis();
		String state;
		//	ignoring exception which is thrown sometimes in IE9
		Wait<JavascriptExecutor> wait = new FluentWait<>((JavascriptExecutor) Selenium.getWebdriver())
				.withTimeout(timeout, TimeUnit.MILLISECONDS)
				.ignoring(WebDriverException.class)
				.pollingEvery(100, TimeUnit.MILLISECONDS);

		try{
			wait.until(new Function<JavascriptExecutor, Boolean>() {
				@Override
				public Boolean apply(JavascriptExecutor js) {
					return js.executeScript("return document.readyState;").equals("complete");

				}
			});
		}

		catch(TimeoutException e){
			//		sometimes document state remains interactive
			state = (String)((JavascriptExecutor) Selenium.getWebdriver()).executeScript("return document.readyState;");
			if(!state.equals("interactive")){
				throw new TimeoutException("Document state is " + state + ". Original message is: " + e.getMessage());
			}
		}
	}

    /**
     * Get start time of loading
     * @return - start time of loading
     */
    public static long getLoadingStartTime() {
        return loadingStartTime;
    }

    /**
	 * Store main window name/handle
	 * @param nameOrHandle - name Or Handle of main window
	 */
	public static void setMainWindow(String nameOrHandle){
		mainWindow = nameOrHandle;
	}

	/**
	 * Closes window with given ID with following main window select
	 * @param windowID - id of window to close
	 */
	public static void closeWindow(String windowID){
		selectWindow(windowID);
        Selenium.getWebdriver().close();
		selectMainWindow();
	}

	/**
	 * This method returns the id of the current window
	 */
    public static String getWindowHandle(){
        return windowHandle;//return current window
    }

    /**
     * This method get the id of the current window and set it to field
     */
    public static void setWindowHandle(){
        windowHandle = Selenium.getWebdriver().getWindowHandle();//getting driver with current window
    }

	/**
	 * Gets the current window inner size.
	 * @return the window inner size
	 */
	public static Dimension getWindowInnerSize() {
//		Hope this work for all browsers.
//		window.innerWidth/innerHeight not work in IE8-, and even in IE9+ it is not available in quirks mode
		int width = Integer.parseInt(((JavascriptExecutor) Selenium.getWebdriver()).executeScript("return document.documentElement.clientWidth;").toString());
		int height = Integer.parseInt(((JavascriptExecutor) Selenium.getWebdriver()).executeScript("return document.documentElement.clientHeight;").toString());
		return new Dimension(width, height);
	}

    /**
	 * Open URL in current window.
     * @throws AWTException 
     */
    public static void openPage() throws AWTException {
        Browser.maximize();
		Selenium.get(host);
    }

    /**
	 * Open URL in current window.
	 * @param url - the url
     * @throws AWTException 
     */
    public static void openPage(String url) throws AWTException {
        Browser.maximize();
		Selenium.get(url);
    }
    
	/**
	 * Open URL in new Window.
	 * @param url - the url
	 */
	public static String openWindow(String url){ 
		Set<String> oldHandles = Selenium.getWebdriver().getWindowHandles();
		((JavascriptExecutor) Selenium.getWebdriver()).executeScript(String.format("window.open('%1$s')", url));
		Set<String> newHandles = Selenium.getWebdriver().getWindowHandles();
        newHandles.removeAll(oldHandles);
        //Find the new window 
        for (String handle: newHandles)
            return handle;
        return null;
	}


	/**
	 * Open URL in new Window.
	 * @param url - the url
	 */
	public static String openWindow(String url, WebDriver driver){
		Set<String> oldHandles = driver.getWindowHandles();
		((JavascriptExecutor) driver).executeScript(String.format("window.open('%1$s')", url));
		Set<String> newHandles = driver.getWindowHandles();
		newHandles.removeAll(oldHandles);
		//Find the new window
		for (String handle: newHandles)
			return handle;
		return null;
	}


	/**
	 * Select window.
	 * @param windowID - the window id.
	 */
	public static void selectWindow(String windowID){
        if(windowID != null){
            Selenium.getWebdriver().switchTo().window(windowID);
        }
	}

	public static String[] getWindowHandles() {
		Set<String> handles = Selenium.getWebdriver().getWindowHandles();
		return handles.toArray(new String[handles.size()]);
	}

	/**
	 * Selects main window.
	 */
	public static void selectMainWindow(){
		mainWindow = getWindowHandles()[0];
        Selenium.getWebdriver().switchTo().window(mainWindow);
	}

	/**
	 * Selects second window.
	 */
	public static void selectChildWindow(){
	//	Assert.assertTrue(Selenium.getWebdriver().getWindowHandles().size() > 1, "There is only one window. No any child window.");
		String secondHwnd = getWindowHandles()[1];
        Selenium.getWebdriver().switchTo().window(secondHwnd);
	}

	/**
	 * Selects window by index.
	 * @param index - index of window to be selected (starts with 0)
	 */
	public static void selectWindow(int index){
	//	Assert.assertTrue(Selenium.getWebdriver().getWindowHandles().size()>index, "There is no window with index: "+index);
		String secondHwnd = getWindowHandles()[index];
        Selenium.getWebdriver().switchTo().window(secondHwnd);
	}

/*	*//**
	 * Verify that alert is shown. If true - accept it, false - no any action.
	 *//*
	public static String checkAlert() {
		return checkAlert(2);
	}*/

	/**
	 * Verify that alert is shown. If true - accept it, false - no any action.
	 *//*
	public static String checkAlert(int timeOut) {
		String alertMessage = null;
		try {
			WebDriverWait wait = new WebDriverWait(Selenium.getWebdriver(), timeOut);
			wait.until(ExpectedConditions.alertIsPresent());
			DataUtils.sleep(5);//DataUtils.sleep(0.5);
			Alert alert = Selenium.getWebdriver().switchTo().alert();
			alertMessage = alert.getText();
			alert.accept();
		} catch (Exception e) {
			//exception handling
		}
		return alertMessage;
	}

	*//**
	 * Is alert is shown
	 *//*
	public static Alert isAlertPresent() {
		Alert alert = null;
		try {
			WebDriverWait wait = new WebDriverWait(Selenium.getWebdriver(), 2);
			alert = wait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
		}
		return alert;
	}
*/
	/**
	 * Verify that url is available
	 * @param urlLink - http URL
	 * @return - true - if url returns 200 or 302 codes, false - otherwise
	 */
	public static boolean isURLAvailable(String urlLink){
		boolean isURLAvailable = false;
		HttpURLConnection huc = null;
		try {
			setFollowRedirects(false);
			huc = (HttpURLConnection) new URL(urlLink).openConnection();
			huc.setRequestMethod("HEAD");
			// huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
			huc.connect();
			int responseCode = huc.getResponseCode();
			isURLAvailable = (responseCode == HTTP_OK || responseCode == HTTP_MOVED_TEMP);
			if(!isURLAvailable){
				huc.connect();
				isURLAvailable = (responseCode == HTTP_OK || responseCode == HTTP_MOVED_TEMP);
			}
		} catch (Exception e) {
			//exception handling
		} finally {
			if(huc != null){
				huc.disconnect();
			}
		}
		return isURLAvailable;
	}
}
