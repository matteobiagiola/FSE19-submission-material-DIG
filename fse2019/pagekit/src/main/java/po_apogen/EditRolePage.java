package po_apogen;

import custom_classes.UserRoles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class EditRolePage extends BasePageObject implements PageObject {

	public UserRoles userRole;
	public final String xpathPrefix = "//div[@class=\"uk-modal-dialog\"]";

	/**
	 * Page Object for EditRole (state204) --> AddEditItemPage
	 */
	public EditRolePage(WebDriver driver, UserRoles userRole) {
		super(driver);
		this.userRole = userRole;
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditRolePage not loaded properly");
		}
	}

	public AnonymousPage goToAnonymous() {
		this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
		return new AnonymousPage(this.getDriver());
	}

	public AnonymousPage editRole() {
		this.type(By.xpath(this.xpathPrefix + "//input"), this.userRole.value());
		this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]"));
		return new AnonymousPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.ADD_ITEM.value())){
			return true;
		}
		return false;
	}
}
