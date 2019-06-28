package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to WalletsManagerPage

public class WalletPage implements PageObject{

    public WalletComponent walletComponent;

    /**
     * Page Object for WalletComponent (state5)
     */
    public WalletPage(WebDriver driver){
        this.walletComponent = new WalletComponent(driver);
        if(!isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
        }
    }

    public AddWalletPage goToAddWalletPage() {
        if(this.walletComponent.getActiveFilter().equals("Active") && !this.walletComponent.getActiveAccessFilter().equals("Shared with you")
                || (this.walletComponent.getActiveAccessFilter().equals("Shared with you") && this.walletComponent.existWalletShared())){
            this.walletComponent.goToAddWalletPage();
            return new AddWalletPage(this.walletComponent.getDriver());
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": cannot add wallet");
        }
    }

    public CreatePlanPage goToGoalsPage() {
        this.walletComponent.goToGoalsPage();
        return new CreatePlanPage(this.walletComponent.getDriver());
    }

    public GiveAccessToWalletPage manageWalletAccess(Id id){
        if(this.walletComponent.isWalletPresent(id)
                && !this.walletComponent.isWalletShared(id)
                && this.walletComponent.getActiveFilter().equals("Active")
                && !this.walletComponent.getActiveAccessFilter().equals("Shared with you")){
            this.walletComponent.manageWalletAccess(id);
            return new GiveAccessToWalletPage(this.walletComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public AddWalletPage editWallet(Id id){
        if(this.walletComponent.isWalletPresent(id)
                && !this.walletComponent.isWalletShared(id)
                && this.walletComponent.getActiveFilter().equals("Active")
                && !this.walletComponent.getActiveAccessFilter().equals("Shared with you")){
            this.walletComponent.editWallet(id);
            return new AddWalletPage(this.walletComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public HideWalletPage hideWallet(Id id){
        if(this.walletComponent.isWalletPresent(id)
                && !this.walletComponent.isWalletShared(id)
                && this.walletComponent.getActiveFilter().equals("Active")
                && !this.walletComponent.getActiveAccessFilter().equals("Shared with you")){
            this.walletComponent.hideWallet(id);
            return new HideWalletPage(this.walletComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id "
                    + id.value + " not present or filters in wrong state");
        }
    }

    public TrashPage goToTrashPage() {
        this.walletComponent.goToTrashPage();
        return new TrashPage(this.walletComponent.getDriver());
    }

    public TransactionsPage goToTransactions(Id id){
        if(this.walletComponent.isWalletPresent(id)){
            this.walletComponent.goToTransaction(id);
            return new TransactionsPage(this.walletComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": wallet with id " + id.value + " not present");
        }
    }

    @Override
    public boolean isPageLoaded(){
        if(this.walletComponent.waitForElementBeingPresentOnPage(ConstantLocators.WALLETS_MANAGER.value())){
            return true;
        }
        return false;
    }

}
