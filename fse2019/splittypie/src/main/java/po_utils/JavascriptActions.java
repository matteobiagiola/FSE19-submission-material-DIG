package po_utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Set of commonly used actions invoked by executing JavaScript on a web page
 */
public class JavascriptActions {

    //private final static int WEBDRIVER_WAIT_TIMEOUT_SEC = 15;
    private final JavascriptExecutor js;
    private final WebDriver driver;

    public JavascriptActions(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
        this.driver = driver;
    }

    /*public void click(String cssSelector) {
        js.executeScript("$('" + cssSelector + "').click()");
    }*/

    /*public void focus(String cssSelector) {
        js.executeScript("$('" + cssSelector + "').focus()");
    }*/

    /*public void focus(WebElement element) {
        js.executeScript("$(arguments[0]).focus()", element);
    }*/

    /*public Object execute(String script, WebElement element) {
        return js.executeScript(script, element);
    }*/

    /*public Object execute(String script) {
        Object value = js.executeScript("return " + script);
        return value;
        // TODO: Get rid of this wait
        try {
            Object value = js.executeScript("return " + script);
            Thread.sleep(1000);
            return value;
        } catch (InterruptedException e) {
            //PageObjectLogging.logInfo("execute", e, false);
            return null;
        } catch (UnsupportedOperationException e) {
            //PageObjectLogging.logInfo("execute", e, true);
            return null;
        }
    }*/

    /*public void mouseOver(WebElement element) {
        js.executeScript("$(arguments[0]).mouseover()", element);
    }*/

    /*public boolean isElementInViewPort(WebElement element) {

        int offset = getOffset();

        try {
            return (Boolean) js.executeScript(
                    "return ($(window).scrollTop() + " + offset + " < $(arguments[0]).offset().top) && ($(window).scrollTop() "
                            + "+ $(window).height() > $(arguments[0]).offset().top + $(arguments[0]).height() + " + offset + ")",
                    element);
        } catch (WebDriverException e) {
            String windowScrollTop = "((window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement " +
                    "|| document.body.parentNode || document.body).scrollTop)";
            String elementOffsetTop = "(arguments[0]).offsetTop";
            String windowHeight = "(window.innerHeight)";
            String elementOuterHeight = "(arguments[0]).clientHeight";

            return (boolean) js.executeScript("return (" + windowScrollTop + " + " + offset + " < " + elementOffsetTop +
                    " && " + windowScrollTop + " + " + windowHeight + " > " + elementOffsetTop + " + " + elementOuterHeight +
                    " + " + offset + ")", element);
        }
    }*/

    /*public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }*/

    /*public void scrollDown(int pixels) {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }*/

    /*public void scrollToElement(By elementBy) {
        scrollToElement(driver.findElement(elementBy));
    }*/

    /*public void scrollToElement(WebElement element) {
        try {
            js.executeScript(
                    "window.scroll(0,parseInt($(arguments[0]).offset().top - " + getOffset() + "));", element);
        } catch (WebDriverException e) {
            if (e.getMessage().contains(XSSContent.NO_JQUERY_ERROR)) {
                PageObjectLogging.logInfo("JSError", "JQuery is not defined", false);
            }
            js.executeScript(
                    "window.scroll(0,parseInt(arguments[0].getBoundingClientRect().top + window.pageYOffset - " +
                            "arguments[0].clientTop - " + getOffset() + "));", element);
        }
    }*/

    /**
     * Gets the distance from top to the bottom of the navigation bar, no matter if it's mobile or desktop.
     * @return offset
     */
    /*private int getOffset() {
        WikiBasePageObject wikiPage = new WikiBasePageObject();
        int offset = wikiPage.getNavigationBarOffsetFromTop();
        if (wikiPage.isBannerNotificationContainerPresent()) {
            offset += wikiPage.getBannerNotificationsHeight();
        }

        Search searchComponent = new SearchResultsPage().getSearch();
        if (searchComponent.isPresent()) {
            offset += searchComponent.getHeight();
        }

        return offset;
    }*/

    /*public void scrollToSpecificElement(WebElement element) {
        try {
            js.executeScript(
                    "arguments[0].scrollIntoView(true);", element);
        } catch (WebDriverException e) {
            if (e.getMessage().contains(XSSContent.NO_JQUERY_ERROR)) {
                PageObjectLogging.logInfo("JSError: " + "JQuery is not defined");
            }
        }
    }*/

    /*public void scrollToElement(WebElement element, int offset) {
        int elementPosition = element.getLocation().getY() - offset;
        js.executeScript(
                "window.scroll(0,arguments[0])", elementPosition
        );
    }*/

    /*public void scrollToElementInModal(WebElement element, WebElement modal) {
        int elementOffsetTop = Integer.parseInt(
                js.executeScript("return Math.round($(arguments[0]).offset().top)", element).toString());
        int modalOffsetTop = Integer.parseInt(
                js.executeScript("return Math.round($(arguments[0]).offset().top)", modal).toString());
        int scrollTop = elementOffsetTop - modalOffsetTop;

        js.executeScript("$(arguments[0]).scrollTop(arguments[1])", modal, scrollTop);
    }*/

    /*public void scrollElementIntoViewPort(WebElement element) {
        try {
            if (!isElementInViewPort(element)) {
                scrollToElement(element);
            }
        }catch(WebDriverException e){
            PageObjectLogging.logInfo("There might be a problem with scrolling to element", e);
        }
    }*/

    /*public void scrollBy(int x, int y) {
        js.executeScript("window.scrollBy(arguments[0], arguments[1])", x, y);
    }*/

