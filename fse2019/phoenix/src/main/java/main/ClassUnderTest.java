package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;
import po_utils.ResetAppState;

public class ClassUnderTest {

	private Object currentPage = null;

	public ClassUnderTest(WebDriver driver) {
		ResetAppState.reset(driver);
		this.currentPage = new po.login.pages.LoginContainerPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTest() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po.login.pages.LoginContainerPage(driver);
	}

	// PO Name: BoardsContainerPage
	public void addNewBoardBoardsContainerPage(
			custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			page.boardsComponent.openAddNewBoardForm();
			page.boardsComponent.createBoard(boardName.value());
			this.currentPage = new po.list.pages.BoardListContainerPage(
					page.boardsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewBoardBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardsContainerPage
	public void goToBoardClickingOnItBoardsContainerPage(
			custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			if (page.boardsComponent.isBoardPresent(boardName.value())) {
				page.boardsComponent.clickOnBoard(boardName.value());
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.navbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToBoardClickingOnIt: board " + (boardName.value())) + " does not exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardClickingOnItBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardsContainerPage
	public void goToBoardListBoardsContainerPage() {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.board.pages.BoardsContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardListBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardsContainerPage
	public void goToBoardUsingNavbarBoardsContainerPage(
			custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			if (page.boardsComponent.isBoardPresent(boardName.value())) {
				page.navbarComponent.goToBoard(boardName.value());
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.navbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToBoardUsingNavbar: board " + (boardName.value())) + " does not exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardUsingNavbarBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardsContainerPage
	public void openAddNewBoardFormBoardsContainerPage() {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			page.boardsComponent.openAddNewBoardForm();
			page.boardsComponent.closeAddNewBoardForm();
			this.currentPage = new po.board.pages.BoardsContainerPage(
					page.boardsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"openAddNewBoardFormBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardsContainerPage
	public void signOutBoardsContainerPage() {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			page.navbarComponent.signOut();
			this.currentPage = new po.login.pages.LoginContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"signOutBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardsContainerPage
	public void viewAllBoardsBoardsContainerPage() {
		if (this.currentPage instanceof po.board.pages.BoardsContainerPage) {
			po.board.pages.BoardsContainerPage page = (po.board.pages.BoardsContainerPage) this.currentPage;
			if (page.boardsComponent.existsBoard()) {
				page.navbarComponent.viewAllBoards();
				this.currentPage = new po.board.pages.BoardsContainerPage(
						page.boardsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"viewAllBoards: does not exist any board");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"viewAllBoardsBoardsContainerPage: expected po.board.pages.BoardsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void addNewCardToListBoardListContainerPage(
			custom_classes.ListNames listName, custom_classes.CardText cardText) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if (page.boardListComponent.isListPresent(listName.value())) {
				page.boardListComponent.openNewCardForm(listName.value());
				page.boardListComponent.addNewCardToList(listName.value(),
						cardText.value());
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.boardListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addNewCardToList: list " + (listName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewCardToListBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void addNewListBoardListContainerPage(
			custom_classes.ListNames listName) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			page.boardListComponent.openListForm();
			page.boardListComponent.createList(listName.value());
			this.currentPage = new po.list.pages.BoardListContainerPage(
					page.boardListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewListBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void addNewMemberToCurrentBoardBoardListContainerPage(
			custom_classes.Email newMember) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if (!(page.boardListComponent.isBoardShared())) {
				page.boardListComponent.openNewMemberForm();
				page.boardListComponent.addNewMember(newMember.value());
				long timeout = 500;
				if (page.boardListComponent
						.waitForElementBeingPresentOnPage(
								org.openqa.selenium.By
										.xpath("//header[@class=\"view-header\"]/ul[@class=\"board-users\"]//li/ul[@class=\"drop-down active\"]//div[@class=\"error\"]"),
								timeout,
								java.util.concurrent.TimeUnit.MILLISECONDS)) {
					page.boardListComponent.closeNewMemberForm();
					this.currentPage = new po.list.pages.BoardListContainerPage(
							page.boardListComponent.getDriver());
				} else {
					this.currentPage = new po.list.pages.BoardListContainerPage(
							page.boardListComponent.getDriver());
				}
			} else {
				throw new NotTheRightInputValuesException(
						"addNewMemberToCurrentBoard: the board is shared");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewMemberToCurrentBoardBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void goToBoardListBoardListContainerPage() {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.board.pages.BoardsContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardListBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void goToBoardUsingNavbarBoardListContainerPage(
			custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if (page.navbarComponent.isBoardPresent(boardName.value())) {
				page.navbarComponent.goToBoard(boardName.value());
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.navbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToBoardUsingNavbar: board name " + (boardName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardUsingNavbarBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void goToCardOfListBoardListContainerPage(
			custom_classes.ListNames listName, custom_classes.Id cardId) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if ((page.boardListComponent.isListPresent(listName.value()))
					&& (page.boardListComponent.isCardPresentOnList(
							listName.value(), cardId.value))) {
				page.boardListComponent.clickOnCardOnList(listName.value(),
						cardId.value);
				this.currentPage = new po.list.pages.modals.CardDetails(
						page.boardListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((("goToCardOfList: list name " + (listName.value())) + " is not present or card with id ") + cardId.value) + " is not present on list ") + (listName
								.value())));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToCardOfListBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void openAddNewListFormBoardListContainerPage() {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			page.boardListComponent.openListForm();
			page.boardListComponent.closeListForm();
			this.currentPage = new po.list.pages.BoardListContainerPage(
					page.boardListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"openAddNewListFormBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void openAddNewMemberFormBoardListContainerPage() {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if (!(page.boardListComponent.isBoardShared())) {
				page.boardListComponent.openNewMemberForm();
				page.boardListComponent.closeNewMemberForm();
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.boardListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"openAddNewMemberForm: the board is shared");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"openAddNewMemberFormBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void openNewCardToListFormBoardListContainerPage(
			custom_classes.ListNames listName) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if (page.boardListComponent.isListPresent(listName.value())) {
				page.boardListComponent.openNewCardForm(listName.value());
				page.boardListComponent.closeNewCardForm(listName.value());
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.boardListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("openNewCardToListForm: list " + (listName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"openNewCardToListFormBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void openUpdateListFormBoardListContainerPage(
			custom_classes.ListNames listName) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if (page.boardListComponent.isListPresent(listName.value())) {
				page.boardListComponent.clickOnList(listName.value());
				page.boardListComponent.closeListForm();
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.boardListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("openUpdateListForm: list name " + (listName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"openUpdateListFormBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void signOutBoardListContainerPage() {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			page.navbarComponent.signOut();
			this.currentPage = new po.login.pages.LoginContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"signOutBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void updateListBoardListContainerPage(
			custom_classes.ListNames currentListName,
			custom_classes.ListNames newListName) {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			if ((!(currentListName.value().equals(newListName.value())))
					&& (page.boardListComponent.isListPresent(currentListName
							.value()))) {
				page.boardListComponent.clickOnList(currentListName.value());
				page.boardListComponent.updateList(newListName.value());
				this.currentPage = new po.list.pages.BoardListContainerPage(
						page.boardListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((((("updateList: current list name " + (currentListName
								.value())) + " and new list name ") + (newListName
								.value())) + " are equal or current list name ") + (currentListName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"updateListBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardListContainerPage
	public void viewAllBoardsBoardListContainerPage() {
		if (this.currentPage instanceof po.list.pages.BoardListContainerPage) {
			po.list.pages.BoardListContainerPage page = (po.list.pages.BoardListContainerPage) this.currentPage;
			page.navbarComponent.viewAllBoards();
			this.currentPage = new po.board.pages.BoardsContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"viewAllBoardsBoardListContainerPage: expected po.list.pages.BoardListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void addCommentCardDetails(custom_classes.CardComment cardComment) {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			page.type(
					org.openqa.selenium.By
							.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//textarea[@placeholder=\"Write a comment...\"]"),
					cardComment.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//button[text()=\"Save comment\"]"));
			this.currentPage = new po.list.pages.modals.CardDetails(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addCommentCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void addMemberToCardCardDetails(custom_classes.Email email) {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			if ((page.isMemberPresent(email))
					&& (!(page.isAlreadyMemberOfCard(email)))) {
				page.openMemberWindow();
				page.clickOnMember(email);
				page.closeMemberWindow();
				this.currentPage = new po.list.pages.modals.CardDetails(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addMemberToCard: member with email " + (email
								.value())) + " is already member of card or it does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addMemberToCardCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void addTagToCardCardDetails(custom_classes.CardTag cardTag) {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			if (!(page.isTagAlreadyActive(cardTag))) {
				page.openTagWindow();
				page.clickOnTag(cardTag);
				page.closeTagWindow();
				this.currentPage = new po.list.pages.modals.CardDetails(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addTagToCard: card tag " + (cardTag.value())) + " is already active on the card"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addTagToCardCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void closeCardDetailsCardDetails() {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//a[@class=\"close\"]"));
			this.currentPage = new po.list.pages.BoardListContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeCardDetailsCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void deleteCardCardDetails() {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//a[@class=\"delete\"]"));
			this.currentPage = new po.list.pages.BoardListContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteCardCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void editDescriptionCardDetails(custom_classes.CardText cardText,
			custom_classes.CardDescription cardDescription) {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//a[text()=\"Edit\"]"));
			page.type(
					org.openqa.selenium.By
							.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/header/form/input[@placeholder=\"Title\"]"),
					cardText.value());
			page.type(
					org.openqa.selenium.By
							.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/header/form/textarea[@placeholder=\"Description\"]"),
					cardDescription.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/header/form/button[text()=\"Save card\"]"));
			this.currentPage = new po.list.pages.modals.CardDetails(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"editDescriptionCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void openEditDescriptionFormCardDetails() {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//a[text()=\"Edit\"]"));
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//form/a[text()=\"cancel\"]"));
			this.currentPage = new po.list.pages.modals.CardDetails(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"openEditDescriptionFormCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void removeMemberFromCardCardDetails(custom_classes.Email email) {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			if ((page.isMemberPresent(email))
					&& (page.isAlreadyMemberOfCard(email))) {
				page.openMemberWindow();
				page.clickOnMember(email);
				page.closeMemberWindow();
				this.currentPage = new po.list.pages.modals.CardDetails(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("removeMemberFromCard: member with email " + (email
								.value())) + " is not a member of card or it does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"removeMemberFromCardCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetails
	public void removeTagToCardCardDetails(custom_classes.CardTag cardTag) {
		if (this.currentPage instanceof po.list.pages.modals.CardDetails) {
			po.list.pages.modals.CardDetails page = (po.list.pages.modals.CardDetails) this.currentPage;
			if (page.isTagAlreadyActive(cardTag)) {
				page.openTagWindow();
				page.clickOnTag(cardTag);
				page.closeTagWindow();
				this.currentPage = new po.list.pages.modals.CardDetails(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("removeTagToCard: card tag " + (cardTag.value())) + " is not active on the card"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"removeTagToCardCardDetails: expected po.list.pages.modals.CardDetails, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: LoginContainerPage
	public void goToSignUpLoginContainerPage() {
		if (this.currentPage instanceof po.login.pages.LoginContainerPage) {
			po.login.pages.LoginContainerPage page = (po.login.pages.LoginContainerPage) this.currentPage;
			page.loginComponent.clickOnCreateNewAccount();
			this.currentPage = new po.login.pages.SignUpContainerPage(
					page.loginComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSignUpLoginContainerPage: expected po.login.pages.LoginContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: LoginContainerPage
	public void loginAsJohnLoginContainerPage() {
		if (this.currentPage instanceof po.login.pages.LoginContainerPage) {
			po.login.pages.LoginContainerPage page = (po.login.pages.LoginContainerPage) this.currentPage;
			page.loginComponent.signIn();
			this.currentPage = new po.board.pages.BoardsContainerPage(
					page.loginComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"loginAsJohnLoginContainerPage: expected po.login.pages.LoginContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: LoginContainerPage
	public void loginLoginContainerPage(custom_classes.Email email,
			custom_classes.Passwords password) {
		if (this.currentPage instanceof po.login.pages.LoginContainerPage) {
			po.login.pages.LoginContainerPage page = (po.login.pages.LoginContainerPage) this.currentPage;
			page.loginComponent.typeEmail(email.value());
			page.loginComponent.typePassword(password.value());
			page.loginComponent.signIn();
			long timeoutInMillis = 500;
			if (page.loginComponent
					.waitForElementBeingPresentOnPage(org.openqa.selenium.By
							.xpath("//div[@class=\"error\"]"), timeoutInMillis,
							java.util.concurrent.TimeUnit.MILLISECONDS)) {
				page.loginComponent.refreshPage();
				this.currentPage = new po.login.pages.LoginContainerPage(
						page.loginComponent.getDriver());
			} else {
				this.currentPage = new po.board.pages.BoardsContainerPage(
						page.loginComponent.getDriver());
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"loginLoginContainerPage: expected po.login.pages.LoginContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SignUpContainerPage
	public void goToSignInPageSignUpContainerPage() {
		if (this.currentPage instanceof po.login.pages.SignUpContainerPage) {
			po.login.pages.SignUpContainerPage page = (po.login.pages.SignUpContainerPage) this.currentPage;
			page.signUpComponent.clickOnSignIn();
			this.currentPage = new po.login.pages.LoginContainerPage(
					page.signUpComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSignInPageSignUpContainerPage: expected po.login.pages.SignUpContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SignUpContainerPage
	public void signUpFailurePasswordSignUpContainerPage(
			custom_classes.PeopleNames firstName,
			custom_classes.PeopleNames lastName, custom_classes.Email email,
			custom_classes.Passwords password,
			custom_classes.Passwords passwordConfirmation) {
		if (this.currentPage instanceof po.login.pages.SignUpContainerPage) {
			po.login.pages.SignUpContainerPage page = (po.login.pages.SignUpContainerPage) this.currentPage;
			if (!(password.value().equals(passwordConfirmation.value()))) {
				page.signUpComponent.typeFirstName(firstName.value());
				page.signUpComponent.typeLastName(lastName.value());
				page.signUpComponent.typeEmail(email.value());
				page.signUpComponent.typePassword(password.value());
				page.signUpComponent
						.typePasswordConfirmation(passwordConfirmation.value());
				page.signUpComponent.clickOnSignUp();
				long timeoutInMillis = 500;
				if (page.signUpComponent
						.waitForElementBeingPresentOnPage(
								org.openqa.selenium.By
										.xpath("//div[@class=\"error\"]"),
								timeoutInMillis,
								java.util.concurrent.TimeUnit.MILLISECONDS)) {
					page.signUpComponent.refreshPage();
					this.currentPage = new po.login.pages.SignUpContainerPage(
							page.signUpComponent.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"signUpFailurePassword: password does not match notification not handled properly");
				}
			} else {
				throw new NotTheRightInputValuesException(
						"signUpFailurePassword: the two passwords are equal.");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"signUpFailurePasswordSignUpContainerPage: expected po.login.pages.SignUpContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SignUpContainerPage
	public void signUpSignUpContainerPage(custom_classes.PeopleNames firstName,
			custom_classes.PeopleNames lastName, custom_classes.Email email,
			custom_classes.Passwords password,
			custom_classes.Passwords passwordConfirmation) {
		if (this.currentPage instanceof po.login.pages.SignUpContainerPage) {
			po.login.pages.SignUpContainerPage page = (po.login.pages.SignUpContainerPage) this.currentPage;
			if (password.value().equals(passwordConfirmation.value())) {
				page.signUpComponent.typeFirstName(firstName.value());
				page.signUpComponent.typeLastName(lastName.value());
				page.signUpComponent.typeEmail(email.value());
				page.signUpComponent.typePassword(password.value());
				page.signUpComponent
						.typePasswordConfirmation(passwordConfirmation.value());
				page.signUpComponent.clickOnSignUp();
				long timeoutInMillis = 500;
				if (page.signUpComponent
						.waitForElementBeingPresentOnPage(
								org.openqa.selenium.By
										.xpath("//div[@class=\"error\"]"),
								timeoutInMillis,
								java.util.concurrent.TimeUnit.MILLISECONDS)) {
					page.signUpComponent.refreshPage();
					this.currentPage = new po.login.pages.SignUpContainerPage(
							page.signUpComponent.getDriver());
				} else {
					this.currentPage = new po.board.pages.BoardsContainerPage(
							page.signUpComponent.getDriver());
				}
			} else {
				throw new NotTheRightInputValuesException(
						"signUp: the two passwords do not match.");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"signUpSignUpContainerPage: expected po.login.pages.SignUpContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
