package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class ConfirmationTransactionPage extends BasePageObject implements PageObject {

	/**
	 * Page Object for Confirmation (state299 is wrong) --> ConfirmationPage
	 */
	public ConfirmationTransactionPage(WebDriver driver) {
		super(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("ConfirmationTransactionPage not loaded properly");
		}
	}

	public HomePage yes() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new HomePage(this.getDriver());
	}

	public EditTransactionPage no() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new EditTransactionPage(this.getDriver());
	}

	public EditTransactionPage close() {
		this.clickOn(By.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new EditTransactionPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.CONFIRMATION.value())){
			return true;
		}
		return false;
	}
}
