package po.event.pages;

import custom_classes.Dates;
import custom_classes.Participants;
import custom_classes.Price;
import custom_classes.Transactions;
import org.openqa.selenium.WebDriver;
import po.event.components.AddEditTransactionComponent;
import po.home.pages.HomePageContainerPage;
import po.shared.components.NavbarComponent;
import po.shared.pages.ConfirmationPage;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class AddEditTransactionContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public AddEditTransactionComponent addEditTransactionComponent;

    public AddEditTransactionContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.addEditTransactionComponent = new AddEditTransactionComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditTransactionContainerPage not loaded properly");
        }
    }

    //tested
    public EventDetailsContainerPage cancel(){
        this.addEditTransactionComponent.cancel();
        return new EventDetailsContainerPage(this.addEditTransactionComponent.getDriver());
    }

    //tested
    public EventDetailsContainerPage shareTransactionAmongAll(Transactions transaction, Participants payer, Price price, Dates date){
        if(this.addEditTransactionComponent.isParticipantPresent(payer.value())){
            this.addEditTransactionComponent.selectParticipant(payer.value());
            this.addEditTransactionComponent.typeTransactionName(transaction.value());
            this.addEditTransactionComponent.typeAmount(price.value);
            this.addEditTransactionComponent.pickDateFromCalendar(date);
            this.addEditTransactionComponent.createOrSave();
            return new EventDetailsContainerPage(this.addEditTransactionComponent.getDriver());
        }else{
            throw new IllegalArgumentException("shareTransactionAmongAll: payer " + payer.value() + " is not present");
        }
    }

    //tested
    public EventDetailsContainerPage shareTransactionExcludingParticipant(Transactions transaction, Participants payer, Price price, Dates date, Participants participantToExclude){
        if(this.addEditTransactionComponent.isParticipantPresent(payer.value()) && this.addEditTransactionComponent.isParticipantPresent(participantToExclude.value())){
            this.addEditTransactionComponent.selectParticipant(payer.value());
            this.addEditTransactionComponent.typeTransactionName(transaction.value());
            this.addEditTransactionComponent.typeAmount(price.value);
            this.addEditTransactionComponent.pickDateFromCalendar(date);
            this.addEditTransactionComponent.excludeFromSharing(participantToExclude.value());
            this.addEditTransactionComponent.createOrSave();
            return new EventDetailsContainerPage(this.addEditTransactionComponent.getDriver());
        }else{
            throw new IllegalArgumentException("shareTransactionExcludingParticipant: payer " + payer.value() + " is not present or participant to exclude " + participantToExclude.value() + " is not present");
        }
    }

    //tested
    public ConfirmationPage deleteTransaction(){
        if(this.addEditTransactionComponent.isEdit()){
            this.addEditTransactionComponent.deleteTransaction();
            String poCallee = this.getClass().getSimpleName();
            return new ConfirmationPage(this.addEditTransactionComponent.getDriver(), poCallee);
        }else{
            throw new IllegalArgumentException("deleteTransaction: is not edit");
        }
    }

    //tested
    public HomePageContainerPage goToHome(){
        this.navbarComponent.goToHomePage();
        return new HomePageContainerPage(this.navbarComponent.getDriver());
    }

    public boolean isPageLoaded() {
        if(this.addEditTransactionComponent.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.addEditTransactionComponent.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.addEditTransactionComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_TRANSACTION.value())){
            return true;
        }
        return false;
    }
}
