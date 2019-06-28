package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NavbarComponent extends BasePageObject implements PageComponent {

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    public void goToHomePage() {
        this.clickOn(By.xpath("//div[@class=\"navbar-header\"]/a[@title=\"SplittyPie Home Page\"]"));
    }

    public void goToAbout() {
        this.clickOn(By.xpath("//div[@id=\"navbar-right\"]//a[@title=\"About\"]"));
    }

    public boolean isEventsPresent() {
        return this.isElementPresentOnPage(By.xpath("//div[@id=\"navbar-right\"]//a[@title=\"Events\"]"));
    }

    public void goToEvents() {
        this.clickOn(By.xpath("//div[@id=\"navbar-right\"]//a[@title=\"Events\"]"));
    }

    public void goToFeatures() {
        this.clickOn(By.xpath("//div[@id=\"navbar-right\"]//a[@title=\"Features\"]"));
    }

    public void createNewEvent() {
        this.clickOn(By.xpath("//div[@id=\"navbar-right\"]//a[@title=\"Create New Event\"]"));
    }

    public void viewingAs(String participant) {
        this.clickOn(By.xpath("//div[@id=\"navbar-right\"]//button[@id=\"dropDownUsers\"]"));
        this.selectOptionInDropdown(By.xpath("//div[@id=\"navbar-right\"]//div[contains(@class, \"dropdown user-dropdown\")]/ul"), participant, "./li/a");
    }

    public List<WebElement> getOtherParticipants(){
        return this.findElements(By.xpath("//div[@id=\"navbar-right\"]//div[contains(@class, \"dropdown user-dropdown\")]/ul/li/a"));
    }

    public boolean isParticipantPresent(String participant){ //is not the current participant
        //String currentParticipantName = this.getCurrentParticipant();
        //if(currentParticipantName.equals(participant)) return true;
        List<WebElement> otherParticipants = this.getOtherParticipants();
        for(WebElement otherParticipant: otherParticipants){
            String otherParticipantName = this.getText(otherParticipant);
            if(otherParticipantName != null && !otherParticipantName.isEmpty()){
                if(otherParticipantName.equals(participant)) return true;
            }else{
                throw new IllegalStateException("isParticipantPresent: other participant name must not be null nor empty");
            }
        }
        return false;
    }

    //the regex fails
    /*public String getCurrentParticipant(){
        WebElement currentParticipantElement = this.findElement(By.xpath("//div[@id=\"navbar-right\"]//button[@id=\"dropDownUsers\"]"));
        String textCurrentParticipant = this.getText(currentParticipantElement);
        if(textCurrentParticipant != null && !textCurrentParticipant.isEmpty()){
            Pattern p = Pattern.compile("Viewing as (.\\*)");
            Matcher m = p.matcher(textCurrentParticipant);
            if(m.find()){
                String participantName = m.group(1);
                return participantName;
            }else{
                throw new IllegalStateException("getCurrentParticipant: text of current participant did not match the regex " + textCurrentParticipant);
            }
        }else{
            throw new IllegalStateException("getCurrentParticipant: text of current participant must not be null nor empty");
        }
    }*/
}
