package po_utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;

//to be tested in ES: I don't know if method isEnabled works
public class ExpectedConditionElementToBeInvisible implements ExpectedCondition<Boolean> {

    private By locator;

    public ExpectedConditionElementToBeInvisible(By locator){
        this.locator = locator;
    }

    @Override
    public Boolean apply(WebDriver webDriver) {
        try {
            return !(webDriver.findElement(locator).isDisplayed());
        } catch (NoSuchElementException e) {
            // Returns true because the element is not present in DOM. The
            // try block checks if the element is present but is invisible.
            return true;
        } catch (StaleElementReferenceException e) {
            // Returns true because stale element reference implies that element
            // is no longer visible.
            return true;
        } catch (WebDriverException e) {
            // If the exception is a class cast exception, then the element is still present in the page
            return false;
//            return !(e.getCause() instanceof ClassCastException);
        }
    }
}
