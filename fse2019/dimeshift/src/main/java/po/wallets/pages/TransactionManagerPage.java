package po.wallets.pages;

import custom_classes.Amount;
import custom_classes.NavbarActions;
import custom_classes.TransactionDescription;
import custom_classes.BreadcrumbActions;
import custom_classes.Id;
import org.openqa.selenium.WebDriver;
import po_utils.PageObject;
import po.goals.pages.GoalsManagerPage;
import po.shared.components.BreadcrumbComponent;
import po.shared.components.NavbarComponent;
import po.wallets.components.*;
import po.wallets.pages.modals.AddIncomeToWalletPage;
import po.wallets.pages.modals.SetTotalIncomeToWalletPage;
import po.wallets.pages.modals.TransactionDetailsPage;
import po_utils.ConstantLocators;

public class TransactionManagerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public BreadcrumbComponent breadcrumbComponent;
    public AddTransactionComponent addTransactionComponent;
    public TransactionsListComponent transactionsListComponent;
    public WalletIncomeManagerComponent walletIncomeManagerComponent;

    //private WalletGoalsManagerComponent walletGoalsManagerComponent; --> functionality in the website doesn't work properly

    public TransactionManagerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.breadcrumbComponent = new BreadcrumbComponent(driver);
        this.addTransactionComponent = new AddTransactionComponent(driver);
        this.transactionsListComponent = new TransactionsListComponent(driver);
        this.walletIncomeManagerComponent = new WalletIncomeManagerComponent(driver);;
        //this.walletGoalsManagerComponent = new WalletGoalsManagerComponent(driver);
        if(!isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
        }
    }

    /*AddTransactionComponent methods --------------------------------------------------------------------------------------------------*/

    public TransactionManagerPage addTransaction(TransactionDescription description, Amount amount){
        this.addTransactionComponent.addTransaction(description, amount);
        return new TransactionManagerPage(this.addTransactionComponent.getDriver());
    }

    /*TransactionsListComponent methods --------------------------------------------------------------------------------------------------*/

    public TransactionDetailsPage selectTransaction(Id id){
        if(this.transactionsListComponent.areTransactionsPresent()
                && this.transactionsListComponent.isTransactionPresent(id)){
            this.transactionsListComponent.selectTransaction(id);
            return new TransactionDetailsPage(this.transactionsListComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": transaction with id " + id.value + " not present");
        }
    }

    //this view is always available: it always possible to click on previous month button
    public TransactionManagerPage goToPreviousMonthTransactionView(){
        this.transactionsListComponent.goToTransactionsPreviousMonth();
        return this;
    }

    public TransactionManagerPage goToNextMonthTransactionView(){
        if(this.transactionsListComponent.isNextMonthViewAvailable()){
            this.transactionsListComponent.goToTransactionsNextMonth();
            return this;
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": next month view not available");
        }
    }

    public TransactionManagerPage goToCurrentMonthTransactionView(){
        if(this.transactionsListComponent.isCurrentMonthViewAvailable()){
            this.transactionsListComponent.goToTransactionsCurrentMonth();
            return this;
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": current month view not available");
        }
    }

    /*WalletIncomeManagerComponent methods --------------------------------------------------------------------------------------------------*/

    public AddIncomeToWalletPage addIncome(){
        this.walletIncomeManagerComponent.addIncome();
        return new AddIncomeToWalletPage(this.walletIncomeManagerComponent.getDriver());
    }

    public SetTotalIncomeToWalletPage setTotalIncome(){
        this.walletIncomeManagerComponent.setTotalIncome();
        return new SetTotalIncomeToWalletPage(this.walletIncomeManagerComponent.getDriver());
    }

    /*WalletGoalsManagerComponent methods --------------------------------------------------------------------------------------------------*/

    /* Navbar */
    public WalletsManagerPage goToWalletsManagerPageNavbar(){
        return (WalletsManagerPage) this.navbarComponent.goTo(NavbarActions.WALLETS);
    }

    public GoalsManagerPage goToGoalsManagerPage(){
        return (GoalsManagerPage) this.navbarComponent.goTo(NavbarActions.GOALS);
    }

    /* Breadcrumb */
    public WalletsManagerPage goToHomeUserLoggedIn(){
        return (WalletsManagerPage) this.breadcrumbComponent.goTo(BreadcrumbActions.HOME);
    }

    public WalletsManagerPage goToWalletsManagerPageBreadcrumb(){
        return (WalletsManagerPage) this.breadcrumbComponent.goTo(BreadcrumbActions.WALLETS);
    }

    @Override
    public boolean isPageLoaded(){
        if(this.transactionsListComponent.waitForElementBeingPresentOnPage(ConstantLocators.TRANSACTIONS_MANAGER.value())){
            return true;
        }
        return false;
    }
}
