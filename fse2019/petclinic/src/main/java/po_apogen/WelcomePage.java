package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class WelcomePage implements PageObject {

	public WelcomeComponent welcomeComponent;

	/**
	 * Page Object for Welcome (state3)
	 */
	public WelcomePage(WebDriver driver) {
		this.welcomeComponent = new WelcomeComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("WelcomePage not loaded properly");
		}
	}

	public IndexPage goToIndex() {
		this.welcomeComponent.goToIndex();
		return new IndexPage(this.welcomeComponent.getDriver());
	}

	public OwnersPage goToOwners() {
		this.welcomeComponent.goToOwners();
		return new OwnersPage(this.welcomeComponent.getDriver());
	}

	public NewOwnerPage goToNewOwner() {
		this.welcomeComponent.goToNewOwner();
		return new NewOwnerPage(this.welcomeComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.welcomeComponent.waitForElementBeingPresentOnPage(ConstantLocators.WELCOME.value())){
			return true;
		}
		return false;
	}
}
