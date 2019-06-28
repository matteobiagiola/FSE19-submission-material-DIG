package po.list.pages;

import custom_classes.BoardNames;
import custom_classes.CardText;
import custom_classes.Email;
import custom_classes.Id;
import custom_classes.ListNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.board.pages.BoardsContainerPage;
import po.list.components.BoardListComponent;
import po.list.pages.modals.CardDetails;
import po.login.pages.LoginContainerPage;
import po.shared.components.NavbarComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

import java.util.concurrent.TimeUnit;

public class BoardListContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public BoardListComponent boardListComponent;

    public BoardListContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.boardListComponent = new BoardListComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("BoardListContainerPage not loaded properly");
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
        return new BoardsContainerPage(this.navbarComponent.getDriver());
    }

    //tested
    public BoardsContainerPage viewAllBoards(){
        this.navbarComponent.viewAllBoards();
        return new BoardsContainerPage(this.navbarComponent.getDriver());
    }

    //tested
    public BoardListContainerPage goToBoardUsingNavbar(BoardNames boardName){
        if(this.navbarComponent.isBoardPresent(boardName.value())){
            this.navbarComponent.goToBoard(boardName.value());
            return new BoardListContainerPage(this.navbarComponent.getDriver()); //it may be another board different from the current one
        }else{
            throw new IllegalArgumentException("goToBoardUsingNavbar: board name " + boardName.value() + " does not exist");
        }
    }

    //tested
    public BoardListContainerPage openAddNewListForm(){
        this.boardListComponent.openListForm();
        this.boardListComponent.closeListForm();
        return this;
    }

    //tested
    public BoardListContainerPage addNewList(ListNames listName){
        this.boardListComponent.openListForm();
        this.boardListComponent.createList(listName.value());
        return this;
    }

    //tested
    public BoardListContainerPage openUpdateListForm(ListNames listName){
        if(this.boardListComponent.isListPresent(listName.value())){
            this.boardListComponent.clickOnList(listName.value());
            this.boardListComponent.closeListForm();
            return this;
        }else{
            throw new IllegalArgumentException("openUpdateListForm: list name " + listName.value() + " does not exist");
        }
    }

    //tested
    public BoardListContainerPage updateList(ListNames currentListName, ListNames newListName){
        if(!currentListName.value().equals(newListName.value()) && this.boardListComponent.isListPresent(currentListName.value())){
            this.boardListComponent.clickOnList(currentListName.value());
            this.boardListComponent.updateList(newListName.value());
            return this;
        }else{
            throw new IllegalArgumentException("updateList: current list name " + currentListName.value() + " and new list name " + newListName.value() + " are equal or current list name " + currentListName.value() + " does not exist");
        }
    }

    //tested
    public BoardListContainerPage openNewCardToListForm(ListNames listName){
        if(this.boardListComponent.isListPresent(listName.value())){
            this.boardListComponent.openNewCardForm(listName.value());
            this.boardListComponent.closeNewCardForm(listName.value());
            return this;
        }else{
            throw new IllegalArgumentException("openNewCardToListForm: list " + listName.value() + " does not exist");
        }
    }

    //tested
    public BoardListContainerPage addNewCardToList(ListNames listName, CardText cardText){
        if(this.boardListComponent.isListPresent(listName.value())){
            this.boardListComponent.openNewCardForm(listName.value());
            this.boardListComponent.addNewCardToList(listName.value(), cardText.value());
            return this;
        }else{
            throw new IllegalArgumentException("addNewCardToList: list " + listName.value() + " does not exist");
        }
    }

    //tested
    public CardDetails goToCardOfList(ListNames listName, Id cardId){
        if(this.boardListComponent.isListPresent(listName.value()) && this.boardListComponent.isCardPresentOnList(listName.value(), cardId.value)){
            this.boardListComponent.clickOnCardOnList(listName.value(), cardId.value);
            return new CardDetails(this.boardListComponent.getDriver());
        }else{
            throw new IllegalArgumentException("goToCardOfList: list name " + listName.value() + " is not present or card with id " + cardId.value + " is not present on list " + listName.value());
        }
    }

    //tested
    public BoardListContainerPage openAddNewMemberForm(){
        if(!this.boardListComponent.isBoardShared()){
            this.boardListComponent.openNewMemberForm();
            this.boardListComponent.closeNewMemberForm();
            return this;
        }else{
            throw new IllegalStateException("openAddNewMemberForm: the board is shared");
        }
    }

    //tested
    public BoardListContainerPage addNewMemberToCurrentBoard(Email newMember){
        if(!this.boardListComponent.isBoardShared()){
            this.boardListComponent.openNewMemberForm();
            this.boardListComponent.addNewMember(newMember.value());
            long timeout = 500;
            if(this.boardListComponent.waitForElementBeingPresentOnPage(By.xpath("//header[@class=\"view-header\"]/ul[@class=\"board-users\"]//li/ul[@class=\"drop-down active\"]//div[@class=\"error\"]"), timeout, TimeUnit.MILLISECONDS)){
                // email of new member does not exist
                this.boardListComponent.closeNewMemberForm();
                return this;
            }else{
                // email of the new member exists
                return this;
            }
        }else{
            throw new IllegalStateException("addNewMemberToCurrentBoard: the board is shared");
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.boardListComponent.waitForElementBeingPresentOnPage(ConstantLocators.LIST.value())){
            return true;
        }
        return false;
    }
}
