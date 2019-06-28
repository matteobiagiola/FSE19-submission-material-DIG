package po.wallets.pages.modals;

import custom_classes.WalletNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageObject;
import po.wallets.pages.WalletsManagerPage;
import po_utils.ConstantLocators;

public class AddWalletPage extends BasePageObject implements PageObject {

    public AddWalletPage(WebDriver driver){
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public WalletsManagerPage add(WalletNames walletName){
        this.typeJS(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_name\"]"),walletName.value());
        this.clickOn(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
        return new WalletsManagerPage(this.getDriver());
    }

    public WalletsManagerPage close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
        return new WalletsManagerPage(this.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.ADD_WALLET.value())){
            return true;
        }
        return false;
    }
}
