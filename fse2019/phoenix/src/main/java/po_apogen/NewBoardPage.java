package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class NewBoardPage implements PageObject {

	public NewBoardPageComponent newBoardPageComponent;

	/**
	 * Page Object for State21 (state21) --> BoardsContainerPage
	 */
	public NewBoardPage(WebDriver driver) {
		this.newBoardPageComponent = new NewBoardPageComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("NewBoardPage not loaded properly");
		}
	}

	// change return type: it was HomPage but it should be BoardPage
	public BoardPage new_board_form(BoardNames boardName) {
		this.newBoardPageComponent.new_board_form(boardName);
		return new BoardPage(this.newBoardPageComponent.getDriver());
	}

	public HomePage goToHomePage() {
		this.newBoardPageComponent.goToHomePage();
		return new HomePage(this.newBoardPageComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.newBoardPageComponent.waitForElementBeingPresentOnPage(ConstantLocators.BOARD_OPEN.value())){
			return true;
		}
		return false;
	}
}
