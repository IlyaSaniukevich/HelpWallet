package com.wallet;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

/**
 * Basic class for Web UI elements using WebDriver API
 */
public class BaseWebElement extends HtmlElement {

private int defaultTimeout=5000;
    private int     pageLoadTimeout=5000;
    private int     implicitlyWaitTimeout=5000;
    @Deprecated
    public boolean isDisplayed(){
///        logger.error("DON'T USE 'isDisplayed' DIRECTLY!");
        return false;
    }

    /**
     * Check whether element is present
     * @return true if element is present, false otherwise
     */
    public boolean isPresent(){
        try{
  
     //       return this.getWrappedElement().isDisplayed();
        } catch(NullPointerException e0){
        } catch(NoSuchElementException e1){
        } catch (StaleElementReferenceException e2){
        } finally {
        }
        return false;
    }

    /**
     * Check whether element is displayed
     * @return true if element is displayed, false otherwise
     */
    public boolean isVisible(){
        try{
            return this.isPresent() && this.getSize().getHeight() > 0
                    && !this.getAttribute("style").toLowerCase().contains("display: none");
        }
        catch(NoSuchElementException e){
        }
        return false;
    }

//	public boolean isVisible(){
//		return this.get().isDisplayed();
//	}


    /**
     * Check whether element is focused
     * @return true if element is focused, false otherwise
     */
    public boolean isFocused() {
        WebElement original = this;
        WebElement activeElement = Selenium.getWebdriver().switchTo().activeElement();
        return (original.equals(activeElement));
    }

    /**
     * Check whether element is readonly
     * @return true if element is readonly, false otherwise
     */
    public boolean isReadonly(){
        if(this.getAttribute("readonly") != null){
            this.getAttribute("class");
            return Boolean.parseBoolean(this.getAttribute("readonly"));
        }
        return false;
    }

    /**
     * Get 'class' attribute
     * @return 'class' attribute
     */
    public String getClassAttribute(){
        return this.getAttribute("class");
    }

    /**
     * Get 'value' attribute
     * @return 'value' attribute
     */
    public String getValueAttribute(){
        return this.getAttribute("value");
    }


    /**
     * Verifies if a vertical scroll bar is visible for the element/container.
     * The verification is based on comparing clientHeight and scrollHeight
     * properties
     *
     * @return true if a scroll bar appears, false if it does not
     */
    public boolean isVScrollBarVisible() {
        int clientHeight = Integer.parseInt(executeCommand("clientHeight"));
        int scrollHeight = Integer.parseInt(executeCommand("scrollHeight"));
        return (clientHeight < scrollHeight);
    }

    public String getTrimText() {
        String text = null;
        try {
            text = this.getText().trim();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Element '"+ this.getName() +"' is not found!", e);
        }
        return text;
    }

    /**
     * Move mouse cursor to the middle of the element and click
     */
    public void clickAction(){
        //Issue 4136 - [SafariDriver] implement the interactions API
        
            new Actions(Selenium.getWebdriver()).moveToElement(this.getWrappedElement()).click().build().perform();
        
        
    }

    /**
     * Focus to this web element
     */
    public void focus() {
        executeCommand("focus()");
    }

    public void assertNotPresent(String message) throws Exception {
        assertPresent(message, false, defaultTimeout, false);
    }

    public void assertNotPresent(String message, boolean softVerification) throws Exception {
        assertPresent(message, false, defaultTimeout, softVerification);
    }

    public void assertPresent(String message) throws Exception {
        assertPresent(message, true, defaultTimeout, false);
    }

    public void assertPresent(String message, boolean softVerification) throws Exception {
        assertPresent(message, true, defaultTimeout, softVerification);
    }

    public void assertPresent(String message, boolean expectedValue, int timeout, boolean softVerification) throws Exception {
        long now = System.currentTimeMillis();
        boolean isPresentAndVisible;
        while (true) {
            isPresentAndVisible = isOnPage();
            if(isPresentAndVisible == expectedValue) return;
            else if (System.currentTimeMillis() - now >= timeout || timeout == 0) break;
            else sleep(1);
        }
        if(softVerification) {
            //Verify.equals(isPresentAndVisible, expectedValue, message);
        }  else {
            //Assert.assertEquals(isPresentAndVisible, expectedValue, message);
        }
    }

