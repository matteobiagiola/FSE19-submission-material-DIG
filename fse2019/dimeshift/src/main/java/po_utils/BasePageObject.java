package po_utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import po_utils.*;

import java.util.Date;
import java.util.List;

public class BasePageObject {

    public final Wait wait;
    public WebDriverWait waitFor;
    public Actions builder;
    //protected WebDriver driver = DriverProvider.getActiveDriver();
    protected WebDriver driver;
    protected int timeOut = 3;
    //protected UrlBuilder urlBuilder = new UrlBuilder();
    protected JavascriptActions jsActions;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        this.waitFor = new WebDriverWait(driver, timeOut);
        this.builder = new Actions(driver);
        this.wait = new Wait(driver);
        this.jsActions = new JavascriptActions(driver);

    }

    public static String getTimeStamp() {
        Date time = new Date();
        long timeCurrent = time.getTime();
        return String.valueOf(timeCurrent);
    }

    /*private static String getEmailChangeConfirmationLink(String email, String password) {
        String mailSubject = "Confirm your email address change on FANDOM";
        String url = EmailUtils.getActivationLinkFromEmailContent(
                EmailUtils.getFirstEmailContent(email, password, mailSubject));
        PageObjectLogging.logInfo("getActivationLinkFromMail",
                "activation link is visible in email content: " + url, true);
        return url;
    }*/

    /*public static String getPasswordResetLink(String email, String password) {
        String passwordResetEmail = EmailUtils
                .getFirstEmailContent(email, password, "Reset your FANDOM password");
        String resetLink = EmailUtils.getPasswordResetLinkFromEmailContent(passwordResetEmail);
        PageObjectLogging.logInfo("Password reset link", "Password reset link received: " + resetLink,
                true);

        return resetLink;
    }*/

    /*public static String getEmailConfirmationLink(String email, String password) {
        String emailConfirmationMessage = EmailUtils.getFirstEmailContent(email, password,
                "Confirm your email and get started on FANDOM!");
        String confirmationLink = EmailUtils.getConfirmationLinkFromEmailContent(emailConfirmationMessage);
        PageObjectLogging.logInfo("Email confirmation link", "Email confirmation link received: " + confirmationLink,
                true);

        return confirmationLink;
    }*/

    // wait for comscore to load
    /*public void waitForPageLoad() {
        wait.forElementPresent(
                By.cssSelector("script[src='http://b.scorecardresearch.com/beacon.js']"));
    }*/

    /*public BasePageObject waitForPageReload() {
        waitSafely(() -> wait.forElementVisible(By.className("loading-overlay"), Duration.ofSeconds(3)));
        waitSafely(() -> wait.forElementNotVisible(By.className("loading-overlay")),
                "Loading overlay still visible, page not loaded in expected time");
        return this;
    }*/

    /**
     * Simple method for checking if element is on page or not. Changing the implicitWait value allows
     * us no need for waiting 30 seconds
     */
    /*protected boolean isElementOnPage(By by) {
        changeImplicitWait(500, TimeUnit.MILLISECONDS);
        try {
            return driver.findElements(by).size() > 0;
        } finally {
            restoreDefaultImplicitWait();
        }
    }*/

    /**
     * Simple method for checking if element is on page or not. Changing the implicitWait value allows
     * us no need for waiting 30 seconds
     */
    /*protected boolean isElementOnPage(WebElement element) {
        changeImplicitWait(500, TimeUnit.MILLISECONDS);
        boolean isElementOnPage = true;
        try {
            // Get location on WebElement is rising exception when element is not present
            element.getLocation();
        } catch (WebDriverException ex) {
            isElementOnPage = false;
        } finally {
            restoreDefaultImplicitWait();
        }
        return isElementOnPage;
    }*/

    /**
     * WebElement.isEnabled() method signature says that it returns true for anything except disabled
     * input fields. In order to check if non-input elements are disabled, "disabled" attribute value
     * must be checked and compared to "true" value
     * @param element WebElement on the page
     * @return true if value of "disabled" attribute is different than "true"
     */
    /*protected boolean isElementEnabled(WebElement element) {
        return !"true".equals(element.getAttribute("disabled"));
    }*/

    /**
     * Method to check if WebElement is displayed on the page
     *
     * @return true if element is displayed, otherwise return false
     */
    /*protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            PageObjectLogging.logInfo(e.getMessage());
            //PageObjectLogging.logInfo(e.getMessage());
            return false;
        }
    }*/

    /**
     * Method to check if WebElement is displayed on the page
     *
     * @return true if element is displayed, otherwise return false
     */

    /*protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = findElement(locator);
            PageObjectLogging.logInfo("Element displayed: " + element.isDisplayed());
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            PageObjectLogging.logInfo(e.getMessage());
            //PageObjectLogging.logInfo(e.getMessage());
            return false;
        }
    }*/

    /**
     * Method to check if WebElement is displayed on the page
     *
     * @return true if element is displayed, otherwise return false
     */

    /*protected boolean isElementDisplayed(By locator, int timeout) {
        try {
            WebElement element = findElement(locator);
            wait.forElementVisible(element, timeout);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }*/

    /**
     * Method to check if WebElement is displayed on the page
     *
     * @return true if element is displayed, otherwise return false
     */

    /*protected boolean isElementDisplayed(WebElement element, int timeout) {
        try {
            wait.forElementVisible(element, timeout);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }*/

    /**
     * Make sure element is ready to be clicked and click on it The separation of this method has
     * particular reason. It allows global modification of such click usages. This way it is very easy
     * to control what criteria have to be met in order to click on element
     *
     * @param element to be clicked on
     */
    /*protected void waitAndClick(WebElement element) {
        wait.forElementClickable(element).click();
    }*/

    /**
     * Simple method for getting number of element on page. Changing the implicitWait value allows us
     * no need for waiting 30 seconds
     */
    /*protected int getNumOfElementOnPage(By cssSelectorBy) {
        changeImplicitWait(500, TimeUnit.MILLISECONDS);
        int numElementOnPage;
        try {
            numElementOnPage = driver.findElements(cssSelectorBy).size();
        } catch (WebDriverException ex) {
            numElementOnPage = 0;
        } finally {
            restoreDefaultImplicitWait();
        }
        return numElementOnPage;
    }*/

    /*protected void waitSafely(Runnable o) {
        waitSafely(o, "");
    }*/

    /*void waitSafely(Runnable o, String message) {
        try {
            o.run();
        } catch (TimeoutException e) {
            PageObjectLogging.logInfo("Timed out waiting: " + String.format("%s\n%s", message, e));
        }
    }*/

    /*protected boolean isElementInContext(String cssSelector, WebElement element) {
        changeImplicitWait(500, TimeUnit.MILLISECONDS);
        boolean isElementInElement = true;
        try {
            if (element.findElements(By.cssSelector(cssSelector)).size() < 1) {
                isElementInElement = false;
            }
        } catch (WebDriverException ex) {
            isElementInElement = false;
        } finally {
            restoreDefaultImplicitWait();
        }
        return isElementInElement;
    }*/

    /*public void scrollTo(WebElement element) {
        jsActions.scrollElementIntoViewPort(element);
        wait.forElementClickable(element, 5);
    }*/

    /*protected void scrollAndClick(WebElement element) {
        jsActions.scrollElementIntoViewPort(element);
        wait.forElementClickable(element, 5);
        element.click();
    }*/

    /*protected void scrollAndClick(List<WebElement> elements, int index) {
        jsActions.scrollElementIntoViewPort(elements.get(index));
        wait.forElementClickable(elements, index, 5);
        elements.get(index).click();
    }*/

    /*protected void scrollAndClick(WebElement element, int offset) {
        jsActions.scrollToElement(element, offset);
        element.click();
    }*/

    /*public boolean isStringInURL(String givenString) {
        String currentURL = driver.getCurrentUrl();
        if (currentURL.toLowerCase().contains(givenString.toLowerCase())) {
            PageObjectLogging.logInfo("isStringInURL: "
                    + String.format("Current url: %s contains given string: %s", currentURL, givenString));
            return true;
        } else {
            PageObjectLogging.logInfo("isStringInURL: "
                    + String.format("Current url: %s does not contain given string: %s", currentURL, givenString));
            return false;
        }
    }*/

    /*public void verifyUrlContains(final String givenString, int timeOut) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            new WebDriverWait(driver, timeOut).until((ExpectedCondition<Boolean>) d -> d.getCurrentUrl()
                    .toLowerCase().contains(givenString.toLowerCase()));
        } finally {
            restoreDefaultImplicitWait();
        }
    }*/

    /*public void verifyURL(String givenURL) {
        Assertion.assertEquals(driver.getCurrentUrl(), givenURL);
    }*/

    /*public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }*/

    /*public void getUrl(String url) {
        getUrl(url, false);
    }*/

    /*public void getUrl(String url, boolean makeScreenshot) {
        driver.get(url);
        if (makeScreenshot) {
            PageObjectLogging.logInfo("Take screenshot",
                    String.format("Screenshot After Navigation to: %s", url), true, driver);
        }
    }*/

    /*public void getUrl(Page page) {
        getUrl(urlBuilder.getUrlForPage(page));
    }*/

    /*public void getUrl(Page page, String queryString) {
        getUrl(urlBuilder.appendQueryStringToURL(urlBuilder.getUrlForPage(page), queryString));
    }*/

    /*public void refreshPage() {
        try {
            driver.navigate().refresh();
            PageObjectLogging.logInfo("refreshPage: " + "page refreshed");
        } catch (TimeoutException e) {
            PageObjectLogging.logInfo("refreshPage: " + "page loaded for more than 30 seconds after click");
        }
    }*/

    /*public void waitForWindow(String windowName, String comment) {
        Object[] windows = driver.getWindowHandles().toArray();
        int delay = 500;
        int sumDelay = 500;
        while (windows.length == 1) {
            try {
                Thread.sleep(delay);
                windows = driver.getWindowHandles().toArray();
                sumDelay += 500;
            } catch (InterruptedException e) {
                PageObjectLogging.logInfo(windowName + " " +  e);
            }
            if (sumDelay > 5000) {
                PageObjectLogging.logInfo(windowName + " " +  comment);
                break;
            }
        }
    }*/

    /*protected void hover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }*/

    /*protected void moveAway(WebElement element) {
        new Actions(driver).moveToElement(element, -200, 0).perform();
    }*/

    /*protected Boolean scrollToSelector(String selector) {
        if (isElementOnPage(By.cssSelector(selector))) {
            try {
                driver.executeScript(
                        "var x = $(arguments[0]);" + "window.scroll(0,x.position()['top']+x.height()+100);"
                                + "$(window).trigger('scroll');",
                        selector);
            } catch (WebDriverException e) {
                if (e.getMessage().contains(XSSContent.NO_JQUERY_ERROR)) {
                    PageObjectLogging.logInfo("JSError", "JQuery is not defined", false);
                }
            }
            return true;
        } else {
            PageObjectLogging.logInfo("SelectorNotFound", "Selector " + selector + " not found on page", true);
            return false;
        }
    }*/

    // You can get access to hidden elements by changing class
    /*public void unhideElementByClassChange(String elementName, String classWithoutHidden,
                                           int... optionalIndex) {
        int numElem = optionalIndex.length == 0 ? 0 : optionalIndex[0];
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByName('" + elementName + "')[" + numElem
                + "].setAttribute('class', '" + classWithoutHidden + "');");
    }*/

    /*public void waitForElementNotVisibleByElement(WebElement element) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            waitFor.until(CommonExpectedConditions.invisibilityOfElementLocated(element));
        } finally {
            restoreDefaultImplicitWait();
        }
    }*/

    /*public void waitForElementNotVisibleByElement(WebElement element, long timeout) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            new WebDriverWait(driver, timeout)
                    .until(CommonExpectedConditions.invisibilityOfElementLocated(element));
        } finally {
            restoreDefaultImplicitWait();
        }
    }*/

    /*public void waitForValueToBePresentInElementsAttributeByCss(String selector, String attribute,
                                                                String value) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            waitFor.until(CommonExpectedConditions
                    .valueToBePresentInElementsAttribute(By.cssSelector(selector), attribute, value));
        } finally {
            restoreDefaultImplicitWait();
        }
    }*/

    /*public void waitForValueToBePresentInElementsCssByCss(String selector, String cssProperty,
                                                          String expectedValue) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            waitFor.until(CommonExpectedConditions.cssValuePresentForElement(By.cssSelector(selector),
                    cssProperty, expectedValue));
        } finally {
            restoreDefaultImplicitWait();
        }
    }*/

    /*public void waitForValueToBePresentInElementsAttributeByElement(WebElement element,
                                                                    String attribute, String value) {
        waitFor.until(
                CommonExpectedConditions.valueToBePresentInElementsAttribute(element, attribute, value));
    }*/

    /*public void waitForStringInURL(String givenString) {
        waitFor.until(CommonExpectedConditions.givenStringtoBePresentInURL(givenString));
        PageObjectLogging.logInfo("waitForStringInURL: " + "verify that url contains " + givenString);
    }*/

    /*public String getRandomDigits(int length) {
        String timeStamp = getTimeStamp();
        int timeStampLenght = timeStamp.length();
        int timeStampCut = timeStampLenght - length;
        return timeStamp.substring(timeStampCut);
    }*/

    /*public void openWikiPage() {
        getUrl(getWikiUrl() + URLsContent.NOEXTERNALS);
        PageObjectLogging.logInfo("WikiPageOpened", "Wiki page is opened", true);
    }*/

    /*public String getWikiUrl() {
        return urlBuilder.getUrlForWiki(Configuration.getWikiName());
    }*/

    /*public void fillInput(WebElement input, String value) {
        wait.forElementVisible(input).sendKeys(value);
    }*/

    /**
     * Wait for new window present
     */
    /*public void waitForNewWindow() {
        waitFor.until(CommonExpectedConditions.newWindowPresent());
    }*/

    /*public void appendToUrl(String additionToUrl) {
        driver.get(urlBuilder.appendQueryStringToURL(driver.getCurrentUrl(), additionToUrl));
        PageObjectLogging.logInfo("appendToUrl", additionToUrl + " has been appended to url", true);
    }*/

    /*public void appendMultipleQueryStringsToUrl(String[] queryStrings) {
        String currentUrl = getCurrentUrl();
        for (String queryString : queryStrings) {
            currentUrl = urlBuilder.appendQueryStringToURL(currentUrl, queryString);
        }
        driver.get(currentUrl);
        PageObjectLogging.logInfo("appendToUrl", queryStrings + " have been appended to url", true);
    }*/

    /*public void pressDownArrow(WebElement element) {
        driver.executeScript(
                "var e = jQuery.Event(\"keydown\"); " + "e.which=40; $(arguments[0]).trigger(e);", element);
    }*/

    /*public void setDisplayStyle(String selector, String style) {
        driver.executeScript("document.querySelector(arguments[0]).style.display = arguments[1]",
                selector, style);
    }*/

    /*private void purge(String url) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpUriRequest method = new PurgeMethod(url);
        try {
            int status = client.execute(method).getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK && status != HttpStatus.SC_NOT_FOUND) {
                throw new Exception("HTTP PURGE failed for: " + url + "(" + status + ")");
            }
            PageObjectLogging.logInfo("purge", url, true);
            return;
        } finally {
            client.close();
        }
    }*/

    /**
     * return status code of given URL
     */
    /*public int getURLStatus(String url) {
        try {
            purge(url);
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.disconnect();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) "
                            + "Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
            int status = connection.getResponseCode();
            connection.disconnect();
            return status;
        } catch (Exception e) {
            throw new WebDriverException(e);
        }
    }*/

    /**
     * check if current HTTP status of given URL is the same as expected
     */
    /*public void verifyURLStatus(int desiredStatus, String url) {
        int waitTime = 500;
        int statusCode = 0;
        boolean status = false;
        while (!status) {
            try {
                statusCode = getURLStatus(url);
                if (statusCode == desiredStatus) {
                    status = true;
                } else {
                    Thread.sleep(500);
                    waitTime += 500;
                }
                if (waitTime > 20000) {
                    break;
                }
            } catch (InterruptedException e) {
                throw new WebDriverException(e);
            }
        }
        Assertion.assertEquals(statusCode, desiredStatus);
        PageObjectLogging.logInfo("verifyURLStatus", url + " has status " + statusCode, true);
    }*/

    /*protected void changeImplicitWait(int value, TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(value, timeUnit);
    }*/

    /*protected void setShortImplicitWait() {
        changeImplicitWait(3, TimeUnit.SECONDS);
    }*/

    /*protected void restoreDefaultImplicitWait() {
        changeImplicitWait(timeOut, TimeUnit.SECONDS);
    }*/

    /*public void verifyUrlInNewWindow(String url) {
        waitForWindow("", "");
        Object[] windows = driver.getWindowHandles().toArray();
        driver.switchTo().window(windows[1].toString());
        waitForStringInURL(url);
        driver.close();
        driver.switchTo().window(windows[0].toString());
        PageObjectLogging.logInfo("verifyUrlInNewWindow: " + "url in new window verified");
    }*/

    /*public void verifyElementMoved(Point source, WebElement element) {
        Point target = element.getLocation();
        if (source.x == target.x && source.y == target.y) {
            Assertion.fail("Element did not move. Old coordinate (" + source.x + "," + source.y + ") "
                    + "New coordinate (" + target.x + "," + target.y + ")");
        }
        PageObjectLogging.logInfo("verifyElementMoved", "Element did move. From (" + source.x + ","
                + source.y + ") to (" + target.x + "," + target.y + ")", true, driver);
    }*/

    /*public void verifyElementResized(Dimension source, WebElement element) {
        Dimension target = element.getSize();
        int sourceWidth = source.width;
        int sourceHeight = source.height;
        int targetWidth = target.width;
        int targetHeight = target.height;

        if (sourceWidth == targetWidth && sourceHeight == targetHeight) {
            Assertion.fail("Element did not resize. Old dimension (" + sourceWidth + "," + sourceHeight
                    + ") " + "New dimension (" + targetWidth + "," + targetHeight + ")");
        }
        PageObjectLogging.logInfo("verifyElementMoved", "Element did resize. From (" + sourceWidth + ","
                + sourceHeight + ") to (" + targetWidth + "," + targetHeight + ")", true, driver);
    }*/

    /*public String switchToNewBrowserTab() {
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        return driver.getCurrentUrl();
    }*/

    /*private int getTabsCount() {
        return driver.getWindowHandles().size();
    }*/

    /*private String getNewTab(String parentTab) {
        Optional<String> newTab = driver.getWindowHandles().stream()
                .filter(handleName -> !handleName.equals(parentTab)).findFirst();
        return newTab.orElseThrow(() -> new NotFoundException("New tab not found!"));
    }*/

    /*private String switchToNewTab(String parentTab) {
        String newTab = getNewTab(parentTab);
        driver.switchTo().window(newTab);
        return newTab;
    }*/

    /*private String getTabWithTitle(String title) {
        return getTabWithCondition(nameToTitle -> nameToTitle.getValue().startsWith(title));
    }*/

    /*private String getOtherTab(String title) {
        return getTabWithCondition(nameToTitle -> !nameToTitle.getValue().startsWith(title));
    }*/

    /*private String getTabWithCondition(
            java.util.function.Predicate<? super Pair<String, String>> condition) {
        Optional<String> newTab = driver.getWindowHandles()
                .stream()
                .map(handleName -> Pair.of(handleName, driver.switchTo().window(handleName).getTitle()))
                .peek(handleTitle -> PageObjectLogging.logInfo("Found window", String.format("Window with title %s", handleTitle), true))
                .filter(condition)
                .map(Pair::getKey)
                .findFirst();
        return newTab.orElseThrow(
                () -> new NotFoundException("Tab that satisfies the condition doesn't exist"));
    }*/

    /*public WebDriver switchToWindowWithTitle(String title) {
        PageObjectLogging.logInfo("Switching windows",
                String.format("Switching to window with title: %s", title), true);
        return driver.switchTo().window(getTabWithTitle(title));
    }*/

    /*public WebDriver switchAwayFromWindowWithTitle(String title) {
        PageObjectLogging.logInfo("Switching windows",
                String.format("Switching away from window with title: %s", title), true);
        return driver.switchTo().window(getOtherTab(title));
    }*/

    /*public WebDriver switchToMainWindow() {
        return driver.switchTo().defaultContent();
    }*/

    /*private void waitForLinkOpenedInNewTab(WebElement link) {
        int initialTabsNumber = driver.getWindowHandles().size();
        link.click();
        new WebDriverWait(driver, TIMEOUT_PAGE_REGISTRATION)
                .until((Predicate<WebDriver>) input -> getTabsCount() > initialTabsNumber);
    }*/

    /*protected void openLinkInNewTab(WebElement link) {
        String currentTab = driver.getWindowHandle();
        waitForLinkOpenedInNewTab(link);
        switchToNewTab(currentTab);
    }*/

    /*private List<String> getTabUrls() {
        String currentTab = driver.getWindowHandle();
        List<String> result = new ArrayList<>();
        for (String windowHandler : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandler);
            result.add(driver.getCurrentUrl());
        }

        driver.switchTo().window(currentTab);
        return result;
    }*/

    /*public boolean tabContainsUrl(String url) {
        return getTabUrls().contains(url);
    }*/

    /*public int getElementBottomPositionByCssSelector(String elementName) {
        WebElement element = driver.findElement(By.cssSelector(elementName));
        return element.getLocation().getY() + element.getSize().getHeight();
    }*/

    /*public int getElementTopPositionByCssSelector(String elementName) {
        WebElement element = driver.findElement(By.cssSelector(elementName));
        return element.getLocation().getY();
    }*/





    /*--------------------------------------------------------------------------------------------------------------------------------------------------*/

    /*@@@@@@@@@@@@@ ACTION @@@@@@@@@@@@@@*/
    // safely finding elements in actions -> for action I mean things performed on the page but after the preconditions are satisfied
    // if these actions are in the body of a PO method I assume that the locator is right and that the element the user is looking for exists in the page
    // if the element exists but the locator is wrong, in any case there is an exception thrown by Selenium

    public void typeJS(By locator, String input){
        WebElement element = this.findElementSafely(locator);
        this.jsActions.sendKeys(element,input);
        /*try{
            WebElement element = this.findElement(locator);
            this.jsActions.sendKeys(element,input);
        }catch(Exception ex){
            PageObjectLogging.logError("SeleniumException typeJS: " + ExceptionUtils.getStackTrace(ex));
        }*/
    }

    public void clickOn(WebElement element){
        this.jsActions.click(element);
        /*try{
            this.jsActions.click(element);
        }catch(Exception ex){
            PageObjectLogging.logError("SeleniumException clickOn: " + ExceptionUtils.getStackTrace(ex));
        }*/
    }

    public void clickOn(By locator){
        WebElement element = this.findElementSafely(locator);
//      element.click();
        this.jsActions.click(element);
        /*try{
            WebElement element = this.findElement(locator);
            this.jsActions.click(element);
        }catch(Exception ex){
            PageObjectLogging.logError("SeleniumException clickOn: " + ExceptionUtils.getStackTrace(ex));
        }*/
    }

    public void clickOnSelenium(By locator){
        WebElement element = this.findElementSafely(locator);
        element.click();
    }

    public void pressKeyboardEnter(By locator){
        this.jsActions.pressKeyEnterOnElement(this.findElementSafely(locator));
        //this.findElement(locator).sendKeys(Keys.ENTER);
        /*try{
            this.findElement(locator).sendKeys(Keys.ENTER);
        }catch(Exception ex){
            PageObjectLogging.logError("SeleniumException pressKeyboardEnter: " + ExceptionUtils.getStackTrace(ex));
        }*/
    }

    /**
     * Simple method for checking if element is on page or not. No Ajax involved.
     */
    public boolean isElementPresentOnPage(By by) {
        return this.findElements(by).size() > 0;
    }

    /**
     * If the element exists in the page it returns it (returns the first element more elements exist)
     * Otherwise it returns null
     * */
    public WebElement elementPresentOnPage(By by){
        List<WebElement> elements = this.findElements(by);
        if(elements.size() > 0) return elements.get(0);
        return null;
    }

    public String getWebElementText(By locator){
        return this.findElement(locator).getText();
    }

    public String getAttribute(WebElement element, String attributeName){
        return this.jsActions.getAttribute(element,attributeName);
    }

    public String getText(WebElement element){
        return this.jsActions.getText(element);
    }

    /*@@@@@@@@@@@@@ LOCALIZATION @@@@@@@@@@@@@@*/

    /**
    *  this method should not be called by methods on preconditions
     * should be called only by methods inside the body of a PO
    * */
    public WebElement findElementSafely(By locator){
        return this.getElementOnPageAfterWait(locator);
    }

    public WebElement findElement(By locator){
        return this.driver.findElement(locator);
        /*try{
            return this.driver.findElement(locator);
        }catch(Exception ex){
            PageObjectLogging.logError("SeleniumException findElement: " + ExceptionUtils.getStackTrace(ex));
            throw ex;
        }*/
    }

    public List<WebElement> findElements(By locator) {
        return this.driver.findElements(locator);
        /*try{
            return this.driver.findElements(locator);
        }catch(Exception ex){
            PageObjectLogging.logError("SeleniumException findElements: " + ExceptionUtils.getStackTrace(ex));
            throw ex;
        }*/
    }

    public WebElement findElementJS(String xpathExpression){
        return this.jsActions.findElementByXPath(xpathExpression);
    }

    public List<WebElement> findElementsJS(String xpathExpression){
        return this.jsActions.findElementsByXPath(xpathExpression);
    }

    /*@@@@@@@@@@@@@ WAIT @@@@@@@@@@@@@@*/

    /**
     * Method to check if WebElement is displayed on the page. The element is present but not visible.
     * It may turn visible according to some Ajax actions.
     *
     * @return true if element is displayed, otherwise return false
     */
    public boolean waitForElementBeingVisibleOnPage(By locator) {
        try {
            WebElement element = findElement(locator);
            wait.forElementBeingVisible(element);
            return true;
        } catch (TimeoutException e) {
            PageObjectLogging.logInfo("waitForElementBeingVisibleOnPage timeout exception: unable to locate the element " + locator.toString());
            return false;
        }
    }

    /**
     * Method to check until a certain WebElement disappears from the page.
     * The element may be present or not. It disappears after some time.
     *
     * @return true if element is present, otherwise return false
     */
    public boolean waitForElementBeingInvisibleOnPage(By locator){
        try{
            wait.forElementBeingInvisible(locator);
            return true;
        }catch(TimeoutException e){
            PageObjectLogging.logInfo("waitForElementBeingInvisibleOnPage timeout exception: unable to locate the element " + locator.toString());
            return false;
        }
    }

    /**
     * Method to check if WebElement is present on the page. The element is not present.
     * It may turn present according to some Ajax actions.
     *
     * @return true if element is present, otherwise return false
     */
    public boolean waitForElementBeingPresentOnPage(By locator){
        try{
            wait.forElementBeingPresentPageLoadingTimeout(locator);
            return true;
        }catch(TimeoutException e){
            PageObjectLogging.logInfo("waitForElementBeingPresentOnPage timeout exception: unable to locate the element " + locator.toString());
            return false;
        }
    }

    public WebElement getElementOnPageAfterWait(By locator){
        return wait.forElementBeingPresentDefaultTimeout(locator);
    }

    /*@@@@@@@@@@@@@ OTHER @@@@@@@@@@@@@@*/

    public WebDriver getDriver(){
        return this.driver;
    }
}
