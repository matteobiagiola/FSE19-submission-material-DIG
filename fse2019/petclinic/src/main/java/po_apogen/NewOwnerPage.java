package po_apogen;

import custom_classes.Addresses;
import custom_classes.Cities;
import custom_classes.FirstNames;
import custom_classes.LastNames;
import custom_classes.Telephones;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class NewOwnerPage implements PageObject {

	public NewOwnerComponent newOwnerComponent;

	/**
	 * Page Object for NewOwner (state8)
	 */
	public NewOwnerPage(WebDriver driver) {
		this.newOwnerComponent = new NewOwnerComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("NewOwner page not loaded properly");
		}
	}

	public OwnersPage submitOwnerForm(FirstNames firstName, LastNames lastName, Addresses address, Cities city, Telephones telephone){
		this.newOwnerComponent.submitOwnerForm(firstName, lastName, address, city, telephone);
		return new OwnersPage(this.newOwnerComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.newOwnerComponent.waitForElementBeingPresentOnPage(ConstantLocators.NEW_OWNER_PAGE.value())){
			return true;
		}
		return false;
	}
}
