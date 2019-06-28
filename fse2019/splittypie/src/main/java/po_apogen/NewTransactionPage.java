package po_apogen;

import custom_classes.Dates;
import custom_classes.Participants;
import custom_classes.Price;
import custom_classes.Transactions;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class NewTransactionPage implements PageObject {

	public NewTransactionComponent newTransactionComponent;

	/**
	 * Page Object for NewTransaction (state272 is wrong) --> AddEditTransactionContainerPage
	 */
	public NewTransactionPage(WebDriver driver) {
		this.newTransactionComponent = new NewTransactionComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("NewTransactionPage not loaded properly");
		}
	}

	public HomePage form(Transactions transaction, Participants payer, Price price, Dates date) {
		if(this.newTransactionComponent.isParticipantPresent(payer.value())){
			this.newTransactionComponent.form(transaction, payer, price, date);
			return new HomePage(this.newTransactionComponent.getDriver());
		}else{
			throw new IllegalArgumentException("form: payer " + payer.value() + " is not present");
		}
	}

	public HomePage cancel() {
		this.newTransactionComponent.cancel();
		return new HomePage(this.newTransactionComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.newTransactionComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.newTransactionComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.newTransactionComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_TRANSACTION.value())){
			return true;
		}
		return false;
	}
}