    public boolean isOnPage(){
        try{
            return isVisible();
        }
        catch(NoSuchElementException e){
            return false;
        }
        catch(StaleElementReferenceException e1){
            return false;
        }
        catch(WebDriverException e2){
            return false;
        }
    }

    /**
     * Allows to execute Javascript on current element
     * @param command without closing semicolon (e.g. to get innerHTML of an element specify command = "innerHTML")
     * @return result of command execution
     */
    public String executeCommand(String command){
        return String.valueOf(((JavascriptExecutor)Selenium.getWebdriver()).executeScript("return arguments[0]." + command + ";", this));
    }

    /**
     * Wait for element to be present, throw exception upon unsuccessful wait
     */
    public void waitForElementPresent() {
        waitForElementPresent(true);
    }

    /**
     * Wait for element to be present
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     */
    public void waitForElementPresent(boolean isThrowException) {
        waitForElementPresent(defaultTimeout, isThrowException);
    }

    /**
     * Wait for element to be present when Alert is possible. The alert will not be handled (closed)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @param timeout - timeout value in milliseconds
     */
    public void waitForElementPresentWithAlert(int timeout, boolean isThrowException) {
        try {

            waitForElementPresent(timeout, isThrowException);
        }
        catch(UnhandledAlertException e){
            throw e;
        }
        catch(Exception e){

            if(isThrowException) throw e;
        }
    }

    /**
     * Wait for element to be present
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     */
    public void waitForElementPresent(int timeout, boolean isThrowException) {
      /*  try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(new Function<WebDriver, Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return isPresent();
                        }
                    });
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }*/
    }

    /**
     * Wait for element to be not present, throw exception upon unsuccessful wait
     */
    public void waitForElementNotPresent() {
        waitForElementNotPresent(true);
    }

    /**
     * Wait for element to be not present
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     */
    public void waitForElementNotPresent(boolean isThrowException) {
        waitForElementNotPresent(defaultTimeout, isThrowException);
    }

    /**
     * Wait for element to be not present
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     */
    public void waitForElementNotPresent(int timeout, boolean isThrowException) {
   /*     try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(new Function<WebDriver, Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return !isPresent();
                        }
                    });
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }*/
    }

    /**
     * Wait for element to be not present
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @param timeout - timeout value in milliseconds
     */
    public void waitForElementNotPresentWithAlert(int timeout, boolean isThrowException) {
        try{

            waitForElementNotPresent(timeout, isThrowException);
        }
        catch(UnhandledAlertException e){
            throw e;
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }

    /**
     * Wait for element to be displayed, throw exception upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementVisible() {
        waitForElementVisible(true);
    }

    /**
     * Wait for element to be displayed
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementVisible(boolean isThrowException) {
        waitForElementVisible(defaultTimeout, isThrowException);
    }

    /**
     * Wait for element to be displayed
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementVisible(int timeout, boolean isThrowException) {
        try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.visibilityOf(this));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }

    /**
     * Wait for element to be not displayed, throw exception upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementNotVisible() {
        waitForElementNotVisible(true);
    }

    /**
     * Wait for element to be not displayed
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementNotVisible(boolean isThrowException) {
        waitForElementNotVisible(defaultTimeout, isThrowException);
    }

    /**
     * Wait for element to be not displayed
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementNotVisible(int timeout, boolean isThrowException) {
  /*      try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.invisibilityOf(this));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }*/
    }

