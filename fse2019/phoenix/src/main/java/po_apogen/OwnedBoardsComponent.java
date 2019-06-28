package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class OwnedBoardsComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for OwnedBoards (state4) --> BoardsContainerPage
	 */
	public OwnedBoardsComponent(WebDriver driver) {
		super(driver);
	}

	public void goToBoardPage(BoardNames boardName) {
		this.goToBoard(boardName.value());
	}

	public void goToHomePage() {
		this.viewAllBoards();
	}

	/*---------- added */

	public void goToBoard(String boardName){
		if(this.isElementPresentOnPage(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]//a[@href=\"#\"]"))){
			List<WebElement> linksToBoards = this.findElements(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]//a[@href=\"#\"]"));
			for(WebElement linkToBoard: linksToBoards){
				String currentBoardName = this.getText(linkToBoard);
				if(currentBoardName.equals(boardName)){
					this.clickOn(linkToBoard);
					break;
				}
			}
		}else{
			throw new IllegalStateException("goToBoard: list of boards does not exist!");
		}
	}

	public void viewAllBoards(){
		this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]/ul/li/a[@href=\"#\"]"));
		long timeout = 500;
		if(this.waitForElementBeingPresentOnPage(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]"), timeout)){
			this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]/ul[3]/li/a[text()=\"View all boards\"]"));
		}else{
			throw new IllegalStateException("viewAllBoards: dropdown appearance not handled properly");
		}
	}
}
