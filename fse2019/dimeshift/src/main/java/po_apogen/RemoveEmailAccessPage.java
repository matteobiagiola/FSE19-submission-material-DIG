package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to ConfirmationPage

public class RemoveEmailAccessPage extends BasePageObject implements PageObject{

	/**
	 * Page Object for RemoveEmailAccessPage (state70): ConfirmationPage
	 */
	public RemoveEmailAccessPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
		}
	}

	public AccessEmailPage remove() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]"));
		return new AccessEmailPage(this.getDriver());
	}

	public AccessEmailPage cancel() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"btn btn-primary pull-left\"]"));
		return new AccessEmailPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.CONFIRMATION.value())){
			return true;
		}
		return false;
	}
}
