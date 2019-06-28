package po_apogen;

import custom_classes.PetNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class OwnerInformationPage implements PageObject {

	public OwnerInformationComponent ownerInformationComponent;

	/**
	 * Page Object for OwnerInformation (state6)
	 */
	public OwnerInformationPage(WebDriver driver) {
		this.ownerInformationComponent = new OwnerInformationComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("OwnerInformationPage not loaded properly");
		}
	}

	public EditOwnerPage goToEditOwner() {
		this.ownerInformationComponent.goToEditOwner();
		return new EditOwnerPage(this.ownerInformationComponent.getDriver());
	}

	// changed it was goToNewPet1
	public NewPetPage goToAddNewPet() {
		this.ownerInformationComponent.goToAddNewPet();
		return new NewPetPage(this.ownerInformationComponent.getDriver());
	}

	// changed it was goToNewPet2
	public NewPetPage goToEditPetThroughName(PetNames petName) {
		if(this.ownerInformationComponent.petExists(petName)){
			this.ownerInformationComponent.goToEditPetThroughName(petName);
			return new NewPetPage(this.ownerInformationComponent.getDriver());
		}else{
			throw new NotTheRightInputValuesException("goToEditPetThroughName: pet name "
					+ petName.value() + " does not exist");
		}
	}

	// changed it was goToNewPet3
	public NewPetPage goToEditPetThroghEditLink(PetNames petName) {
		if(this.ownerInformationComponent.petExists(petName)){
			this.ownerInformationComponent.goToEditPetThroghEditLink(petName);
			return new NewPetPage(this.ownerInformationComponent.getDriver());
		}else{
			throw new NotTheRightInputValuesException("goToEditPetThroghEditLink: pet name "
					+ petName.value() + " does not exist");
		}
	}

	public VisitsPage goToVisits(PetNames petName) {
		if(this.ownerInformationComponent.petExists(petName)){
			this.ownerInformationComponent.goToVisits(petName);
			return new VisitsPage(this.ownerInformationComponent.getDriver());
		}else{
			throw new NotTheRightInputValuesException("goToVisits: pet name "
					+ petName.value() + " does not exist");
		}
	}

	/*---- added */
	public IndexPage goToIndex(){
		this.ownerInformationComponent.goToIndex();
		return new IndexPage(this.ownerInformationComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.ownerInformationComponent.waitForElementBeingPresentOnPage(ConstantLocators.OWNER_INFO_PAGE_SECOND_TABLE.value(), 1000)
				|| this.ownerInformationComponent
				.waitForElementBeingPresentOnPage(ConstantLocators.OWNER_INFO_PAGE.value())){
			return true;
		}
		return false;
	}

}
