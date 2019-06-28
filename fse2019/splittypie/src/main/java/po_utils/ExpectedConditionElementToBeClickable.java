package po_utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

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
