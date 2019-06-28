package po_apogen;

import custom_classes.BoardNames;
import custom_classes.Id;
import custom_classes.ListNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class BoardPage implements PageObject {

	public BoardPageComponent boardPageComponent;

	/**
	 * Page Object for BoardPage (state5) --> BoardListContainerPage
	 */
	public BoardPage(WebDriver driver) {
		this.boardPageComponent = new BoardPageComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("BoardPage not loaded properly");
		}
	}

	public CardDetailsPage goToCardDetailsPage(ListNames listName, Id cardId) {
		if(this.boardPageComponent.isListPresent(listName.value()) && this.boardPageComponent.isCardPresentOnList(listName.value(), cardId.value)){
			this.boardPageComponent.goToCardDetailsPage(listName, cardId);
			return new CardDetailsPage(this.boardPageComponent.getDriver());
		}else{
			throw new IllegalArgumentException("goToCardDetailsPage: list name " + listName.value() + " is not present or card with id " + cardId.value + " is not present on list " + listName.value());
		}
	}

	public BoardPage add_new_list(ListNames listName) {
		this.boardPageComponent.add_new_list(listName);
		return new BoardPage(this.boardPageComponent.getDriver());
	}

	public InsertCardPage goToInsertCardPage(ListNames listName){
		if(this.boardPageComponent.isListPresent(listName.value())){
			this.boardPageComponent.goToInsertCardPage(listName);
			return new InsertCardPage(this.boardPageComponent.getDriver(), listName);
		}else{
			throw new IllegalArgumentException("addNewCardToList: list " + listName.value() + " does not exist");
		}
	}

	// added otherwise no path from InsertCardPage to OwnedBoardsPage
	public OwnedBoardsPage goToBoardUsingNavbar(BoardNames boardName){
		if(this.boardPageComponent.isBoardPresent(boardName.value())){
			return new OwnedBoardsPage(this.boardPageComponent.getDriver(), boardName);
		}else{
			throw new IllegalArgumentException("goToBoardUsingNavbar: board " + boardName.value() + " does not exists");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.boardPageComponent.waitForElementBeingPresentOnPage(ConstantLocators.LIST.value())){
			return true;
		}
		return false;
	}
}
