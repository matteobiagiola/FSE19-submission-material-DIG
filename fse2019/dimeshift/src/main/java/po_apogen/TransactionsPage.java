package po_apogen;

import custom_classes.Amount;
import custom_classes.TransactionDescription;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to TransactionManagerPage

public class TransactionsPage implements PageObject {

	public TransactionsComponent transactionsComponent;

	/**
	 * Page Object for Transactions (state7)
	 */
	public TransactionsPage(WebDriver driver) {
		this.transactionsComponent = new TransactionsComponent(driver);
		if(!isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
		}
	}

	public AddIncomePage goToAddIncomePage() {
		this.transactionsComponent.goToAddIncomePage();
		return new AddIncomePage(this.transactionsComponent.getDriver());
	}

	public SetTotalPage goToSetTotalPage() {
		this.transactionsComponent.goToSetTotalPage();
		return new SetTotalPage(this.transactionsComponent.getDriver());
	}

	public WalletPage goToWalletPage() {
		this.transactionsComponent.goToWalletPage();
		return new WalletPage(this.transactionsComponent.getDriver());
	}

	public TransactionsPage addTransaction(TransactionDescription description, Amount amount){
		this.transactionsComponent.addTransaction(description, amount);
		return new TransactionsPage(this.transactionsComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded(){
		if(this.transactionsComponent.waitForElementBeingPresentOnPage(ConstantLocators.TRANSACTIONS_MANAGER.value())){
			return true;
		}
		return false;
	}
}
