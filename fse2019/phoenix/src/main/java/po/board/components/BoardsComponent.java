package po.board.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class BoardsComponent extends BasePageObject implements PageComponent {

    public BoardsComponent(WebDriver driver) {
        super(driver);
    }

    //it considers also shared boards
    public List<WebElement> getBoards(){
        return this.findElements(By.xpath("//div[@class=\"boards-wrapper\"]/div[@id]"));
    }

    public boolean existsBoard(){
        return this.getBoards().size() > 0;
    }

    public boolean isBoardPresent(String boardName){
        List<WebElement> boards = this.getBoards();
        for(WebElement board: boards){
            WebElement headerFour = this.findElementJSByXPathStartingFrom(board, "./div/h4");
            String currentBoardName = this.getText(headerFour);
            if(currentBoardName.equals(boardName)){
                return true;
            }
        }
        return false;
    }

    public void openAddNewBoardForm(){
        this.clickOn(By.id("add_new_board"));
    }

    public void closeAddNewBoardForm(){
        this.clickOn(By.xpath("//form[@id=\"new_board_form\"]/a[text()=\"cancel\"]"));
    }

    public void createBoard(String boardName){
        this.type(By.xpath("//form[@id=\"new_board_form\"]/input[@id=\"board_name\"]"), boardName);
        this.clickOn(By.xpath("//form[@id=\"new_board_form\"]/button[text()=\"Create board\"]"));
        long timeoutMillis = 200;
        this.waitForTimeoutExpires(timeoutMillis); // wait that the new list is added to the DOM: no other way to do it since I don't know in which position (index) the element will be added
    }

    public void clickOnBoard(String boardName){
        List<WebElement> boards = this.getBoards();
        for(WebElement board: boards){
            WebElement headerFour = this.findElementJSByXPathStartingFrom(board, "./div/h4");
            String currentBoardName = this.getText(headerFour);
            if(currentBoardName.equals(boardName)){
                this.clickOn(board);
                break;
            }
        }
    }
}
