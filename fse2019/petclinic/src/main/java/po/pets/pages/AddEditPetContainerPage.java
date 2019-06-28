package po.pets.pages;

import custom_classes.Dates;
import custom_classes.LongPetNames;
import custom_classes.PetTypes;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.owners.pages.AddEditOwnerContainerPage;
import po.owners.pages.OwnerInformationContainerPage;
import po.owners.pages.OwnersListContainerPage;
import po.pets.components.AddEditPetComponent;
import po.shared.components.NavbarComponent;
import po.veterinarians.pages.VeterinariansContainerPage;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class AddEditPetContainerPage implements PageObject {

    public AddEditPetComponent addEditPetComponent;
    public NavbarComponent navbarComponent;

    public AddEditPetContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.addEditPetComponent = new AddEditPetComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditPetContainerPage not loaded properly");
        }
    }

    public AddEditPetContainerPage addNewPetWithLongName(LongPetNames petName, Dates date, PetTypes petType){
        if(petName.value().length() > 40){
            this.addEditPetComponent.addNewPet(petName.value(), date.value(), petType.value());
            return new AddEditPetContainerPage(this.addEditPetComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("addNewPet: pet name is too short");
        }
    }

    public OwnerInformationContainerPage addNewPet(LongPetNames petName, Dates date, PetTypes petType){
        if(petName.value().length() <= 40){
            this.addEditPetComponent.addNewPet(petName.value(), date.value(), petType.value());
            return new OwnerInformationContainerPage(this.addEditPetComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("addNewPet: pet name is too long");
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
        if(this.addEditPetComponent.waitForElementBeingPresentOnPage(ConstantLocators.NEW_PET_PAGE.value())){
            return true;
        }
        return false;
    }
}
