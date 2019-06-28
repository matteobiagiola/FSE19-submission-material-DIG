package po_apogen;

import custom_classes.Email;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to WalletAccessManagerPage

public class GiveAccessToWalletPage extends BasePageObject implements PageObject {

	/**
	 * Page Object for GiveAccessToWalletPage (state18)
	 */
	public GiveAccessToWalletPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
		}
	}

	public WalletPage goToWalletPage() {
		this.clickOn(By.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
		return new WalletPage(this.getDriver());
	}

	public AccessEmailPage addAccess(Email email){
		this.typeJS(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_email\"]"), email.value());
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@type=\"submit\"]"));
		return new AccessEmailPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.WALLET_ACCESS_MANAGER.value())){
			return true;
		}
		return false;
	}
}
