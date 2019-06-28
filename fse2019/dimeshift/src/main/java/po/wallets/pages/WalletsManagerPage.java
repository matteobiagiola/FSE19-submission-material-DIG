package po.wallets.pages;

import custom_classes.BreadcrumbActions;
import custom_classes.Id;
import custom_classes.NavbarActions;
import org.openqa.selenium.WebDriver;
import po_utils.PageObject;
import po.goals.pages.GoalsManagerPage;
import po.shared.components.BreadcrumbComponent;
import po.shared.components.NavbarComponent;
import po.shared.pages.modals.ConfirmationPage;
import po.wallets.components.AccessComponent;
import po.wallets.components.FilterComponent;
import po.wallets.components.WalletsListComponent;
import po.wallets.pages.modals.AddWalletPage;
import po.wallets.pages.modals.WalletAccessManagerPage;
import po_utils.ConstantLocators;

public class WalletsManagerPage implements PageObject{

    public WalletsListComponent walletsListComponent;
    public FilterComponent filterComponent;
    public AccessComponent accessComponent;
    public NavbarComponent navbarComponent;
    public BreadcrumbComponent breadcrumbComponent;

    public WalletsManagerPage(WebDriver driver){
        this.walletsListComponent = new WalletsListComponent(driver);
        this.filterComponent = new FilterComponent(driver);
        this.accessComponent = new AccessComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        this.breadcrumbComponent = new BreadcrumbComponent(driver);
        if(!isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
        }
    }

    /* WalletsListComponent methods ------------------------------------------------------------------------------------------------------*/

    public AddWalletPage addWallet(){
        if(this.filterComponent.getActiveFilter().equals("Active") && !this.accessComponent.getActiveAccessFilter().equals("Shared with you")
                || (this.accessComponent.getActiveAccessFilter().equals("Shared with you") && this.walletsListComponent.existWalletShared())){
            this.walletsListComponent.addWallet();
            return new AddWalletPage(this.walletsListComponent.getDriver());
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": cannot add wallet");
        }
    }

    public TransactionManagerPage selectWallet(Id id){
        if(this.walletsListComponent.isWalletPresent(id)){
            this.walletsListComponent.selectWallet(id);
            return new TransactionManagerPage(this.walletsListComponent.getDriver());
            /*if(this.walletsListComponent.waitForElementBeingPresentOnPage(ConstantLocators.TRANSACTIONS.value(),true)){
                return new TransactionManagerPage(this.walletsListComponent.getDriver());
            }else{
                throw new IllegalStateException(this.getClass().getName() + " timeout expired in waiting for transactions page");
            }*/
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id " + id.value + " not present");
        }
    }

    public AddWalletPage editWallet(Id id){
        if(this.walletsListComponent.isWalletPresent(id)
                && !this.walletsListComponent.isWalletShared(id)
                && this.filterComponent.getActiveFilter().equals("Active")
                && !this.accessComponent.getActiveAccessFilter().equals("Shared with you")){
            this.walletsListComponent.editWallet(id);
            return new AddWalletPage(this.walletsListComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public WalletAccessManagerPage manageWalletAccess(Id id){
        if(this.walletsListComponent.isWalletPresent(id)
                && !this.walletsListComponent.isWalletShared(id)
                && this.filterComponent.getActiveFilter().equals("Active")
                && !this.accessComponent.getActiveAccessFilter().equals("Shared with you")){
            this.walletsListComponent.manageWalletAccess(id);
            return new WalletAccessManagerPage(this.walletsListComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public ConfirmationPage hideWallet(Id id){
        if(this.walletsListComponent.isWalletPresent(id)
                && !this.walletsListComponent.isWalletShared(id)
                && this.filterComponent.getActiveFilter().equals("Active")
                && !this.accessComponent.getActiveAccessFilter().equals("Shared with you")){
            this.walletsListComponent.hideWallet(id);
            return new ConfirmationPage(this.walletsListComponent.getDriver(),this.getClass().getSimpleName());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public ConfirmationPage removeWallet(Id id){
        if(this.walletsListComponent.isWalletPresent(id)
                && this.filterComponent.getActiveFilter().equals("Trash")){
            this.walletsListComponent.removeWallet(id);
            return new ConfirmationPage(this.walletsListComponent.getDriver(),this.getClass().getSimpleName());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public WalletsManagerPage restoreWallet(Id id){
        if(this.walletsListComponent.isWalletPresent(id)
                && this.filterComponent.getActiveFilter().equals("Trash")){
            this.walletsListComponent.restoreWallet(id);
            return this;
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    /* FilterComponent methods ------------------------------------------------------------------------------------------------------*/

    public WalletsManagerPage selectActiveFilter(){
        this.filterComponent.clickOnFilter("Active");
        return this;
    }

    public WalletsManagerPage selectTrashFilter(){
        this.filterComponent.clickOnFilter("Trash");
        return this;
    }


    /* AccessComponent methods ------------------------------------------------------------------------------------------------------*/

    public WalletsManagerPage selectBothAccess(){
        this.accessComponent.clickOnAccess("Both");
        return this;
    }


    public WalletsManagerPage selectYoursAccess(){
        this.accessComponent.clickOnAccess("Yours");
        return this;
    }

    public WalletsManagerPage selectSharedWithYouAccess(){
        this.accessComponent.clickOnAccess("Shared with you");
        return this;
    }

    /* Navbar */
    public WalletsManagerPage goToWalletsManagerPage(){
        return (WalletsManagerPage) this.navbarComponent.goTo(NavbarActions.WALLETS);
    }

    public GoalsManagerPage goToGoalsManagerPage(){
        return (GoalsManagerPage) this.navbarComponent.goTo(NavbarActions.GOALS);
    }

    /* Breadcrumb */
    public WalletsManagerPage goToHomeUserLoggedIn(){
        return (WalletsManagerPage) this.breadcrumbComponent.goTo(BreadcrumbActions.HOME);
    }

    @Override
    public boolean isPageLoaded(){
        if(this.walletsListComponent.waitForElementBeingPresentOnPage(ConstantLocators.WALLETS_MANAGER.value())){
            return true;
        }
        return false;
    }

}
