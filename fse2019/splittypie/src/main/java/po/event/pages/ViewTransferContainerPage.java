package po.event.pages;

import org.openqa.selenium.WebDriver;
import po.event.components.AddEditTransactionComponent;
import po.event.components.ViewTransferComponent;
import po.home.pages.HomePageContainerPage;
import po.shared.components.NavbarComponent;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class ViewTransferContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public ViewTransferComponent viewTransferComponent;

    public ViewTransferContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.viewTransferComponent = new ViewTransferComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("ViewTransferContainerPage not loaded properly");
        }
    }

    //tested
    public EventDetailsContainerPage cancel(){
        this.viewTransferComponent.cancel();
        return new EventDetailsContainerPage(this.viewTransferComponent.getDriver());
    }

    //tested
    public HomePageContainerPage goToHome(){
        this.navbarComponent.goToHomePage();
        return new HomePageContainerPage(this.navbarComponent.getDriver());
    }

    public boolean isPageLoaded() {
        if(this.viewTransferComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.viewTransferComponent.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.viewTransferComponent.waitForElementBeingPresentOnPage(ConstantLocators.VIEW_TRANSFER.value())){
            return true;
        }
        return false;
    }
}
