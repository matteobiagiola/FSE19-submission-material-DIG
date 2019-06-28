package po_apogen;

import custom_classes.Amount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to SetTotalIncomeToWalletPage

public class SetTotalPage extends BasePageObject implements PageObject{

	/**
	 * Page Object for SetTotalPage (state31)
	 */
	public SetTotalPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
		}
	}

	public TransactionsPage addProfit(Amount amount) {
		this.typeJS(By.id("input_total"), String.valueOf(amount.value));
		this.clickOn(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
		return new TransactionsPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.SET_TOTAL_INCOME.value())){
			return true;
		}
		return false;
	}
}
