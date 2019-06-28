package po_apogen;

import custom_classes.Dates;
import custom_classes.Participants;
import custom_classes.Price;
import custom_classes.Transactions;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class EditTransactionPage implements PageObject {

	public EditTransactionComponent editTransactionComponent;

	/**
	 * Page Object for NewTransaction (state272 is wrong) --> AddEditTransactionContainerPage
	 */
	public EditTransactionPage(WebDriver driver) {
		this.editTransactionComponent = new EditTransactionComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditTransactionPage not loaded properly");
		}
	}

	public HomePage form(Transactions transaction, Participants payer, Price price, Dates date) {
		if(this.editTransactionComponent.isParticipantPresent(payer.value())){
			this.editTransactionComponent.form(transaction, payer, price, date);
			return new HomePage(this.editTransactionComponent.getDriver());
		}else{
			throw new IllegalArgumentException("form: payer " + payer.value() + " is not present");
		}
	}

	public HomePage cancel() {
		this.editTransactionComponent.cancel();
		return new HomePage(this.editTransactionComponent.getDriver());
	}

	public ConfirmationTransactionPage delete() {
		if(this.editTransactionComponent.isEdit()){
			this.editTransactionComponent.delete();
			return new ConfirmationTransactionPage(this.editTransactionComponent.getDriver());
		}else{
			throw new IllegalArgumentException("deleteTransaction: is not edit");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.editTransactionComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.editTransactionComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.editTransactionComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_TRANSACTION.value())){
			return true;
		}
		return false;
	}
}
