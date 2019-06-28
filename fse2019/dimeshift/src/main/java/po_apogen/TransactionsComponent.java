package po_apogen;

import custom_classes.Amount;
import custom_classes.TransactionDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class TransactionsComponent extends BasePageObject implements PageComponent {

	public TransactionsComponent(WebDriver driver) {
		super(driver);
	}

	public void goToAddIncomePage() {
		this.clickOn(By.id("add_profit_button"));
	}

	public void goToSetTotalPage() {
		this.clickOn(By.id("set_total_to_button"));
	}

	public void goToWalletPage() {
		this.clickOn(By.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[2]/a[1]"));
	}

	public void addTransaction(TransactionDescription description, Amount amount) {
		this.typeJS(By.id("add_transaction_text"), description.value());
		this.pressKeyboardEnter(By.id("add_transaction_text"));
		this.typeJS(By.id("add_transaction_amount"), String.valueOf(amount.value));
		this.pressKeyboardEnter(By.id("add_transaction_amount"));
	}

}