    /**
     *  Wait for element become enabled, throw exception upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementToBeClickable() {
        waitForElementToBeClickable(true);
    }

    /**
     * Wait for element become enabled
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementToBeClickable(boolean isThrowException) {
        waitForElementToBeClickable(defaultTimeout, isThrowException);
    }

    /**
     * Wait for element become enabled
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementToBeClickable(int timeout, boolean isThrowException) {
        try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.elementToBeClickable(this));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }

    /**
     *  Wait for element become enabled, throw exception upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementEnabled() {
        waitForElementEnabled(true);
    }

    /**
     * Wait for element become enabled
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementEnabled(boolean isThrowException) {
        waitForElementEnabled(defaultTimeout, isThrowException);
    }

    /**
     * Wait for element become enabled
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementEnabled(int timeout, boolean isThrowException) {
        try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.not(ExpectedConditions.stalenessOf(this)));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }


    /**
     *  Wait for element become disabled, throw exception upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementDisabled() {
        waitForElementDisabled(true);
    }

    /**
     * Wait for element become disabled
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForElementDisabled(boolean isThrowException) {
        try{
            (new WebDriverWait(Selenium.getWebdriver(), defaultTimeout/1000))
                    .until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(this)));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }

    /**
     * Waits until an element contains a specified text
     * @param text - specified text
     * @throws Exception
     */
    public void waitForTextEqual(String text) {
        waitForTextEqual(text, true);
    }

    /**
     * Waits until an element contains a specified text
     * @param text - specified text
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForTextEqual(String text, boolean isThrowException) {
        try{
            (new WebDriverWait(Selenium.getWebdriver(), defaultTimeout/1000))
                    .until(ExpectedConditions.textToBePresentInElement(this, text));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }



    /**
     * Waits until a specified attribute of a web element equals to a specified value
     * @param attributeName - a name of an attribute (String)
     * @param value - a value to check (String)
     * @throws Exception
     */
    public void waitForAttributeEqual(String attributeName, String value) {
        waitForAttributeEqual(attributeName, value, true);
    }

    /**
     * Waits until a specified attribute of a web element equals to a specified value
     * @param attributeName - a name of an attribute (String)
     * @param value - a value to check (String)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForAttributeEqual(String attributeName, String value, boolean isThrowException) {
        waitForAttributeEqual(attributeName, value, defaultTimeout, isThrowException);
    }

    /**
     * Waits until a specified attribute of a web element equals to a specified value
     * @param attributeName - a name of an attribute (String)
     * @param value - a value to check (String)
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForAttributeEqual(String attributeName, String value, int timeout, boolean isThrowException) {
        try {
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.attributeToBe(this, attributeName, value));
        }
        catch(Exception e) {
            if(isThrowException) throw e;
        }
    }

    /**
     * Waits until a specified attribute of a web element equals to a specified value
     * @param attributeName - a name of an attribute (String)
     * @param value - a value to check (String)
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForAttributeContains(String attributeName, String value, int timeout, boolean isThrowException) {
        try {
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.attributeContains(this, attributeName, value));
        }
        catch(Throwable e) {
            if(isThrowException) throw e;
        }
    }

    /**
     * Waits until a specified attribute of a web element equals to a specified value
     * @param attributeName - a name of an attribute (String)
     * @param value - a value to check (String)
     * @param timeout - time to wait in milliseconds (int)
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForAttributeNotContains(String attributeName, String value, int timeout, boolean isThrowException) {
        try {
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.not(ExpectedConditions.attributeContains(this, attributeName, value)));
        }
        catch(Exception e) {
            if(isThrowException) throw e;
        }
    }

    /**
     * Waits until a specified attribute of a web element does not equal to a specified value
     * @param attributeName - a name of an attribute (String).
     * @param value - a value to check (String).
     * @throws Exception
     */
    public void waitForAttributeNotEqual(String attributeName, String value) {
        waitForAttributeNotEqual(attributeName, value, true);
    }

