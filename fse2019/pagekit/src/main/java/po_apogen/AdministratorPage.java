package po_apogen;

import org.openqa.selenium.*;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AdministratorPage implements PageObject {

	public AdministratorComponent administratorComponent;

	/**
	 * Page Object for Anonymous (state111) --> RolesContainerPage
	 */
	public AdministratorPage(WebDriver driver) {
		this.administratorComponent = new AdministratorComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AdministratorPage not loaded properly");
		}
	}

	public AnonymousPage goToAnonymous() {
		this.administratorComponent.goToAnonymous();
		return new AnonymousPage(this.administratorComponent.getDriver());
	}

	public AdministratorPage goToAdministrator() {
		this.administratorComponent.goToAdministrator();
		return new AdministratorPage(this.administratorComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.administratorComponent.waitForElementBeingPresentOnPage(ConstantLocators.ROLES.value())){
			return true;
		}
		return false;
	}
}
