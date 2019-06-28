package po.wallets.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class WalletIncomeManagerComponent extends BasePageObject implements PageComponent {

    public WalletIncomeManagerComponent(WebDriver driver){
        super(driver);
    }

    public void addIncome(){
        this.clickOn(By.id("add_profit_button"));
    }

    public void setTotalIncome(){
        this.clickOn(By.id("set_total_to_button"));
    }
}
