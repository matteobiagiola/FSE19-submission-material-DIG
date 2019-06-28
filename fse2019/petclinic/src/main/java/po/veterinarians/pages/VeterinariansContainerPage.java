package po.veterinarians.pages;

import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.owners.pages.AddEditOwnerContainerPage;
import po.owners.pages.OwnersListContainerPage;
import po.shared.components.NavbarComponent;
import po.veterinarians.components.VeterinariansComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class VeterinariansContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public VeterinariansComponent veterinariansComponent;

    public VeterinariansContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.veterinariansComponent = new VeterinariansComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("VeterinariansContainerPage not loaded properly");
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
        if(this.veterinariansComponent
                .waitForElementBeingPresentOnPage(ConstantLocators.VETS_PAGE.value())){
            return true;
        }
        return false;
    }
}
