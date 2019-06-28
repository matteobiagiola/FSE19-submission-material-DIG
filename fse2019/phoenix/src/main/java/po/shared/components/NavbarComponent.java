package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NavbarComponent extends BasePageObject implements PageComponent {

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    public void clickOnLogo(){
        this.clickOn(By.xpath("//a[@href=\"/\"]"));
    }

    //it considers also shared boards
    public void goToBoard(String boardName){
        this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]/ul/li/a[@href=\"#\"]"));
        long timeout = 500;
        if(this.waitForElementBeingPresentOnPage(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]//a[@href=\"#\"]"), timeout, TimeUnit.MILLISECONDS)){
            List<WebElement> linksToBoards = this.findElements(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]//a[@href=\"#\"]"));
            for(WebElement linkToBoard: linksToBoards){
                String currentBoardName = this.getText(linkToBoard);
                if(currentBoardName.equals(boardName)){
                    this.clickOn(linkToBoard);
                    break;
                }
            }
        }else{
            throw new IllegalStateException("goToBoard: dropdown appearance not handled properly");
        }
    }

    public boolean isBoardPresent(String boardName){
        this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]/ul/li/a[@href=\"#\"]"));
        long timeout = 500;
        if(this.waitForElementBeingPresentOnPage(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]"), timeout, TimeUnit.MILLISECONDS)){
            List<WebElement> linksToBoards = this.findElements(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]//a[@href=\"#\"]"));
            for(WebElement linkToBoard: linksToBoards){
                String currentBoardName = this.getText(linkToBoard);
                if(currentBoardName.equals(boardName)){
                    return true;
                }
            }
            this.clickOnName(); // if boardName is not present the dropdown menu remains open; to close it I need to click anywhere else -> it is reasonable to click on the current user name since the action has no effect
            return false;
        }else{
            throw new IllegalStateException("goToBoard: dropdown appearance not handled properly");
        }
    }

    public void clickOnName(){
        this.clickOnSelenium(By.xpath("//nav[@class=\"right\"]//a[@class=\"current-user\"]")); // I have to use selenium click since the js click has no effect for an element that is not clickable
    }

    public void viewAllBoards(){
        this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]/ul/li/a[@href=\"#\"]"));
        long timeout = 500;
        if(this.waitForElementBeingPresentOnPage(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]"), timeout, TimeUnit.MILLISECONDS)){
            this.clickOn(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]/ul[3]/li/a[text()=\"View all boards\"]"));
        }else{
            throw new IllegalStateException("viewAllBoards: dropdown appearance not handled properly");
        }
    }

    public void signOut(){
        this.clickOn(By.id("crawler-sign-out"));
    }
}
