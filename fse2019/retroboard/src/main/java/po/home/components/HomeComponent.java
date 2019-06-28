package po.home.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

import java.util.List;

public class HomeComponent extends BasePageObject implements PageComponent {

    public HomeComponent(WebDriver driver) {
        super(driver);
    }

    public void clickLogout(){
        this.clickOn(By.xpath("//button[text()=\"Logout\"]"));
    }

    public void clickCreateSession(){
        this.clickOn(By.xpath("//button[text()=\"Create a new session\"]"));
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
