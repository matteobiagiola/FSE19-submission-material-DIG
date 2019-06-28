package po.event.pages;

import custom_classes.Id;
import custom_classes.Participants;
import custom_classes.TripNames;
import org.openqa.selenium.WebDriver;
import po.event.components.EventDetailsComponent;
import po.event.components.EventDetailsNavbarComponent;
import po.event.pages.modals.QuickAddTransactionPage;
import po.event.pages.modals.ShareEventPage;
import po.home.pages.HomePageContainerPage;
import po.shared.components.NavbarComponent;
import po.shared.pages.ConfirmationPage;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class EventDetailsContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public EventDetailsNavbarComponent eventDetailsNavbarComponent;
    public EventDetailsComponent eventDetailsComponent;

    public EventDetailsContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.eventDetailsNavbarComponent = new EventDetailsNavbarComponent(driver);
        this.eventDetailsComponent = new EventDetailsComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("EventDetailsContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditEventContainerPage editEvent(TripNames newTripName){
        if(!this.eventDetailsNavbarComponent.isTripPresent(newTripName.value())){
            this.eventDetailsNavbarComponent.clickOnEdit();
            String poCallee = this.getClass().getSimpleName();
            return new AddEditEventContainerPage(this.eventDetailsComponent.getDriver(), poCallee, newTripName.value());
        }else{
            throw new IllegalArgumentException("editEvent: new trip name " + newTripName.value() + " already exists");
        }
    }

    //tested
    public HomePageContainerPage goToHome(){
        this.navbarComponent.goToHomePage();
        return new HomePageContainerPage(this.navbarComponent.getDriver());
    }

    //tested
    public ShareEventPage shareEvent(){
        this.eventDetailsNavbarComponent.clickOnShare();
        return new ShareEventPage(this.eventDetailsComponent.getDriver());
    }

    //tested
    public EventDetailsContainerPage viewDetailsAs(Participants participant){
        if(this.navbarComponent.isParticipantPresent(participant.value())){
            this.navbarComponent.viewingAs(participant.value());
            return this;
        }else{
            throw new IllegalArgumentException("viewDetailsAs: participant " + participant.value() + " is not present or it is the current participant");
        }
    }

    //tested
    public EventDetailsContainerPage goToOverview(){
        this.eventDetailsNavbarComponent.clickOnOverview();
        return this;
    }

    //tested
    public EventDetailsContainerPage goToTransaction(){
        this.eventDetailsNavbarComponent.clickOnTransactions();
        return this;
    }

    //tested
    public QuickAddTransactionPage addTransaction(){
        this.eventDetailsComponent.addTransaction();
        return new QuickAddTransactionPage(this.eventDetailsComponent.getDriver());
    }

    //tested
    public AddEditEventContainerPage addNewEvent(TripNames tripName){
        if(!this.eventDetailsNavbarComponent.isTripPresent(tripName.value())){
            this.eventDetailsNavbarComponent.addNewEvent();
            String poCallee = this.getClass().getSimpleName();
            return new AddEditEventContainerPage(this.eventDetailsComponent.getDriver(), tripName.value(), poCallee);
        }else{
            throw new IllegalArgumentException("addNewEvent: trip " + tripName.value() + " already exists");
        }
    }

    //tested
    public ConfirmationPage settleUp(Id id){
        if(this.eventDetailsNavbarComponent.isOverviewTabActive() && this.eventDetailsComponent.isSettlementPresent(id.value)){
            this.eventDetailsComponent.settleUp(id.value);
            String poCallee = this.getClass().getSimpleName();
            return new ConfirmationPage(this.eventDetailsComponent.getDriver(), poCallee);
        }else{
            throw new IllegalArgumentException("settleUp: settlement with id " + id.value + " does not exist");
        }
    }

    //tested
    public AddEditTransactionContainerPage editTransaction(Id id){
        if(this.eventDetailsNavbarComponent.isTransactionsViewActive() && this.eventDetailsComponent.isTransactionPresent(id.value) && this.eventDetailsComponent.isTransaction(id.value)){
            this.eventDetailsComponent.clickOnTransaction(id.value);
            return new AddEditTransactionContainerPage(this.eventDetailsComponent.getDriver());
        }else{
            throw new IllegalArgumentException("editTransaction: transaction with id " + id.value + " is not present or it is not a transaction or the transaction view is not active");
        }
    }

    //tested
    public ViewTransferContainerPage viewTransfer(Id id){
        if(this.eventDetailsNavbarComponent.isTransactionsViewActive() && this.eventDetailsComponent.isTransactionPresent(id.value) && this.eventDetailsComponent.isSettlementTransaction(id.value)){
            this.eventDetailsComponent.clickOnTransaction(id.value);
            return new ViewTransferContainerPage(this.eventDetailsComponent.getDriver());
        }else{
            throw new IllegalArgumentException("viewTransfer: transaction with id " + id.value + " is not present or it is not a settlement transaction or the transaction view is not active");
        }
    }

    public boolean isPageLoaded() {
        if(this.eventDetailsComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.eventDetailsComponent.getDriver().get("http://localhost:" + MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.eventDetailsComponent.waitForElementBeingPresentOnPage(ConstantLocators.EVENT_DETAILS.value())){
            return true;
        }
        return false;
    }
}
