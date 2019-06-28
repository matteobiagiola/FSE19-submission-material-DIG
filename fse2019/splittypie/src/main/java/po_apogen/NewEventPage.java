package po_apogen;

import custom_classes.Currencies;
import custom_classes.Participants;
import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class NewEventPage implements PageObject {

	public NewEventComponent newEventComponent;
	public TripNames tripName;

	/**
	 * Page Object for NewEvent (state5) --> AddEditEventContainerPage
	 */
	public NewEventPage(WebDriver driver, TripNames tripName) {
		this.tripName = tripName;
		this.newEventComponent = new NewEventComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("NewEventPage not loaded properly");
		}
	}

	public IndexPage cancel() {
		this.newEventComponent.cancel();
		return new IndexPage(this.newEventComponent.getDriver());
	}

	public HomePage form(Currencies currency, Participants participant1, Participants participant2) {
		if(!participant1.value().equals(participant2.value())){
			this.newEventComponent.form(this.tripName, currency, participant1, participant2);
			return new HomePage(this.newEventComponent.getDriver());
		}else{
			throw new IllegalArgumentException("form: the two participants must be different "
					+ participant1.value() + " " + participant2.value());
		}
	}

	public boolean isPageLoaded() {
		if(this.newEventComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.newEventComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.newEventComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_EVENT.value())){
			return true;
		}
		return false;
	}
}
