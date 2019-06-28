package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class PermissionsPage implements PageObject {

	public PermissionsComponent permissionsComponent;

	/**
	 * Page Object for Permissions (state9) --> PermissionsContainerPage
	 */
	public PermissionsPage(WebDriver driver) {
		this.permissionsComponent = new PermissionsComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("PermissionsPage not loaded properly");
		}
	}

	public UsersPage goToUsers() {
		this.permissionsComponent.goToUsers();
		return new UsersPage(this.permissionsComponent.getDriver());
	}

	public PermissionsPage goToPermissions() {
		this.permissionsComponent.goToPermissions();
		return new PermissionsPage(this.permissionsComponent.getDriver());
	}

	public AnonymousPage goToAnonymous() {
		this.permissionsComponent.goToAnonymous();
		return new AnonymousPage(this.permissionsComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.permissionsComponent.waitForElementBeingPresentOnPage(ConstantLocators.PERMISSIONS.value())){
			return true;
		}
		return false;
	}
}
