package po.wallets.components;

import custom_classes.Amount;
import custom_classes.TransactionDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AddTransactionComponent extends BasePageObject implements PageComponent {

    public AddTransactionComponent(WebDriver driver) {
        super(driver);
    }

    public void addTransaction(TransactionDescription description, Amount amount){
        this.typeJS(By.id("add_transaction_text"),description.value());
        this.pressKeyboardEnter(By.id("add_transaction_text"));
        this.typeJS(By.id("add_transaction_amount"),String.valueOf(amount.value));
        this.pressKeyboardEnter(By.id("add_transaction_amount"));
    }

}
