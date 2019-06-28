package po.wallets.pages.modals;

import custom_classes.Amount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageObject;
import po.wallets.pages.TransactionManagerPage;
import po_utils.ConstantLocators;

public class SetTotalIncomeToWalletPage extends BasePageObject implements PageObject {

    public SetTotalIncomeToWalletPage(WebDriver driver){
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public TransactionManagerPage setTotalIncome(Amount amount){
        if(this.isElementPresentOnPage((By.id("input_total")))){
            this.typeJS(By.id("input_total"), String.valueOf(amount.value));
            this.clickOn(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
            return new TransactionManagerPage(this.getDriver());
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": cannot set total income");
        }
    }

    public TransactionManagerPage close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
        return new TransactionManagerPage(this.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.SET_TOTAL_INCOME.value())){
            return true;
        }
        return false;
    }
}
