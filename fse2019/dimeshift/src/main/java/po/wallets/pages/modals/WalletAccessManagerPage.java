package po.wallets.pages.modals;

import custom_classes.Email;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageObject;
import po.shared.pages.modals.ConfirmationPage;
import po.wallets.pages.WalletsManagerPage;
import po_utils.ConstantLocators;

import java.util.List;

public class WalletAccessManagerPage extends BasePageObject implements PageObject {

    public WalletAccessManagerPage(WebDriver driver){
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public WalletsManagerPage addAccess(Email email){
        this.typeJS(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_email\"]"), email.value());
        this.clickOn(By.xpath("//div[@class=\"modal-footer\"]//input[@type=\"submit\"]"));
        return new WalletsManagerPage(this.getDriver());
    }

    public ConfirmationPage removeAccess(Email email){
        int indexElement = this.associatedEmailExist(email);
        if(indexElement != -1){
            this.clickOn(By.xpath("(//div[@class=\"modal-body modal-body-default\"]/div[@class=\"table-responsive\"]//a)[" + (indexElement + 1) + "]"));
            return new ConfirmationPage(this.getDriver(),this.getClass().getSimpleName());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": email " + email.value() + " has not been associated with any wallet");
        }
    }

    public WalletsManagerPage close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
        return new WalletsManagerPage(this.getDriver());
    }

    public int associatedEmailExist(Email email){
        if(this.isElementPresentOnPage(By.xpath("//div[@class=\"modal-body modal-body-default\"]/div[@class=\"table-responsive\"]//strong[@id]"))){
            List<WebElement> elements = this.findElements(By.xpath("//div[@class=\"modal-body modal-body-default\"]/div[@class=\"table-responsive\"]//strong[@id]"));
            for (int i = 0; i < elements.size(); i++) {
                if(elements.get(i).getText().equals(email.value())){
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.WALLET_ACCESS_MANAGER.value())){
            return true;
        }
        return false;
    }
}
