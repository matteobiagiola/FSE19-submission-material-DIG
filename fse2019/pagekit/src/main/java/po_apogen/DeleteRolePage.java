package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class DeleteRolePage extends BasePageObject implements PageObject {

	/**
	 * Page Object for DeleteRole (state195) --> DeleteItemPage
	 */
	public DeleteRolePage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("DeleteRolePage not loaded properly");
		}
	}

	public AnonymousPage goToAnonymous() {
		this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
		return new AnonymousPage(this.getDriver());
	}

	public AnonymousPage deleteRole() {
		this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
		return new AnonymousPage(this.getDriver(), "wait");
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.DELETE_ITEM.value())){
			return true;
		}
		return false;
	}
}
