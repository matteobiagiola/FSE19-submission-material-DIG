package po_apogen;

import custom_classes.Id;
import custom_classes.ListNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class BoardPageComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for BoardPage (state5) --> BoardListContainerPage
	 */
	public BoardPageComponent(WebDriver driver) {
		super(driver);
	}

	public void goToCardDetailsPage(ListNames listName, Id cardId) {
		this.clickOnCardOnList(listName.value(), cardId.value);
	}

	public void add_new_list(ListNames listName) {
		this.openListForm();
		this.createList(listName.value());
	}

	public void goToInsertCardPage(ListNames listName) {
		this.openNewCardForm(listName.value());
	}

	/*------- added */

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

	public boolean isListPresent(String listName){
		List<WebElement> lists = this.getLists();
		for(WebElement list: lists){
			WebElement headerFour = this.findElementJSByXPathStartingFrom(list, "./div/header/h4");
			String currentListName = this.getText(headerFour);
			if(currentListName.equals(listName)){
				return true;
			}
		}
		return false;
	}

	public boolean isCardPresentOnList(String listName, int cardId){
		WebElement list = this.getListByName(listName);
		List<WebElement> cards = this.findElementsJSByXPathStartingFrom(list, ".//div[@class=\"cards-wrapper\"]/div[@id]");
		if(cards.size() > 0 && cardId <= cards.size()){
			return true;
		}
		return false;
	}

	public void openListForm(){
		this.clickOn(By.xpath("//div[@class=\"list add-new\"]"));
	}

	public void createList(String listName){
		this.type(By.xpath("//form[@id=\"new_list_form\"]/input[@id=\"list_name\"]"), listName);
		this.clickOn(By.xpath("//form[@id=\"new_list_form\"]/button[text()=\"Save list\"]"));
		long timeoutMillis = 200;
		this.waitForTimeoutExpires(timeoutMillis); // wait that the new list is added to the DOM: no other way to do it since I don't know in which position (index) the element will be added
	}

	//cardId starts from 1 while list counter starts from 0
	public void clickOnCardOnList(String listName, int cardId){
		WebElement list = this.getListByName(listName);
		List<WebElement> cards = this.findElementsJSByXPathStartingFrom(list, ".//div[@class=\"cards-wrapper\"]/div[@id]");
		WebElement card = cards.get(cardId - 1);
		this.clickOn(card);
	}

	public void openNewCardForm(String listName){
		WebElement list = this.getListByName(listName);
		WebElement cardElement = this.findElementJSByXPathStartingFrom(list, "./div/footer/a[@class=\"add-new\"]");
		this.clickOn(cardElement);
	}

	public void addNewCardToList(String listName, String cardText){
		WebElement list = this.getListByName(listName);
		WebElement textArea = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form/textarea[@id=\"card_name\"]");
		WebElement addButton = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form/button[text()=\"Add\"]");
		this.type(textArea, cardText);
		this.clickOn(addButton);
		long timeoutMillis = 200;
		this.waitForTimeoutExpires(timeoutMillis);
	}

	public boolean isBoardPresent(String boardName){
		this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]/ul/li/a[@href=\"#\"]"));
		long timeout = 500;
		if(this.waitForElementBeingPresentOnPage(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]"), timeout)){
			List<WebElement> linksToBoards = this.findElements(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]//a[@href=\"#\"]"));
			for(WebElement linkToBoard: linksToBoards){
				String currentBoardName = this.getText(linkToBoard);
				if(currentBoardName.equals(boardName)){
					return true;
				}
			}
			this.clickOnName(); // if boardName is not present the dropdown menu remains open; to close it I need to click anywhere else -> it is reasonable to click on the current user name since the action has no effect
			return false;
		}else{
			throw new IllegalStateException("goToBoard: dropdown appearance not handled properly");
		}
	}

	public void clickOnName(){
		this.clickOnSelenium(By.xpath("//nav[@class=\"right\"]//a[@class=\"current-user\"]")); // I have to use selenium click since the js click has no effect for an element that is not clickable
	}

}
