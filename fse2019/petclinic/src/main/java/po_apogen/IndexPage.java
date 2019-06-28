package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class IndexPage implements PageObject {

	public IndexComponent indexComponent;

	/**
	 * Page Object for Index (index)
	 */
	public IndexPage(WebDriver driver) {
		this.indexComponent = new IndexComponent(driver);
		if (!this.isPageLoaded())
			throw new IllegalStateException("Index page not loaded properly");
	}

	public WelcomePage goToWelcome() {
		this.indexComponent.goToWelcome();
		return new WelcomePage(this.indexComponent.getDriver());
	}

	public NewOwnerPage goToNewOwner() {
		this.indexComponent.goToNewOwner();
		return new NewOwnerPage(this.indexComponent.getDriver());
	}

	public OwnersPage goToOwners() {
		this.indexComponent.goToOwners();
		return new OwnersPage(this.indexComponent.getDriver());
	}

	public VetsPage goToVets() {
		this.indexComponent.goToVets();
		return new VetsPage(this.indexComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.indexComponent.waitForElementBeingPresentOnPage(ConstantLocators.INDEX.value())){
			return true;
		}
		return false;
	}
}
