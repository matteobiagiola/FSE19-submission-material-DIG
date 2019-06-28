package po_apogen;

import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class IndexPage implements PageObject {

	public IndexComponent indexComponent;

	/**
	 * Page Object for Index (index) --> HomePageContainerPage
	 */
	public IndexPage(WebDriver driver) {
		this.indexComponent = new IndexComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("IndexPage not loaded properly");
		}
	}

	// changed: it was goToNew
	public NewEventPage goToNewEventNavbar(TripNames tripName) {
		if(!this.indexComponent.isEventPresent(tripName.value())){
			this.indexComponent.goToNewEventNavbar();
			return new NewEventPage(this.indexComponent.getDriver(), tripName);
		}else{
			throw new IllegalArgumentException("goToNewEventNavbar: tripName " + tripName.value() + " already exists");
		}
	}

	// changed: it was goToNew_1
	public NewEventPage goToNewEventHome(TripNames tripName) {
		if(!this.indexComponent.isEventPresent(tripName.value())){
			this.indexComponent.goToNewEventHome();
			return new NewEventPage(this.indexComponent.getDriver(), tripName);
		}else{
			throw new IllegalArgumentException("goToNewEventHome: tripName " + tripName.value() + " already exists");
		}
	}

	public HomePage goToHomePage(TripNames tripName) {
		if(this.indexComponent.isEventPresent(tripName.value())){
			this.indexComponent.goToHomePage(tripName);
			return new HomePage(this.indexComponent.getDriver());
		}else{
			throw new IllegalArgumentException("goToHomePage: tripName " + tripName.value() + " does not exist");
		}
	}
	
	public ConfirmationPage goToConfirmation(TripNames tripName) {
		if(this.indexComponent.isEventPresent(tripName.value())){
			this.indexComponent.goToConfirmation(tripName);
			return new ConfirmationPage(this.indexComponent.getDriver());
		}else{
			throw new IllegalArgumentException("goToConfirmation: tripName " + tripName.value() + " does not exist");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.indexComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.indexComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.indexComponent.waitForElementBeingPresentOnPage(ConstantLocators.HOME.value())){
			return true;
		}
		return false;
	}
}
