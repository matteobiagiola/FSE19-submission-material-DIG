package po_utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExpectedConditionTextThatChanges implements ExpectedCondition<WebElement> {

    private By locator;
    private String text;
    private boolean textExpected = false;

    public ExpectedConditionTextThatChanges(By locator, boolean textExpected, String text){
        this.locator = locator;
        this.text = text;
        this.textExpected = textExpected;
    }

    @Override
    public WebElement apply(WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement element = webDriver.findElement(this.locator);
        String text = (String) js.executeScript("return $(arguments[0])[0].textContent", element);
        if(this.textExpected){
            if(text.trim().equals(this.text)) return element;
            return null;
        }else{
            if(!text.trim().equals(this.text)) return element;
            return null;
        }
    }
}
