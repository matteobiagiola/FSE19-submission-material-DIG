package po_apogen;

import custom_classes.Dates;
import custom_classes.Price;
import custom_classes.Transactions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class AddTransactionPage extends BasePageObject implements PageObject {

	/**
	 * Page Object for AddTransactionPage (state271) --> QuickAddTransactionPage
	 */
	public AddTransactionPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AddTransactionPage not loaded properly");
		}
	}

	public TransactionsPage close() {
		this.clickOn(By.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new TransactionsPage(this.getDriver());
	}

	// modified: it was void quickAdd(string)
	public HomePage quickAdd(Transactions transaction, Price price, Dates date) {
		String input = date.value() + " " + price.value + " " + transaction.value();
		if(this.waitForElementBeingVisibleOnPage(By.xpath("//div[@class=\"modal-body pull-up-30\"]//input[@placeholder=\"Example: 10 tickets\"]"))){
			//this.clickOn(By.xpath("//div[@class=\"modal-body pull-up-30\"]//input[@placeholder=\"Example: 10 tickets\"]"));
			this.type(By.xpath("//div[@class=\"modal-body pull-up-30\"]//input[@placeholder=\"Example: 10 tickets\"]"), input);
			this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Add\"]"));
			this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
			return new HomePage(this.getDriver());
		}else{
			throw new IllegalStateException("add: failed to locate the input box");
		}
	}

	// modified: it was AddWithDetails
	public NewTransactionPage addWithDetails() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Add With Details\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new NewTransactionPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.QUICK_ADD.value())){
			return true;
		}
		return false;
	}
}
