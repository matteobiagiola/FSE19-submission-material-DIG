package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.NotTheRightInputValuesException;
import po_utils.PageObject;

public class OwnedBoardsPage implements PageObject {

	public OwnedBoardsComponent ownedBoardsComponent;
	public BoardNames boardName;

	/**
	 * Page Object for OwnedBoards (state4) --> BoardsContainerPage
	 */
	public OwnedBoardsPage(WebDriver driver, BoardNames boardName) {
		this.ownedBoardsComponent = new OwnedBoardsComponent(driver);
		this.boardName = boardName;
		if(!this.isPageLoaded()){
			throw new IllegalStateException("OwnedBoardsPage not loaded properly");
		}
	}

	public OwnedBoardsPage(WebDriver driver) {
		this.ownedBoardsComponent = new OwnedBoardsComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("OwnedBoardsPage not loaded properly");
		}
	}

	public BoardPage goToBoardPage() {
		if(this.boardName != null){
			this.ownedBoardsComponent.goToBoardPage(this.boardName);
			return new BoardPage(this.ownedBoardsComponent.getDriver());
		}else{
			throw new NotTheRightInputValuesException(
					"goToBoardPage: board name is null. PO was not instantiated with boardName");
		}
	}

	public HomePage goToHomePage() {
		this.ownedBoardsComponent.goToHomePage();
		return new HomePage(this.ownedBoardsComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.ownedBoardsComponent.waitForElementBeingPresentOnPage(ConstantLocators.BOARDS_LIST_MODAL_OPEN.value())){
			return true;
		}
		return false;
	}
}
