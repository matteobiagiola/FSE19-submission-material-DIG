package po_apogen;

import custom_classes.Currencies;
import custom_classes.Participants;
import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class EditEventPage implements PageObject {

	public EditEventComponent editEventComponent;
	public TripNames tripName;

	/**
	 * Page Object for EditEvent (state177) --> AddEditEventContainerPage
	 */
	public EditEventPage(WebDriver driver, TripNames tripName) {
		this.tripName = tripName;
		this.editEventComponent = new EditEventComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditEventPage not loaded properly");
		}
	}

	public ConfirmDeletionPage goToConfirmDeletionPage() {
		if(this.editEventComponent.isEdit()){
			this.editEventComponent.goToConfirmDeletionPage();
			return new ConfirmDeletionPage(this.editEventComponent.getDriver(), this.tripName);
		}else{
			throw new IllegalArgumentException("deleteEvent: is not edit");
		}
	}

	public HomePage form(Currencies currency, Participants participant1, Participants participant2) {
		if(!participant1.value().equals(participant2.value())){
			this.editEventComponent.form(this.tripName, currency, participant1, participant2);
			return new HomePage(this.editEventComponent.getDriver());
		}else{
			throw new IllegalArgumentException("form: the two participants must be different "
					+ participant1.value() + " " + participant2.value());
		}
	}

	public HomePage cancel() {
		this.editEventComponent.cancel();
		return new HomePage(this.editEventComponent.getDriver());
	}

	public boolean isPageLoaded() {
		if(this.editEventComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.editEventComponent.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.editEventComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_EVENT.value())){
			return true;
		}
		return false;
	}
}
