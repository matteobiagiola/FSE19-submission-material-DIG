package po.owners.pages;

import custom_classes.Addresses;
import custom_classes.Cities;
import custom_classes.LastNames;
import custom_classes.LongFirstNames;
import custom_classes.LongTelephones;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.owners.components.AddEditOwnerComponent;
import po.shared.components.NavbarComponent;
import po.veterinarians.pages.VeterinariansContainerPage;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class AddEditOwnerContainerPage implements PageObject {

    public AddEditOwnerComponent addEditOwnerComponent;
    public NavbarComponent navbarComponent;

    public AddEditOwnerContainerPage(WebDriver driver){
        this.addEditOwnerComponent = new AddEditOwnerComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditOwnerContainerPage not loaded properly");
        }
    }

    public OwnersListContainerPage registerOwner(LongFirstNames firstName, LastNames lastName,
                                                   Addresses address, Cities city, LongTelephones telephone){
        if(telephone.value().length() <= 10 && firstName.value().length() < 40){
            this.addEditOwnerComponent.registerOwner(firstName.value(), lastName.value(),
                    address.value(), city.value(), telephone.value());
            return new OwnersListContainerPage(this.addEditOwnerComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException(
                    "registerOwner: either telephone number or first name is too long");
        }
    }

//    public ErrorModalPage registerOwnerWithLongTelephoneNumber(LongFirstNames firstName, LastNames lastName,
//                                                               Addresses address, Cities city, LongTelephones telephone){
//        if(telephone.value().length() > 10 && firstName.value().length() < 40){
//            this.addEditOwnerComponent.registerOwner(firstName.value(), lastName.value(),
//                    address.value(), city.value(), telephone.value());
//            return new ErrorModalPage(this.addEditOwnerComponent.getDriver());
//        }else{
//            throw new NotTheRightInputValuesException(
//                    "registerOwnerWithLongTelephoneNumber: telephone number too short or first name too long");
//        }
//    }

    public AddEditOwnerContainerPage registerOwnerWithLongFirstName(LongFirstNames firstName, LastNames lastName,
                                                               Addresses address, Cities city, LongTelephones telephone){
        if(firstName.value().length() > 40 && telephone.value().length() <= 10){
            this.addEditOwnerComponent.registerOwner(firstName.value(), lastName.value(),
                    address.value(), city.value(), telephone.value());
            return new AddEditOwnerContainerPage(this.addEditOwnerComponent.getDriver());
        }else{
            throw new NotTheRightInputValuesException(
                    "registerOwnerWithLongFirstName: first name too short or telephone number is too long");
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
        if(this.addEditOwnerComponent.waitForElementBeingPresentOnPage(ConstantLocators.NEW_OWNER_PAGE.value())){
            return true;
        }
        return false;
    }
}