    /*public String getCountry() {
        waitForJavaScriptTruthy("Wikia.geo");
        return js.executeScript("return Wikia.geo.getCountryCode();").toString();
    }*/

    /*public void waitForJavaScriptTruthy(final String script) {
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        try {
            new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT_SEC).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        return (boolean) js.executeScript("return !!(" + script + ");");
                    } catch (WebDriverException e) {
                        PageObjectLogging.logInfo("waitForJavaScriptTruthy: " + e);
                        return false;
                    }
                }
            });
        } finally {
            driver.manage().timeouts().implicitlyWait(WEBDRIVER_WAIT_TIMEOUT_SEC, TimeUnit.MILLISECONDS);
        }
    }*/

    /*public void changeElementOpacity(String selector, int value) {
        js.executeScript(
                "document.querySelector(arguments[0]).style.opacity = arguments[1];",
                selector, value);
    }*/

    /*public String getWindowErrors() {
        return js.executeScript("return window.errors || ''").toString();
    }*/

    /*public void addErrorListenerScript() {
        js.executeScript(
                "var script = document.createElement('script'); " + "script.innerHTML = 'window.onerror = "
                        + "function (e, u, l, c, errorObj) { window.errors = errorObj.stack }';"
                        + "document.querySelector('body').appendChild(script);");
    }*/

    /*public Long getCurrentPosition() {
        return (Long) js.executeScript("return window.pageYOffset;");
    }*/

    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/


    public WebElement findElementByXPath(String xpathExpression){
        WebElement el = (WebElement) js.executeScript("return document" +
                ".evaluate('" + xpathExpression + "',document, null, XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue");
        return el;
    }

    public WebElement findElementByXPath(WebElement element, String xpathExpression){
        WebElement el = (WebElement) js.executeScript("return document" +
                ".evaluate('" + xpathExpression + "',arguments[0], null, XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue", element);
        return el;
    }

    public List<WebElement> findElementsByXPath(String xpathExpression){
        Long numberOfMatches = (Long) js.executeScript("return document" +
                ".evaluate('count(" + xpathExpression + ")', document, null, XPathResult.NUMBER_TYPE,null).numberValue");
        List<WebElement> elements = new ArrayList<WebElement>();
        for (int i = 0; i < numberOfMatches; i++) {
            elements.add((WebElement) js.executeScript("return document" +
                    ".evaluate('" + xpathExpression + "',document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null).snapshotItem(" + i + ")"));
        }
        return elements;
    }

    public List<WebElement> findElementsByXPath(WebElement element, String xpathExpression){
        Long numberOfMatches = (Long) js.executeScript("return document" +
                ".evaluate('count(" + xpathExpression + ")', arguments[0], null, XPathResult.NUMBER_TYPE,null).numberValue", element);
        List<WebElement> elements = new ArrayList<WebElement>();
        for (int i = 0; i < numberOfMatches; i++) {
            elements.add((WebElement) js.executeScript("return document" +
                    ".evaluate('" + xpathExpression + "', arguments[0], null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null).snapshotItem(" + i + ")", element));
        }
        return elements;
    }

    //works without jquery
    public void click(WebElement element) {
        js.executeScript("return arguments[0].click()", element);
    }

    //to test without jquery
    public void sendKeys(WebElement element, String text){
        js.executeScript("return arguments[0].value='" + text + "'", element);
    }

    //to test without jquery
    public void sendKeys(WebElement element, CharSequence text){
        js.executeScript("return arguments[0].value='" + text.subSequence(0, text.length()) + "'", element);
    }

    //to test without jquery
    public void focusOnElement(WebElement element){
        js.executeScript("return arguments[0].focus();", element);
    }

    //to test without jquery
    public String getAttribute(WebElement element, String attributeName){
        String attribute = (String) js.executeScript("return arguments[0].getAttribute('" + attributeName + "')",element);
        return attribute.trim();
    }

    //to test without jquery
    public String getText(WebElement element){
        String text = (String) js.executeScript("return arguments[0].textContent", element);
        return text.trim();
    }

    //to test without jquery
    public String getValue(WebElement element){
        String text = (String) js.executeScript("return arguments[0].value", element);
        return text.trim();
    }


    public void selectOptionInDropdown(WebElement dropdown, String optionToSelectByStringValue){
        List<WebElement> options = (List<WebElement>) js.executeScript("return arguments[0].options", dropdown);
        for(WebElement option: options){
            String optionText = this.getText(option);
            if(optionText.equals(optionToSelectByStringValue)){
                option.click();
                break;
            }
        }
    }


    public boolean isOptionPresentInDropdown(WebElement dropdown, String optionToSelectByStringValue){
        List<WebElement> options = (List<WebElement>) js.executeScript("return arguments[0].options", dropdown);
        for(WebElement option: options){
            if(option.getText().equals(optionToSelectByStringValue)){
                return true;
            }
        }
        return false;
    }

    /**
     *  initKeyEvent for Firefox
     * initKeyboardEvent for IE9+, Chrome and Safari
     * Send key '13' (= enter)
     * For details: https://stackoverflow.com/questions/3276794/jquery-or-pure-js-simulate-enter-key-pressed-for-testing
     * */
    public void pressKeyEnterOnElement(WebElement element){
        js.executeScript("var ev = document.createEvent('KeyboardEvent'); " +
                "ev.initKeyboardEvent(\'keydown\', true, true, window, false, false, false, false, 13, 0);",element);
    }


}
