package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class TransactionsComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Transactions (state182) --> EventDetailsContainerPage
	 */
	public TransactionsComponent(WebDriver driver) {
		super(driver);
	}

	public void goToEditEvent() {
		this.clickOn(By.xpath("//div[@class=\"pull-right\"]/a[text()]"));
	}

	public void goToAddTransactionPage() {
		this.clickOn(By.xpath("//a[@title=\"Add New Transaction\"]"));
	}

	public void goToEditTransactionPage(Id id) {
		this.clickOnTransaction(id.value);
	}

	/*----------- added */

	public boolean isTripPresent(String tripName){
		String currentTripName = this.getCurrentTrip();
		if(currentTripName.equals(tripName)) return true;
		this.clickOn(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
		List<WebElement> otherTripElements = this.findElements(By.xpath("//div[@class=\"pull-left\"]/div[contains(@class, \"dropdown event-dropdown\")]/ul/li/a[not(@href=\"/new\")]"));
		for(WebElement otherTripElement: otherTripElements){
			String otherTripName = this.getText(otherTripElement);
			if(otherTripName != null && !otherTripName.isEmpty()){
				if(otherTripName.equals(tripName)) return true;
			}else{
				throw new IllegalStateException("isTripPresent: other trip name must not be null nor empty");
			}
		}
		return false;
	}

	public String getCurrentTrip(){
		WebElement currentTripElement = this.findElement(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
		String currentTripName = this.getText(currentTripElement);
		if(currentTripName != null && !currentTripName.isEmpty()){
			return currentTripName;
		}else{
			throw new IllegalStateException("getCurrentTrip: current trip name must not be null nor empty");
		}
	}

	public boolean isTransactionPresent(int id){
		List<WebElement> transactionList = this.getTransactionList();
		if(id <= transactionList.size()) {
			return true; //id starts from one
		}
		return false;
	}

	public boolean isTransaction(int id){
		List<WebElement> transactionList = this.getTransactionList();
		WebElement transaction = transactionList.get(id - 1); //id starts from 1
		WebElement transactionDescriptionElement = this.findElementJSByXPathStartingFrom(transaction, "./div[@class=\"transaction-list-item-description\"]");
		String description = this.getText(transactionDescriptionElement);
		//PageObjectLogging.logInfo("Description: " + description);
		if(description != null && !description.isEmpty()){
			if(description.contains("paid for")) return true;
			return false;
		}else{
			throw new IllegalStateException("isTransaction: description must not be null nor empty");
		}
	}

	public void clickOnTransaction(int id){
		List<WebElement> transactionList = this.getTransactionList();
		WebElement transaction = transactionList.get(id - 1); //id starts from one
		this.clickOn(transaction);
	}

	public List<WebElement> getTransactionList(){
		return this.findElements(By.xpath("//div[@class=\"list-group ember-view\"]/div[contains(@class, \"list-group-item\")]"));
	}

	public boolean isTransactionsViewActive(){
		return this.isElementPresentOnPage(By.xpath("//li[@title=\"Transactions\" and contains(@class, \"active\")]"));
	}
}
