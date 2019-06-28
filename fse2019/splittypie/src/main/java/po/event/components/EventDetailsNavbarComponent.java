package po.event.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class EventDetailsNavbarComponent extends BasePageObject implements PageComponent {

    public EventDetailsNavbarComponent(WebDriver driver) {
        super(driver);
    }

    public void clickOnEdit(){
        this.clickOn(By.xpath("//div[@class=\"pull-right\"]/a[text()]"));
    }

    public void clickOnShare(){
        this.clickOn(By.xpath("//div[@class=\"pull-right\"]/button"));
    }

    public void selectTrip(String tripName){
        this.clickOn(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
        this.selectOptionInDropdown(By.xpath("//div[@class=\"pull-left\"]/div[contains(@class, \"dropdown event-dropdown\")]/ul"), tripName, "./li/a[not(@href=\"/new\")]");
    }

    public void addNewEvent(){
        this.clickOn(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
        this.clickOn(By.xpath("//div[@class=\"pull-left\"]/div[contains(@class, \"dropdown event-dropdown\")]/ul/li/a[@href=\"/new\"]"));
    }

    public boolean isTripPresent(String tripName){
        String currentTripName = this.getCurrentTrip();
        if(currentTripName.equals(tripName)) return true;
        this.clickOn(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
        List<WebElement> otherTripElements = this.findElements(By.xpath("//div[@class=\"pull-left\"]/div[contains(@class, \"dropdown event-dropdown\")]/ul/li/a[not(@href=\"/new\")]"));
        for(WebElement otherTripElement: otherTripElements){
            String otherTripName = this.getText(otherTripElement);
            if(otherTripName != null && !otherTripName.isEmpty()){
                if(otherTripName.equals(tripName)) return true;
            }else{
                throw new IllegalStateException("isTripPresent: other trip name must not be null nor empty");
            }
        }
        return false;
    }

    public String getCurrentTrip(){
        WebElement currentTripElement = this.findElement(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
        String currentTripName = this.getText(currentTripElement);
        if(currentTripName != null && !currentTripName.isEmpty()){
            return currentTripName;
        }else{
            throw new IllegalStateException("getCurrentTrip: current trip name must not be null nor empty");
        }
    }

    public void clickOnOverview(){
        this.clickOn(By.xpath("//a[@title=\"Overview\"]"));
    }

    public void clickOnTransactions(){
        this.clickOn(By.xpath("//a[@title=\"Transactions\"]"));
    }

    public boolean isOverviewTabActive(){
        return this.isElementPresentOnPage(By.xpath("//li[@title=\"Overview\" and contains(@class, \"active\")]"));
    }

    public boolean isTransactionsViewActive(){
        return this.isElementPresentOnPage(By.xpath("//li[@title=\"Transactions\" and contains(@class, \"active\")]"));
    }
}
