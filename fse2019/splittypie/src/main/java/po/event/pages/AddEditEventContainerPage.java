package po.event.pages;

import custom_classes.Currencies;
import custom_classes.Participants;
import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po.event.components.AddEditEventComponent;
import po.home.pages.HomePageContainerPage;
import po.shared.components.NavbarComponent;
import po.shared.pages.ConfirmationPage;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class AddEditEventContainerPage implements PageObject {

    public String tripName;
    public String poCallee;
    public final String eventDetails = "EventDetailsContainerPage";
    public final String home = "HomePageContainerPage";
    public NavbarComponent navbarComponent;
    public AddEditEventComponent addEditEventComponent;

    public AddEditEventContainerPage(WebDriver driver, String poCallee, String tripName){
        this.navbarComponent = new NavbarComponent(driver);
        this.addEditEventComponent = new AddEditEventComponent(driver);
        this.poCallee = poCallee;
        this.tripName = tripName;
        if(!this.isPageLoaded()){
            throw new IllegalStateException("CreateEventContainerPage not loaded properly");
        }
    }

    //tested
    public EventDetailsContainerPage createEvent(Currencies currency, Participants participant1, Participants participant2){
        if(!participant1.value().equals(participant2.value())){
            this.addEditEventComponent.typeTripName(this.tripName);
            this.addEditEventComponent.selectCurrency(currency.value());
            this.addEditEventComponent.typeParticipant(participant1.value(), 0);
            this.addEditEventComponent.typeParticipant(participant2.value(), 1);
            this.addEditEventComponent.createOrSave();
            return new EventDetailsContainerPage(this.addEditEventComponent.getDriver());
        }else{
            throw new IllegalArgumentException("createEvent: the two participants must be different " + participant1.value() + " " + participant2.value());
        }
    }

    public PageObject cancel(){
        if(this.poCallee.equals(this.eventDetails)){
            this.addEditEventComponent.cancel();
            return new EventDetailsContainerPage(this.addEditEventComponent.getDriver());
        }else if(this.poCallee.equals(this.home)){
            this.addEditEventComponent.cancel();
            return new HomePageContainerPage(this.addEditEventComponent.getDriver());
        }else{
            throw new IllegalStateException("cancel: unknown poCallee " + this.poCallee);
        }
    }
    //tested
    public HomePageContainerPage goToHomePage(){
        this.navbarComponent.goToHomePage();
        return new HomePageContainerPage(this.navbarComponent.getDriver());
    }

    //tested
    public EventDetailsContainerPage deleteParticipant(Participants participant){
        if(this.addEditEventComponent.isEdit() && this.addEditEventComponent.isParticipantPresent(participant.value()) && this.addEditEventComponent.isButtonEnabled(participant.value())){
            this.addEditEventComponent.deleteParticipant(participant.value());
            this.addEditEventComponent.save();
            return new EventDetailsContainerPage(this.addEditEventComponent.getDriver());
        }else{
            throw new IllegalArgumentException("deleteParticipant: is not edit or participant " + participant.value() + " not present or button disabled");
        }
    }

    //tested
    public EventDetailsContainerPage addParticipant(Participants participant){
        if(this.addEditEventComponent.isEdit() && !this.addEditEventComponent.isParticipantPresent(participant.value())){
            this.addEditEventComponent.addParticipant();
            int index = this.addEditEventComponent.getIndexOfEmptyParticipant();
            this.addEditEventComponent.typeParticipant(participant.value(), index);
            this.addEditEventComponent.save();
            return new EventDetailsContainerPage(this.addEditEventComponent.getDriver());
        }else{
            throw new IllegalArgumentException("addParticipant: is not edit or participant " + participant.value() + " already exists");
        }
    }

    //tested
    public ConfirmationPage deleteEvent(){
        if(this.addEditEventComponent.isEdit()){
            this.addEditEventComponent.delete();
            String poCallee = this.getClass().getSimpleName();
            return new ConfirmationPage(this.addEditEventComponent.getDriver(), poCallee, this.poCallee, this.tripName);
        }else{
            throw new IllegalArgumentException("deleteEvent: is not edit");
        }
    }



    public boolean isPageLoaded() {
        if(this.addEditEventComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.addEditEventComponent.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.addEditEventComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_EVENT.value())){
            return true;
        }
        return false;
    }
}
