package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to ConfirmationPage

public class HideWalletPage extends BasePageObject implements PageObject{

	/**
	 * Page Object for HideWalletPage (state14): ConfirmationPage
	 */
	public HideWalletPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
		}
	}

	public WalletPage goToWalletPage() {
		this.clickOn(By.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
		return new WalletPage(this.getDriver());
	}

	public WalletPage hide() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]"));
		return new WalletPage(this.getDriver());
	}

	public WalletPage cancel() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"btn btn-primary pull-left\"]"));
		return new WalletPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.CONFIRMATION.value())){
			return true;
		}
		return false;
	}
}
