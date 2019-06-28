package po.event.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddEditEventComponent extends BasePageObject implements PageComponent {

    public AddEditEventComponent(WebDriver driver) {
        super(driver);
    }

    public void typeTripName(String tripName){
        this.type(By.xpath("//input[@placeholder=\"Example: Trip to Barcelona\"]"), tripName);
    }

    public void selectCurrency(String currencyName){
        this.selectOptionInDropdown(By.xpath("//select[@class=\"form-control event-currency\"]"), currencyName);
    }

    public void typeParticipant(String participant, int index){
        List<WebElement> participants = this.getParticipants();
        WebElement inputBox = this.findElementJSByXPathStartingFrom(participants.get(index), "./input");
        this.type(inputBox, participant);
    }

    public void deleteParticipant(String participant){
        List<WebElement> participants = this.getParticipants();
        for(WebElement participantElement: participants){
            WebElement participantNameElement = this.findElementJSByXPathStartingFrom(participantElement, "./input");
            String participantName = this.getValue(participantNameElement);
            if(participantName.equals(participant)){
                WebElement deleteButton = this.findElementJSByXPathStartingFrom(participantElement, "./span/button");
                this.clickOn(deleteButton);
            }
        }
    }

    public boolean isParticipantPresent(String participant){
        List<WebElement> participants = this.getParticipants();
        for(WebElement participantElement: participants){
            WebElement participantNameElement = this.findElementJSByXPathStartingFrom(participantElement, "./input");
            String participantName = this.getValue(participantNameElement);
            if(participantName.equals(participant)){
                return true;
            }
        }
        return false;
    }

    public boolean isButtonEnabled(String participant){
        List<WebElement> participants = this.getParticipants();
        for(WebElement participantElement: participants){
            WebElement participantNameElement = this.findElementJSByXPathStartingFrom(participantElement, "./input");
            String participantName = this.getValue(participantNameElement);
            if(participantName.equals(participant)){
                WebElement deleteButton = this.findElementJSByXPathStartingFrom(participantElement, "./span/button");
                //to test in ES
                return deleteButton.isEnabled();
            }
        }
        return false;
    }

    public List<WebElement> getParticipants(){
        return this.findElements(By.xpath("//ul//div[@class=\"input-group\"]"));
    }

    public int getIndexOfEmptyParticipant(){
        //when add participant is called the last input box is empty -> send back the number of participants in the page
        return this.getParticipants().size() - 1;
    }

    public void addParticipant(){
        this.clickOn(By.xpath("//button[@class=\"btn btn-primary add-user\"]"));
    }

    public void createOrSave(){
        this.save();
    }

    public void create(){
        this.clickOnSelenium(By.xpath("//button[@class=\"btn btn-success save-event\" and text()=\"Create\"]"));
        int timeoutMs = 1000;
        if(this.waitForElementBeingPresentOnPage(By.xpath("//button[@class=\"btn btn-success save-event\" and text()=\"Save\"]")
                , timeoutMs, TimeUnit.MILLISECONDS)){
            this.save();
        }else{
            PageObjectLogging.logInfo("Save button is not present: it may be an error (timeout too low) or the right behaviour");
        }
    }

    public void cancel(){
        this.clickOn(By.xpath("//div[@class=\"form-group hidden-xs\"]//button[@class=\"btn btn-link ember-view\"]"));
    }

    public void delete(){
        this.clickOn(By.xpath("//button[@class=\"btn btn-danger delete-event\"]"));
    }

    public void save(){
        this.clickOn(By.xpath("//button[@class=\"btn btn-success save-event\" and text()=\"Save\"]"));
        int timeout = 500;
        if(!this.waitForElementBeingPresentOnPage(ConstantLocators.EVENT_DETAILS.value(), timeout, TimeUnit.MILLISECONDS)){
            // we are still in AddEditEventContainerPage so save button should still be on the page
            if(this.isElementPresentOnPage(By.xpath("//button[@class=\"btn btn-success save-event\" and text()=\"Save\"]"))){
                // if it is on the page repeat the process
                this.save();
            }else{
                throw new IllegalStateException("save button not present in the page");
            }
        }
    }

    public boolean isEdit(){
        return this.isElementPresentOnPage(By.xpath("//button[@class=\"btn btn-danger delete-event\"]"));
    }

}
