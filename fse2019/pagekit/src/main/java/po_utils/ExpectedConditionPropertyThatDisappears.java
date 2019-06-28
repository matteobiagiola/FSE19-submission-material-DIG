package po_utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExpectedConditionPropertyThatDisappears implements ExpectedCondition<WebElement> {

    private By locator;
    private WebElement webElement;
    private String attributeName;
    private String defaultValueAttribute;

    public ExpectedConditionPropertyThatDisappears(By locator, String attributeName, String defaultValueAttribute){
        this.locator = locator;
        this.attributeName = attributeName;
        this.defaultValueAttribute = defaultValueAttribute;
    }

    public ExpectedConditionPropertyThatDisappears(WebElement webElement, String attributeName, String defaultValueAttribute){
        this.webElement = webElement;
        this.attributeName = attributeName;
        this.defaultValueAttribute = defaultValueAttribute;
    }

    /**
    * It works this way: the web element has a certain attribute or property called attributeName. That attribute is present
     * on the element until a certain Ajax event occurs; when that event occurs the property is removed from the element by the JS code.
     * Ex. attributeName="style" and defaultValueAttribute="display: none;". When a certain event occurs the element becomes visible
     * and the property "style" is removed from the web element.
    * */
    @Override
    public WebElement apply(WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement element = null;
        if(this.locator != null) element = webDriver.findElement(this.locator);
        else element = this.webElement;
        String attribute = (String) js.executeScript("return $(arguments[0])[0].getAttribute('" + this.attributeName + "')", element);
        PageObjectLogging.logInfo("Attribute: " + attribute);
        if(attribute == null) return element;
        if(attribute.equals(this.defaultValueAttribute)){
            return null;
        }
        return null;
    }
}
