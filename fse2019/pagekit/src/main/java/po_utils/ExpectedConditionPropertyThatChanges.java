package po_utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Optional;

public class ExpectedConditionPropertyThatChanges implements ExpectedCondition<WebElement> {

    private By locator;
    private WebElement webElement;
    private String attributeName;
    private String expectedValueAttribute;

    public ExpectedConditionPropertyThatChanges(By locator, String attributeName, String expectedValueAttribute){
        this.locator = locator;
        this.attributeName = attributeName;
        this.expectedValueAttribute = expectedValueAttribute;
    }

    public ExpectedConditionPropertyThatChanges(WebElement webElement, String attributeName, String expectedValueAttribute){
        this.webElement = webElement;
        this.attributeName = attributeName;
        this.expectedValueAttribute = expectedValueAttribute;
    }

    /**
    * It works this way: the web element has a certain attribute or property called attributeName. That attribute has a certain value
     * until a certain Ajax event occurs; when that event occurs the property changes its value.
     * Ex. attributeName="aria-expanded" and expectedValueAttribute="true"; by default aria-expanded="false". When a certain event occurs the property
     * becomes true.
    * */
    @Override
    public WebElement apply(WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement element = null;
        if(this.locator != null) {
            Optional<WebElement> optionalElement = this.findElement(webDriver, this.locator);
            if(optionalElement.isPresent()) element = optionalElement.get();
            else return null; //no assumption about property changed
        }
        else element = this.webElement;
        String attribute = (String) js.executeScript("return $(arguments[0])[0].getAttribute('" + this.attributeName + "')", element);
        //PageObjectLogging.logInfo("ExpectedConditionPropertyThatChanges. Attribute: " + attribute);
        if(attribute.equals(this.expectedValueAttribute)){
            return element;
        }
        return null;
    }

    public Optional<WebElement> findElement(WebDriver driver, By locator){
        try{
            WebElement element = driver.findElement(locator);
            return Optional.of(element);
        }catch (Exception ex){
            return Optional.empty();
        }
    }
}