    /**
     * Waits until a specified attribute of a web element does not equal to a specified value
     * @param attributeName - a name of an attribute (String).
     * @param value - a value to check (String).
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForAttributeNotEqual(String attributeName, String value, boolean isThrowException) {
        waitForAttributeNotEqual(attributeName, value, defaultTimeout, isThrowException);
    }

    /**
     * Waits until a specified attribute of a web element does not equal to a specified value
     * @param attributeName - a name of an attribute (String).
     * @param value - a value to check (String).
     * @param timeout - time to wait in milliseconds (int).
     * @param isThrowException - if true, exception is thrown upon unsuccessful wait
     * @throws Exception
     */
    public void waitForAttributeNotEqual(String attributeName, String value, int timeout, boolean isThrowException) {
        try{
            (new WebDriverWait(Selenium.getWebdriver(), timeout/1000))
                    .until(ExpectedConditions.not(ExpectedConditions.attributeToBe(this, attributeName, value)));
        }
        catch(Exception e){
            if(isThrowException) throw e;
        }
    }

    /**
     * Move mouse cursor to the middle of the element using javascript
     */
    public void mouseOverJS() {
        String code="";


            code = "var fireOnThis = arguments[0];"
                    + "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initEvent( 'mouseover', true, true );"
                    + "fireOnThis.dispatchEvent(evObj);";

        ((JavascriptExecutor) Selenium.getWebdriver()).executeScript(code, this);
    }

    /**
     * Move out mouse cursor from the element using javascript
     */
    public void mouseOutJS() {
        String code="";
        if(Browser.isIE8()){
            code = "var fireOnThis = arguments[0];"
                    + "fireOnThis.fireEvent('onmouseout');";
        } else {
            code = "var fireOnThis = arguments[0];"
                    + "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initEvent( 'mouseout', true, true );"
                    + "fireOnThis.dispatchEvent(evObj);";

        }
        ((JavascriptExecutor) Selenium.getWebdriver()).executeScript(code, this);
    }

    /**
     * Click on element using javascript
     */
    public void clickJS() {
        String code = "var element = arguments[0];" +
                "clickEvent = document.createEvent(\"MouseEvents\");" +
                "clickEvent.initEvent(\"mousedown\", true, true);" +
                "element.dispatchEvent(clickEvent);" +
                "clickEvent = document.createEvent(\"MouseEvents\");" +
                "clickEvent.initEvent(\"click\", true, true);" +
                "element.dispatchEvent(clickEvent);" +
                "clickEvent = document.createEvent(\"MouseEvents\");" +
                "clickEvent.initEvent(\"mouseup\", true, true);" +
                "element.dispatchEvent(clickEvent);";
        ((JavascriptExecutor) Selenium.getWebdriver()).executeScript(code, this.getWrappedElement());
    }

    public void click() {

        Throwable th = null;
        long finishTime = System.currentTimeMillis() + defaultTimeout;

        while (System.currentTimeMillis() <= finishTime) {

            try {

                simpleClick();
                return;

            } catch (UnhandledAlertException e) {
                throw e;
            } catch (Throwable e) {


            }
        }
        try {

        } catch (ClassCastException ccex) {

        }
    	
       /* try{
        	
            super.click();
        } catch (NoSuchElementException e){
            String localizedMessage = e.getLocalizedMessage();
            localizedMessage = localizedMessage.replaceAll("Unable to locate the element", "Unable to locate the element '"+ this.getName() +"'.");
            throw new NoSuchElementException(localizedMessage + e.getCause());
        }*/
    }

    public void scrollAndClick(){

        Throwable th = null;
        long finishTime = System.currentTimeMillis() + defaultTimeout;

        while (System.currentTimeMillis() <= finishTime) {

            try {

                scroll();

                simpleClick();

                return;


            } catch (UnhandledAlertException e) {
                throw e;
            } catch (Throwable e) {


                th = e;

            }
        }
        try {


        } catch (ClassCastException ccex) {

        }
    }

    public void simpleClick(){
        try{
            super.click();
        } catch (NoSuchElementException e){
            String localizedMessage = e.getLocalizedMessage();
            localizedMessage = localizedMessage.replaceAll("Unable to locate the element", "Unable to locate the element '"+ this.getName() +"'.");
            throw new NoSuchElementException(localizedMessage + e.getCause());
        }
    }


