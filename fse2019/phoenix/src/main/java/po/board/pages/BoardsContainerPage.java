package po.board.pages;

import custom_classes.BoardNames;
import org.openqa.selenium.WebDriver;
import po.board.components.BoardsComponent;
import po.list.pages.BoardListContainerPage;
import po.login.pages.LoginContainerPage;
import po.shared.components.NavbarComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class BoardsContainerPage implements PageObject {

    public BoardsComponent boardsComponent;
    public NavbarComponent navbarComponent;

    public BoardsContainerPage(WebDriver driver){
        this.boardsComponent = new BoardsComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("BoardsContainerPage not loaded properly");
        }
    }

    //tested
    public BoardListContainerPage addNewBoard(BoardNames boardName){
        this.boardsComponent.openAddNewBoardForm();
        this.boardsComponent.createBoard(boardName.value());
        return new BoardListContainerPage(this.boardsComponent.getDriver());
    }

    //tested
    public BoardsContainerPage openAddNewBoardForm(){
        this.boardsComponent.openAddNewBoardForm();
        this.boardsComponent.closeAddNewBoardForm();
        return this;
    }

    //tested
    public BoardListContainerPage goToBoardUsingNavbar(BoardNames boardName){
        if(this.boardsComponent.isBoardPresent(boardName.value())){
            this.navbarComponent.goToBoard(boardName.value());
            return new BoardListContainerPage(this.navbarComponent.getDriver());
        }else{
            throw new IllegalArgumentException("goToBoardUsingNavbar: board " + boardName.value() + " does not exists");
        }
    }

    //tested
    public BoardListContainerPage goToBoardClickingOnIt(BoardNames boardName){
        if(this.boardsComponent.isBoardPresent(boardName.value())){
            this.boardsComponent.clickOnBoard(boardName.value());
            return new BoardListContainerPage(this.navbarComponent.getDriver());
        }else{
            throw new IllegalArgumentException("goToBoardClickingOnIt: board " + boardName.value() + " does not exists");
        }
    }

    //tested
    public LoginContainerPage signOut(){
        this.navbarComponent.signOut();
        return new LoginContainerPage(this.navbarComponent.getDriver());
    }

    //tested
    public BoardsContainerPage goToBoardList(){
        this.navbarComponent.clickOnLogo();
        return this;
    }

    //tested
    public BoardsContainerPage viewAllBoards(){
        if(this.boardsComponent.existsBoard()){
            this.navbarComponent.viewAllBoards();
            return this;
        }else{
            throw new IllegalArgumentException("viewAllBoards: does not exist any board");
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.boardsComponent.waitForElementBeingPresentOnPage(ConstantLocators.BOARDS.value())){
            return true;
        }
        return false;
    }
}
