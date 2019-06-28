package po_utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

//to be tested in ES: I don't know if method isEnabled works
public class ExpectedConditionElementToBeClickable implements ExpectedCondition<WebElement> {

    private By locator;

    public ExpectedConditionElementToBeClickable(By locator){
        this.locator = locator;
    }

    @Override
    public WebElement apply(WebDriver webDriver) {
        try {
            WebElement element = webDriver.findElement(locator);
            if (element != null && element.isEnabled()) {
                return element;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