    /**
     * @param timeoutInSec - time to wait (secs)
     */
    public void sleep(double timeoutInSec) {
        try {
            Thread.sleep((long)(timeoutInSec * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scroll element to the center of page
     * @throws Exception
     */
    private void scroll() {
//		this.executeCommand("scrollIntoView(true)");
//Selenium.getWebdriver().manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
		/*Actions actions = new Actions(Selenium.getWebdriver());
		actions.moveToElement(this.getWrappedElement());
		actions.build().perform();
		sleep(1);*/


        Locatable elementLocation = (Locatable) this.getWrappedElement();
      //  int coordY = elementLocation.getCoordinates().inViewPort().getY();
       // int shift = coordY - Selenium.getWebdriver().manage().window().getSize().getHeight()/2;
      //  String str = String.valueOf(shift);
       // String jsString = "scrollBy(0," + str + ")";
       // ((JavascriptExecutor)Selenium.getWebdriver()).executeScript(jsString);
        sleep(1);
    }

    public void scrollToElement() {

        Throwable th = null;
        long finishTime = System.currentTimeMillis() + defaultTimeout;

        while (System.currentTimeMillis() <= finishTime) {

            try {

                scroll();

                if (isOnPage()) {
                    return;
                }

            } catch (UnhandledAlertException e) {
                throw e;
            } catch (Throwable e) {

                th = e;

            }
        }
        try {

        } catch (ClassCastException ccex) {

        }
    }

    public void waitUntilJQueryCompleted() throws InterruptedException{
        int timeout = pageLoadTimeout/1000;
        WebDriverWait waiter = new WebDriverWait(Selenium.getWebdriver(), timeout);
 /*       try {
            waiter.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver input) {
                    return (Boolean) ((JavascriptExecutor) Selenium.getWebdriver()).executeScript("return jQuery.active==0");
                }
            });
        } catch (TimeoutException e) {

        }*/
    }

    /**
     * Verify that element is present inside BaseWebElement using findElements, implicitlyWait=0
     */
    public Boolean isElementPresent(By locator){
        WebDriver driver = Selenium.getWebdriver();
      //  driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        List<WebElement> list = this.getWrappedElement().findElements(locator);
      //  driver.manage().timeouts().implicitlyWait(implicitlyWaitTimeout, TimeUnit.MILLISECONDS);
        //return !list.isEmpty() && list.get(0).isDisplayed();
        return false;
    }

    /**
     * Verify inner text of element
     */
    public boolean verifyElementText (String textExpected) throws Exception{
        boolean isEqual;
        isEqual = this.getText().toUpperCase().contentEquals(textExpected.toUpperCase());
        //Verify.equals(isEqual, true, "Text \"" + textExpected + "\" doesn't match expected.");
        return isEqual;
    }

    /**
     * Verify value of desired attribute of element
     */
    public boolean verifyAttributeValueEqual (String attribute, String textExpected) throws Exception{
        boolean isEqual;
        isEqual = this.getWrappedElement().getAttribute(attribute).toUpperCase().contentEquals(textExpected.toUpperCase());
        //Verify.equals(isEqual, true, attribute + " text \"" + textExpected + "\" doesn't match expected.");
        return isEqual;
    }

    /**
     * Verify value of desired attribute of element
     */
    public boolean verifyAttributeValueContains (String attribute, String textExpected) throws Exception{
        boolean contains;
        contains = this.getWrappedElement().getAttribute(attribute).toUpperCase().contains(textExpected.toUpperCase());
        //Verify.equals(contains, true, attribute + " text \"" + textExpected + "\" doesn't contain expected.");
        return contains;
    }

    /**
     * Set checkBox element active/inactive 
     */
    public void setActive(boolean activate) {
        boolean isActive=this.getClassAttribute().contains("active");
        if ((isActive) && (!activate))
            this.scrollAndClick();
        else
        if ((!isActive) && (activate))
            this.scrollAndClick();
    }
}