package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class NewBoardPageComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for State21 (state21) --> BoardsContainerPage
	 */
	public NewBoardPageComponent(WebDriver driver) {
		super(driver);
	}

	public void new_board_form(BoardNames boardName) {
		this.createBoard(boardName.value());
	}

	public void goToHomePage() {
		this.closeAddNewBoardForm();
	}

	/*------- added */

	public void closeAddNewBoardForm(){
		this.clickOn(By.xpath("//form[@id=\"new_board_form\"]/a[text()=\"cancel\"]"));
	}

	public void createBoard(String boardName){
		this.type(By.xpath("//form[@id=\"new_board_form\"]/input[@id=\"board_name\"]"), boardName);
		this.clickOn(By.xpath("//form[@id=\"new_board_form\"]/button[text()=\"Create board\"]"));
		long timeoutMillis = 200;
		this.waitForTimeoutExpires(timeoutMillis); // wait that the new list is added to the DOM: no other way to do it since I don't know in which position (index) the element will be added
	}

}
