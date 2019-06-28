package po.home.pages;

import custom_classes.TripNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.event.pages.AddEditEventContainerPage;
import po.event.pages.EventDetailsContainerPage;
import po.home.components.HomeComponent;
import po.shared.components.NavbarComponent;
import po.shared.pages.ConfirmationPage;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class HomePageContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public HomeComponent homeComponent;

    public HomePageContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.homeComponent = new HomeComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("HomePageContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditEventContainerPage createNewEventNavbar(TripNames tripName){
        if(!this.homeComponent.isEventPresent(tripName.value())){
            this.navbarComponent.createNewEvent();
            String poCallee = this.getClass().getSimpleName();
            return new AddEditEventContainerPage(this.navbarComponent.getDriver(), poCallee, tripName.value());
        }else{
            throw new IllegalArgumentException("createNewEventNavbar: tripName " + tripName.value() + " already exists");
        }
    }

    //tested
    public AddEditEventContainerPage createNewEventHome(TripNames tripName){
        if(!this.homeComponent.isEventPresent(tripName.value())){
            this.homeComponent.createNewEvent();
            String poCallee = this.getClass().getSimpleName();
            return new AddEditEventContainerPage(this.homeComponent.getDriver(), poCallee, tripName.value());
        }else{
            throw new IllegalArgumentException("createNewEventHome: tripName " + tripName.value() + " already exists");
        }
    }

    //tested
    public EventDetailsContainerPage seeEventDetails(TripNames tripName){
        if(this.homeComponent.isEventPresent(tripName.value())){
            this.homeComponent.clickOnTrip(tripName.value());
            return new EventDetailsContainerPage(this.homeComponent.getDriver());
        }else{
            throw new IllegalArgumentException("seeEventDetails: tripName " + tripName.value() + " does not exist");
        }
    }

    //tested
    public ConfirmationPage deleteEvent(TripNames tripName){
        if(this.homeComponent.isEventPresent(tripName.value())){
            this.homeComponent.deleteTrip(tripName.value());
            String poCallee = this.getClass().getSimpleName();
            return new ConfirmationPage(this.homeComponent.getDriver(), poCallee);
        }else{
            throw new IllegalArgumentException("deleteEvent: tripName " + tripName.value() + " does not exist");
        }
    }

    //tested
    public HomePageContainerPage goToAbout(){
        this.navbarComponent.goToAbout();
        return this;
    }

    //tested
    public HomePageContainerPage goToFeatures(){
        this.navbarComponent.goToFeatures();
        return this;
    }

    //tested
    public HomePageContainerPage goToEvents(){
        if(this.navbarComponent.isEventsPresent()){
            this.navbarComponent.goToEvents();
            return this;
        }else{
            throw new IllegalStateException("goToEvents: events are not present");
        }
    }


    public boolean isPageLoaded() {
        if(this.homeComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.homeComponent.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.homeComponent.waitForElementBeingPresentOnPage(ConstantLocators.HOME.value())){
            return true;
        }
        return false;
    }
}
