package po.wallets.pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageObject;
import po.shared.pages.modals.ConfirmationPage;
import po.wallets.pages.TransactionManagerPage;
import po_utils.ConstantLocators;

public class TransactionDetailsPage extends BasePageObject implements PageObject {

    public TransactionDetailsPage(WebDriver driver){
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public ConfirmationPage removeTransaction(){
        this.clickOn(By.id("remove_transaction_button"));
        return new ConfirmationPage(this.getDriver(), this.getClass().getSimpleName());
    }

    public TransactionManagerPage close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
        return new TransactionManagerPage(this.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.TRANSACTION_DETAILS.value())){
            return true;
        }
        return false;
    }
}
