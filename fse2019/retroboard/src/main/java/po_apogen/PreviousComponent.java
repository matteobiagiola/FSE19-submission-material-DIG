package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class PreviousComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Previous (state31) --> HomeContainerPage
	 */
	public PreviousComponent(WebDriver driver) {
		super(driver);
	}

	// goToBoard
	public void goToRetrospective(BoardNames boardName) {
		this.clickOnBoard(boardName.value());
	}

	// no correspondence with manual PO
	public void goToIndex() {
		this.clickCreate();
	}

	//	goToPreviousView
	public void goToPrevious() {
		this.clickPrevious();
	}

	// no correspondence with manual PO
	public void goToAdvanced() {
		this.clickAdvanced();
	}

	/*--------- added */

	public void clickCreateSession(){
		this.clickOn(By.xpath("//button[text()=\"Create a new session\"]"));
	}

	public void clickAdvanced(){
		this.clickOn(By.xpath("//label[text()=\"Advanced\"]"));
	}

	public void clickPrevious(){
		this.clickOn(By.xpath("//label[text()=\"Previous\"]"));
	}

	public boolean isPreviousTabPresent(){
		return this.isElementPresentOnPage(By.xpath("//label[text()=\"Previous\"]"));
	}

	public boolean isPreviousTabActive(){
		WebElement activeTab = this.findElement(By.xpath("//nav/label[contains(@class,\"theme__active___2SLiK\")]"));
		String text = this.getText(activeTab);
		if(text.equalsIgnoreCase("Previous")){
			return true;
		}
		return false;
	}

	public void clickCreate(){
		this.clickOn(By.xpath("//label[text()=\"Create\"]"));
	}

	public List<WebElement> getBoardsList(){
		return this.findElements(By.xpath("//ul/li"));
	}

	public boolean isBoardPresent(String boardName){
		List<WebElement> boardsList = this.getBoardsList();
		for(WebElement board: boardsList){
			WebElement spanWithBoardName = this.findElementStartingFrom(board, By.xpath(".//span[contains(@class,\"theme__primary___22ZvQ\")]"));
			String currentBoardName = this.getText(spanWithBoardName);
			if(currentBoardName.equals(boardName)){
				return true;
			}
		}
		return false;
	}

	public void clickOnBoard(String boardName){
		List<WebElement> boardsList = this.getBoardsList();
		for(WebElement board: boardsList){
			WebElement spanWithBoardName = this.findElementStartingFrom(board, By.xpath(".//span[contains(@class,\"theme__primary___22ZvQ\")]"));
			String currentBoardName = this.getText(spanWithBoardName);
			if(currentBoardName.equals(boardName)){
				this.clickOn(board);
				break;
			}
		}
	}

}
