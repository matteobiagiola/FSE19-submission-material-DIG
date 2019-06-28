package po_apogen;

import custom_classes.Currencies;
import custom_classes.Participants;
import custom_classes.TripNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageComponent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewEventComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for NewEvent (state5) --> AddEditEventContainerPage
	 */
	public NewEventComponent(WebDriver driver) {
		super(driver);
	}

	public void form(TripNames tripName, Currencies currency, Participants participant1, Participants participant2) {
		this.type(By.xpath("//input[@placeholder=\"Example: Trip to Barcelona\"]"), tripName.value());
		this.selectOptionInDropdown(By.xpath("//select[@class=\"form-control event-currency\"]"), currency.value());
		this.typeParticipant(participant1.value(), 0);
		this.typeParticipant(participant2.value(), 1);
		this.createOrSave();
	}

	public void cancel() {
		this.clickOn(By.xpath("//div[@class=\"form-group hidden-xs\"]//button[@class=\"btn btn-link ember-view\"]"));
	}

	/*---------------------- added */

	public void typeParticipant(String participant, int index){
		List<WebElement> participants = this.getParticipants();
		WebElement inputBox = this.findElementJSByXPathStartingFrom(participants.get(index), "./input");
		this.type(inputBox, participant);
	}

	public List<WebElement> getParticipants(){
		return this.findElements(By.xpath("//ul//div[@class=\"input-group\"]"));
	}

	public void createOrSave(){
		this.save();
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
