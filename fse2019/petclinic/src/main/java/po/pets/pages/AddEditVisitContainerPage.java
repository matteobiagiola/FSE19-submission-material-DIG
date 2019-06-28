package po.pets.pages;

import custom_classes.Dates;
import custom_classes.VisitDescriptions;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.owners.pages.AddEditOwnerContainerPage;
import po.owners.pages.OwnerInformationContainerPage;
import po.owners.pages.OwnersListContainerPage;
import po.pets.components.AddEditVisitComponent;
import po.shared.components.NavbarComponent;
import po.veterinarians.pages.VeterinariansContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddEditVisitContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public AddEditVisitComponent addEditVisitComponent;

    public AddEditVisitContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.addEditVisitComponent = new AddEditVisitComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditVisitContainerPage not loaded properly");
        }
    }

    public OwnerInformationContainerPage addVisit(Dates date, VisitDescriptions visitDescription) {
        this.addEditVisitComponent.addVisit(date, visitDescription);
        return new OwnerInformationContainerPage(this.addEditVisitComponent.getDriver());
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
        if(this.addEditVisitComponent
                .waitForElementBeingPresentOnPage(ConstantLocators.VISITS_PAGE.value())){
            return true;
        }
        return false;
    }
}
