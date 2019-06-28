package po_apogen;

import custom_classes.CardText;
import custom_classes.ListNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class InsertCardPageComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for CardPage (state7) --> BoardListContainerPage
	 */
	public InsertCardPageComponent(WebDriver driver) {
		super(driver);
	}

	public void goToBoardPage(ListNames listName) {
		this.closeNewCardForm(listName.value());
	}

	public void new_card_form(ListNames listName, CardText cardText) {
		this.addNewCardToList(listName.value(), cardText.value());
	}

	// duplicated
//	public BoardPage goToBoardPage1() {
//		a_Cancel.click();
//		return new BoardPage(driver);
//	}

	/*------ added */

	public void closeNewCardForm(String listName){
		WebElement list = this.getListByName(listName);
		WebElement cancelLink = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form[@id=\"new_card_form\"]/a[text()=\"cancel\"]");
		this.clickOn(cancelLink);
	}

	public void addNewCardToList(String listName, String cardText){
		WebElement list = this.getListByName(listName);
		WebElement textArea = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form/textarea[@id=\"card_name\"]");
		WebElement addButton = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form/button[text()=\"Add\"]");
		this.type(textArea, cardText);
		this.clickOn(addButton);
		long timeoutMillis = 500;
		this.waitForTimeoutExpires(timeoutMillis);
	}

	public List<WebElement> getLists(){
		return this.findElements(By.xpath("//div[@class=\"lists-wrapper\"]/div[@id]"));
	}

	public WebElement getListByName(String listName){
		List<WebElement> lists = this.getLists();
		for(WebElement list: lists){
			WebElement headerFour = this.findElementJSByXPathStartingFrom(list, "./div/header/h4");
			String currentListName = this.getText(headerFour);
			if(currentListName.equals(listName)){
				return list;
			}
		}
		throw new IllegalStateException("getListByName: not possible to find list with name " + listName);
	}

}
