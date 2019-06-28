package po.shared.pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageObject;
import po.goals.pages.GoalsManagerPage;
import po.wallets.pages.TransactionManagerPage;
import po.wallets.pages.WalletsManagerPage;
import po.wallets.pages.modals.TransactionDetailsPage;
import po.wallets.pages.modals.WalletAccessManagerPage;
import po_utils.ConstantLocators;

public class ConfirmationPage extends BasePageObject implements PageObject {

    public String poNameCaller;

    public ConfirmationPage(WebDriver driver, String poNameCaller){
        super(driver);
        this.poNameCaller = poNameCaller;
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public PageObject acceptOperation(){
        this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]"));
        if(this.poNameCaller.equals("WalletsManagerPage")){
            return new WalletsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("WalletAccessManagerPage")){
            return new WalletAccessManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("GoalsManagerPage")){
            return new GoalsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("TransactionDetailsPage")){
            return new TransactionManagerPage(this.getDriver());
        }
        else{
            throw new IllegalStateException(this.getClass().getName() + ": poNameCaller " + poNameCaller + " not valid");
        }
    }

    public PageObject denyOperation(){
        this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"btn btn-primary pull-left\"]"));
        if(this.poNameCaller.equals("WalletsManagerPage")){
            return new WalletsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("WalletAccessManagerPage")){
            return new WalletAccessManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("GoalsManagerPage")){
            return new GoalsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("TransactionDetailsPage")){
            return new TransactionDetailsPage(this.getDriver());
        }
        else{
            throw new IllegalStateException(this.getClass().getName() + ": poNameCaller " + poNameCaller + " not valid");
        }
    }

    public PageObject close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
        if(this.poNameCaller.equals("WalletsManagerPage")){
            return new WalletsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("WalletAccessManagerPage")){
            return new WalletsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("GoalsManagerPage")){
            return new GoalsManagerPage(this.getDriver());
        }else if(this.poNameCaller.equals("TransactionDetailsPage")){
            return new TransactionManagerPage(this.getDriver());
        }
        else{
            throw new IllegalStateException(this.getClass().getName() + ": poNameCaller " + poNameCaller + " not valid");
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.CONFIRMATION.value())){
            return true;
        }
        return false;
    }
}
