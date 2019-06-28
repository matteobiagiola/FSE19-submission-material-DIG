package po_apogen;

import custom_classes.Dates;
import custom_classes.PetNames;
import custom_classes.PetTypes;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class NewPetPage implements PageObject {

	public NewPetComponent newPetComponent;

	/**
	 * Page Object for NewPet (state10)
	 */
	public NewPetPage(WebDriver driver) {
		this.newPetComponent = new NewPetComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("NewPetPage not loaded properly");
		}
	}
	public OwnerInformationPage submit(PetNames petName, Dates date, PetTypes petType){
		this.newPetComponent.submit(petName, date, petType);
		return new OwnerInformationPage(this.newPetComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.newPetComponent.waitForElementBeingPresentOnPage(ConstantLocators.NEW_PET_PAGE.value())){
			return true;
		}
		return false;
	}

}
