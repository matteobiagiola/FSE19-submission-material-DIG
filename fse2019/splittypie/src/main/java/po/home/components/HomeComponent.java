package po.home.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class HomeComponent extends BasePageObject implements PageComponent {

    public HomeComponent(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getEvents(){
        return this.findElements(By.xpath("//section[@id=\"events\"]//div[@class=\"previous-events\"]/div"));
    }

    public void clickOnTrip(String tripName){
        List<WebElement> events = this.getEvents();
        for(WebElement event: events){
            WebElement strongElement = this.findElementJSByXPathStartingFrom(event, "./a/strong");
            String strongText = this.getText(strongElement);
            if(strongText.equals(tripName)){
                WebElement linkElement = this.findElementJSByXPathStartingFrom(event, "./a");
                this.clickOn(linkElement);
                break;
            }
        }
    }

    public void deleteTrip(String tripName){
        List<WebElement> events = this.getEvents();
        for(WebElement event: events){
            WebElement strongElement = this.findElementJSByXPathStartingFrom(event, "./a/strong");
            String strongText = this.getText(strongElement);
            if(strongText.equals(tripName)){
                WebElement button = this.findElementJSByXPathStartingFrom(event, "./button");
                this.clickOn(button);
            }
        }
    }

    public boolean isEventPresent(String tripName){
        List<WebElement> events = this.getEvents();
        for(WebElement event: events){
            WebElement strongElement = this.findElementJSByXPathStartingFrom(event, "./a/strong");
            String strongText = this.getText(strongElement);
            if(strongText.equals(tripName)){
                return true;
            }
        }
        return false;
    }

    public void createNewEvent(){
        this.clickOn(By.xpath("//section[@id=\"about\"]//a[text()=\"Create New Event\"]"));
    }
}
