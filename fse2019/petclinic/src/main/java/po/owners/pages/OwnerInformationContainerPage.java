package po.owners.pages;

import custom_classes.PetNames;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.owners.components.OwnerInformationComponent;
import po.pets.pages.AddEditPetContainerPage;
import po.pets.pages.AddEditVisitContainerPage;
import po.shared.components.NavbarComponent;
import po.veterinarians.pages.VeterinariansContainerPage;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class OwnerInformationContainerPage implements PageObject {

    public OwnerInformationComponent ownerInformationComponent;
    public NavbarComponent navbarComponent;

    public OwnerInformationContainerPage(WebDriver driver){
        this.ownerInformationComponent = new OwnerInformationComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("OwnerInformationContainerPage not loaded properly");
        }
    }

    public AddEditOwnerContainerPage goToEditOwner() {
        this.ownerInformationComponent.goToEditOwner();
        return new AddEditOwnerContainerPage(this.ownerInformationComponent.getDriver());
    }

    public AddEditPetContainerPage goToAddNewPet() {
        this.ownerInformationComponent.goToAddNewPet();
        return new AddEditPetContainerPage(this.ownerInformationComponent.getDriver());
    }

    // changed it was goToNewPet2
    public AddEditPetContainerPage goToEditPetThroughName(PetNames petName) {
        if(this.ownerInformationComponent.petExists(petName)){
            this.ownerInformationComponent.goToEditPetThroughName(petName);
            return new AddEditPetContainerPage(this.ownerInformationComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("goToEditPetThroughName: pet name "
                    + petName.value() + " does not exist");
        }
    }

    public AddEditPetContainerPage goToEditPetThroghEditLink(PetNames petName) {
        if(this.ownerInformationComponent.petExists(petName)){
            this.ownerInformationComponent.goToEditPetThroghEditLink(petName);
            return new AddEditPetContainerPage(this.ownerInformationComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("goToEditPetThroghEditLink: pet name "
                    + petName.value() + " does not exist");
        }
    }

    public AddEditVisitContainerPage goToVisits(PetNames petName) {
        if(this.ownerInformationComponent.petExists(petName)){
            this.ownerInformationComponent.goToVisits(petName);
            return new AddEditVisitContainerPage(this.ownerInformationComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("goToVisits: pet name "
                    + petName.value() + " does not exist");
        }
    }

    public HomeContainerPage goToHomeThroughLogo(){
        this.navbarComponent.clickOnLogo();
        return new HomeContainerPage(this.navbarComponent.getDriver());
    }

    public HomeContainerPage goToHomeThroughNavbar(){
        this.navbarComponent.clickOnHome();
        return new HomeContainerPage(this.navbarComponent.getDriver());
    }

    public OwnersListContainerPage goToOwnersList(){
        this.navbarComponent.clickOnAll();
        return new OwnersListContainerPage(this.navbarComponent.getDriver());
    }

    public AddEditOwnerContainerPage registerNewOwner(){
        this.navbarComponent.clickOnRegister();
        return new AddEditOwnerContainerPage(this.navbarComponent.getDriver());
    }

    public VeterinariansContainerPage goToVeterinarians(){
        this.navbarComponent.clickOnVets();
        return new VeterinariansContainerPage(this.navbarComponent.getDriver());
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
