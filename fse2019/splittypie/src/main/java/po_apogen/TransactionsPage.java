package po_apogen;

import custom_classes.Id;
import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class TransactionsPage implements PageObject {

	public TransactionsComponent transactionsComponent;

	/**
	 * Page Object for Transactions (state182) --> EventDetailsContainerPage
	 */
	public TransactionsPage(WebDriver driver) {
		this.transactionsComponent = new TransactionsComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("TransactionsPage not loaded properly");
		}
	}

	public EditEventPage goToEditEvent(TripNames tripName) {
		if(!this.transactionsComponent.isTripPresent(tripName.value())){
			this.transactionsComponent.goToEditEvent();
			return new EditEventPage(this.transactionsComponent.getDriver(), tripName);
		}else{
			throw new IllegalArgumentException("goToEditEvent: new trip name " + tripName.value() + " already exists");
		}
	}

	public AddTransactionPage goToAddTransactionPage() {
		this.transactionsComponent.goToAddTransactionPage();
		return new AddTransactionPage(this.transactionsComponent.getDriver());
	}

	public EditTransactionPage goToEditTransactionPage(Id id) {
		if(this.transactionsComponent.isTransactionsViewActive()
				&& this.transactionsComponent.isTransactionPresent(id.value)
				&& this.transactionsComponent.isTransaction(id.value)){
			this.transactionsComponent.clickOnTransaction(id.value);
			return new EditTransactionPage(this.transactionsComponent.getDriver());
		}else{
			throw new IllegalArgumentException("goToEditTransactionPage: transaction with id " + id.value
					+ " is not present or it is not a transaction or the transaction view is not active");
		}
	}

	public boolean isPageLoaded() {
		if(this.transactionsComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.transactionsComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.transactionsComponent.waitForElementBeingPresentOnPage(ConstantLocators.EVENT_DETAILS.value())){
			return true;
		}
		return false;
	}
}
