package po_apogen;

import custom_classes.WalletNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to AddWalletPage

public class AddWalletPage extends BasePageObject implements PageObject{

	/**
	 * Page Object for AddWalletPage (state9)
	 */
	public AddWalletPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
		}
	}

	public WalletPage goToWalletPage() {
		this.clickOn(By.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
		return new WalletPage(this.getDriver());
	}

	public WalletPage edit(WalletNames walletName) {
		this.typeJS(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_name\"]"), walletName.value());
		this.clickOn(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
		return new WalletPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.ADD_WALLET.value())){
			return true;
		}
		return false;
	}
}
