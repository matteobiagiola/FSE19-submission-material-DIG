package po_apogen;

import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class HomePage implements PageObject {

	public HomeComponent homeComponent;

	/**
	 * Page Object for HomePage (state176) --> EventDetailsContainerPage
	 */
	public HomePage(WebDriver driver) {
		this.homeComponent = new HomeComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("HomePage not loaded properly");
		}
	}

	public EditEventPage goToEditEvent(TripNames tripName) {
		if(!this.homeComponent.isTripPresent(tripName.value())){
			this.homeComponent.goToEditEvent();
			return new EditEventPage(this.homeComponent.getDriver(), tripName);
		}else{
			throw new IllegalArgumentException("goToEditEvent: new trip name " + tripName.value() + " already exists");
		}
	}

	public HomePage goToHomePage() {
		this.homeComponent.goToHomePage();
		return new HomePage(this.homeComponent.getDriver());
	}

	public TransactionsPage goToTransactions() {
		this.homeComponent.goToTransactions();
		return new TransactionsPage(this.homeComponent.getDriver());
	}

	/*------ added otherwise some methods in IndexPage would have been infeasible */
	public IndexPage goToIndexPage(){
		this.homeComponent.goToIndexPage();
		return new IndexPage(this.homeComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.homeComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.homeComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.homeComponent.waitForElementBeingPresentOnPage(ConstantLocators.EVENT_DETAILS.value())){
			return true;
		}
		return false;
	}
}
