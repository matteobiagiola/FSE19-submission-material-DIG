package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class HomePage implements PageObject {

	public HomePageComponent homePageComponent;

	/**
	 * Page Object for HomePage (state3) --> BoardsContainerPage
	 */
	public HomePage(WebDriver driver) {
		this.homePageComponent = new HomePageComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("HomePage not loaded properly");
		}
	}

	public NewBoardPage goToNewBoardPage() {
		this.homePageComponent.goToNewBoardPage();
		return new NewBoardPage(this.homePageComponent.getDriver());
	}

	/* sign out. */
	public IndexPage goToIndex() {
		this.homePageComponent.goToIndex();
		return new IndexPage(this.homePageComponent.getDriver());
	}

	// changed: it was goToState4
	public OwnedBoardsPage seeAvailableBoards() {
		if(this.homePageComponent.existsBoard()){
			this.homePageComponent.seeAvailableBoards();
			return new OwnedBoardsPage(this.homePageComponent.getDriver());
		}else{
			throw new IllegalArgumentException("viewAllBoards: does not exist any board");
		}
	}

	// added: otherwise tricky to solve a non closed modal if precondition on a modal page is not satisfied
	public OwnedBoardsPage goToBoardUsingNavbar(BoardNames boardName){
		if(this.homePageComponent.isBoardPresent(boardName.value())){
			this.homePageComponent.seeAvailableBoards();
			return new OwnedBoardsPage(this.homePageComponent.getDriver(), boardName);
		}else{
			throw new IllegalArgumentException("goToBoardUsingNavbar: board " + boardName.value() + " does not exists");
		}
	}


	@Override
	public boolean isPageLoaded() {
		if(this.homePageComponent.waitForElementBeingPresentOnPage(ConstantLocators.BOARDS.value())){
			return true;
		}
		return false;
	}
}
