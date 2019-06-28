package po_apogen;

import custom_classes.FirstNames;
import custom_classes.LastNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class OwnersPage implements PageObject {

	public OwnersComponent ownersComponent;

	/**
	 * Page Object for Owners (state5)
	 */
	public OwnersPage(WebDriver driver) {
		this.ownersComponent = new OwnersComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("OwnersPage not loaded properly");
		}
	}

	public OwnerInformationPage goToOwnerInformation(FirstNames firstName, LastNames lastName) {
		if(this.ownersComponent.isOwnerPresent(firstName, lastName)){
			this.ownersComponent.goToOwnerInformation(firstName, lastName);
			return new OwnerInformationPage(this.ownersComponent.getDriver());
		}else{
			throw new NotTheRightInputValuesException("goToOwnerInformation: owner " + firstName.value()
					+ " " + lastName.value() + " does not exist");
		}
	}

	public OwnersPage searchFilter(FirstNames firstName, LastNames lastName){
		if(this.ownersComponent.isOwnerPresent(firstName, lastName)){
			this.ownersComponent.searchFilter(firstName);
			return new OwnersPage(this.ownersComponent.getDriver());
		}else{
			throw new NotTheRightInputValuesException("searchFilter: owner " + firstName.value()
					+ " " + lastName.value() + " does not exist");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.ownersComponent.waitForElementBeingPresentOnPage(ConstantLocators.OWNERS_PAGE_TABLE.value(), 1000)
                || this.ownersComponent
				.waitForElementBeingPresentOnPage(ConstantLocators.OWNERS_PAGE.value())){
			return true;
		}
		return false;
	}

}
