package po.owners.pages;

import custom_classes.FirstNames;
import custom_classes.LastNames;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.owners.components.OwnersListComponent;
import po.shared.components.NavbarComponent;
import po.veterinarians.pages.VeterinariansContainerPage;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class OwnersListContainerPage implements PageObject {

    public OwnersListComponent ownersListComponent;
    public NavbarComponent navbarComponent;

    public OwnersListContainerPage(WebDriver driver){
        this.ownersListComponent = new OwnersListComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("OwnersListContainerPage not loaded properly");
        }
    }

    public OwnerInformationContainerPage goToOwnerInformation(FirstNames firstName, LastNames lastName) {
        if(this.ownersListComponent.isOwnerPresent(firstName, lastName)){
            this.ownersListComponent.goToOwnerInformation(firstName, lastName);
            return new OwnerInformationContainerPage(this.ownersListComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("goToOwnerInformation: owner " + firstName.value()
                    + " " + lastName.value() + " does not exist");
        }
    }

    public OwnersListContainerPage searchFilter(FirstNames firstName, LastNames lastName){
        if(this.ownersListComponent.isOwnerPresent(firstName, lastName)){
            this.ownersListComponent.searchFilter(firstName);
            return new OwnersListContainerPage(this.ownersListComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException("searchFilter: owner " + firstName.value()
                    + " " + lastName.value() + " does not exist");
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
        if(this.ownersListComponent.waitForElementBeingPresentOnPage(ConstantLocators.OWNERS_PAGE_TABLE.value(), 1000)
                || this.ownersListComponent
                .waitForElementBeingPresentOnPage(ConstantLocators.OWNERS_PAGE.value())){
            return true;
        }
        return false;
    }
}
