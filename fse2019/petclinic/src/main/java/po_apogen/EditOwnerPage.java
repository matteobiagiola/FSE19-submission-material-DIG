package po_apogen;

import custom_classes.Addresses;
import custom_classes.Cities;
import custom_classes.FirstNames;
import custom_classes.LastNames;
import custom_classes.Telephones;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class EditOwnerPage implements PageObject {

	public EditOwnerComponent editOwnerComponent;

	/**
	 * Page Object for EditOwner (state7)
	 */
	public EditOwnerPage(WebDriver driver) {
		this.editOwnerComponent = new EditOwnerComponent(driver);
		if (!this.isPageLoaded()) {
			throw new IllegalStateException("EditOwner page not loaded properly");
		}
	}

	public OwnerInformationPage editOwner(FirstNames firstName, LastNames lastName, Addresses address, Cities city, Telephones telephone){
		this.editOwnerComponent.editOwner(firstName, lastName, address, city, telephone);
		return new OwnerInformationPage(this.editOwnerComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.editOwnerComponent.waitForElementBeingPresentOnPage(ConstantLocators.NEW_OWNER_PAGE.value())){
			return true;
		}
		return false;
	}
}
