package po_apogen;

import custom_classes.CardText;
import custom_classes.ListNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class InsertCardPage implements PageObject {

	public InsertCardPageComponent insertCardPageComponent;
	public ListNames listName;

	/**
	 * Page Object for CardPage (state7) --> BoardListContainerPage
	 */
	public InsertCardPage(WebDriver driver) {
		this.insertCardPageComponent = new InsertCardPageComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("InsertCardPage not loaded properly");
		}
	}

	public InsertCardPage(WebDriver driver, ListNames listName) {
		this.insertCardPageComponent = new InsertCardPageComponent(driver);
		this.listName = listName;
		if(!this.isPageLoaded()){
			throw new IllegalStateException("InsertCardPage not loaded properly");
		}
	}

	public BoardPage goToBoardPage() {
		this.insertCardPageComponent.goToBoardPage(this.listName);
		return new BoardPage(this.insertCardPageComponent.getDriver());
	}

	public BoardPage new_card_form(CardText cardText) {
		this.insertCardPageComponent.new_card_form(this.listName, cardText);
		return new BoardPage(this.insertCardPageComponent.getDriver());
	}

//	 duplicated
//	public BoardPage goToBoardPage1() {
//		a_Cancel.click();
//		return new BoardPage(driver);
//	}

	@Override
	public boolean isPageLoaded() {
		if(this.insertCardPageComponent.waitForElementBeingPresentOnPage(ConstantLocators.LIST.value())){
			return true;
		}
		return false;
	}
}
