package po.wallets.pages.modals;

import custom_classes.Amount;
import custom_classes.IncomeDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageObject;
import po.wallets.pages.TransactionManagerPage;
import po_utils.ConstantLocators;

public class AddIncomeToWalletPage extends BasePageObject implements PageObject {

    public AddIncomeToWalletPage(WebDriver driver){
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public TransactionManagerPage addIncome(IncomeDescription description, Amount amount){
        if(this.isElementPresentOnPage(By.id("input_amount"))){
            this.typeJS(By.id("input_amount"),String.valueOf(amount.value));
            this.typeJS(By.id("input_description"),description.value());
            this.clickOn(By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
            return new TransactionManagerPage(this.getDriver());
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": cannot add income");
        }
    }

    public TransactionManagerPage close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
        return new TransactionManagerPage(this.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.ADD_INCOME.value())){
            return true;
        }
        return false;
    }
}
