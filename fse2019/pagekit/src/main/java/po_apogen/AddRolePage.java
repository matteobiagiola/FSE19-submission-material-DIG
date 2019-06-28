package po_apogen;

import custom_classes.UserRoles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddRolePage extends BasePageObject implements PageObject{

	public final String xpathPrefix = "//div[@class=\"uk-modal-dialog\"]";
	public UserRoles userRole;

	/**
	 * Page Object for AddRole (state117) -> AddEditItemPage
	 */
	public AddRolePage(WebDriver driver, UserRoles userRole) {
		super(driver);
		this.userRole = userRole;
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AddRolePage not loaded properly");
		}
	}

	public AnonymousPage cancel() {
		this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
		return new AnonymousPage(this.getDriver());
	}

	public AnonymousPage addRole() {
		this.type(By.xpath(this.xpathPrefix + "//input"), userRole.value());
		this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]"));
		return new AnonymousPage(this.getDriver(), true);
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.ADD_ITEM.value())){
			return true;
		}
		return false;
	}
}
