package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;
import po_utils.ResetAppState;

public class ClassUnderTestApogen {

	private Object currentPage = null;

	public ClassUnderTestApogen(WebDriver driver) {
		ResetAppState.reset(driver);
		this.currentPage = new po_apogen.IndexPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTestApogen() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po_apogen.IndexPage(driver);
	}

	// PO Name: NewBoardPage
	public void goToHomePageNewBoardPage() {
		if (this.currentPage instanceof po_apogen.NewBoardPage) {
			po_apogen.NewBoardPage page = (po_apogen.NewBoardPage) this.currentPage;
			page.newBoardPageComponent.goToHomePage();
			this.currentPage = new po_apogen.HomePage(
					page.newBoardPageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomePageNewBoardPage: expected po_apogen.NewBoardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewBoardPage
	public void new_board_formNewBoardPage(custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po_apogen.NewBoardPage) {
			po_apogen.NewBoardPage page = (po_apogen.NewBoardPage) this.currentPage;
			page.newBoardPageComponent.new_board_form(boardName);
			this.currentPage = new po_apogen.BoardPage(
					page.newBoardPageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"new_board_formNewBoardPage: expected po_apogen.NewBoardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetailsPage
	public void deleteCardCardDetailsPage() {
		if (this.currentPage instanceof po_apogen.CardDetailsPage) {
			po_apogen.CardDetailsPage page = (po_apogen.CardDetailsPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//a[@class=\"delete\"]"));
			this.currentPage = new po_apogen.BoardPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteCardCardDetailsPage: expected po_apogen.CardDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetailsPage
	public void goToBoardPageCardDetailsPage() {
		if (this.currentPage instanceof po_apogen.CardDetailsPage) {
			po_apogen.CardDetailsPage page = (po_apogen.CardDetailsPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//a[@class=\"close\"]"));
			this.currentPage = new po_apogen.BoardPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardPageCardDetailsPage: expected po_apogen.CardDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CardDetailsPage
	public void save_commentCardDetailsPage(
			custom_classes.CardComment cardComment) {
		if (this.currentPage instanceof po_apogen.CardDetailsPage) {
			po_apogen.CardDetailsPage page = (po_apogen.CardDetailsPage) this.currentPage;
			page.type(
					org.openqa.selenium.By
							.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//textarea[@placeholder=\"Write a comment...\"]"),
					cardComment.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//button[text()=\"Save comment\"]"));
			this.currentPage = new po_apogen.CardDetailsPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"save_commentCardDetailsPage: expected po_apogen.CardDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToBoardUsingNavbarHomePage(custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			if (page.homePageComponent.isBoardPresent(boardName.value())) {
				page.homePageComponent.seeAvailableBoards();
				this.currentPage = new po_apogen.OwnedBoardsPage(
						page.homePageComponent.getDriver(), boardName);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToBoardUsingNavbar: board " + (boardName.value())) + " does not exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardUsingNavbarHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToIndexHomePage() {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			page.homePageComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.homePageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToNewBoardPageHomePage() {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			page.homePageComponent.goToNewBoardPage();
			this.currentPage = new po_apogen.NewBoardPage(
					page.homePageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToNewBoardPageHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void seeAvailableBoardsHomePage() {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			if (page.homePageComponent.existsBoard()) {
				page.homePageComponent.seeAvailableBoards();
				this.currentPage = new po_apogen.OwnedBoardsPage(
						page.homePageComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"viewAllBoards: does not exist any board");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"seeAvailableBoardsHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardPage
	public void add_new_listBoardPage(custom_classes.ListNames listName) {
		if (this.currentPage instanceof po_apogen.BoardPage) {
			po_apogen.BoardPage page = (po_apogen.BoardPage) this.currentPage;
			page.boardPageComponent.add_new_list(listName);
			this.currentPage = new po_apogen.BoardPage(
					page.boardPageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"add_new_listBoardPage: expected po_apogen.BoardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardPage
	public void goToBoardUsingNavbarBoardPage(
			custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po_apogen.BoardPage) {
			po_apogen.BoardPage page = (po_apogen.BoardPage) this.currentPage;
			if (page.boardPageComponent.isBoardPresent(boardName.value())) {
				this.currentPage = new po_apogen.OwnedBoardsPage(
						page.boardPageComponent.getDriver(), boardName);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToBoardUsingNavbar: board " + (boardName.value())) + " does not exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardUsingNavbarBoardPage: expected po_apogen.BoardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardPage
	public void goToCardDetailsPageBoardPage(custom_classes.ListNames listName,
			custom_classes.Id cardId) {
		if (this.currentPage instanceof po_apogen.BoardPage) {
			po_apogen.BoardPage page = (po_apogen.BoardPage) this.currentPage;
			if ((page.boardPageComponent.isListPresent(listName.value()))
					&& (page.boardPageComponent.isCardPresentOnList(
							listName.value(), cardId.value))) {
				page.boardPageComponent.goToCardDetailsPage(listName, cardId);
				this.currentPage = new po_apogen.CardDetailsPage(
						page.boardPageComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((("goToCardDetailsPage: list name " + (listName
								.value())) + " is not present or card with id ") + cardId.value) + " is not present on list ") + (listName
								.value())));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToCardDetailsPageBoardPage: expected po_apogen.BoardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: BoardPage
	public void goToInsertCardPageBoardPage(custom_classes.ListNames listName) {
		if (this.currentPage instanceof po_apogen.BoardPage) {
			po_apogen.BoardPage page = (po_apogen.BoardPage) this.currentPage;
			if (page.boardPageComponent.isListPresent(listName.value())) {
				page.boardPageComponent.goToInsertCardPage(listName);
				this.currentPage = new po_apogen.InsertCardPage(
						page.boardPageComponent.getDriver(), listName);
			} else {
				throw new NotTheRightInputValuesException(
						(("addNewCardToList: list " + (listName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToInsertCardPageBoardPage: expected po_apogen.BoardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnedBoardsPage
	public void goToBoardPageOwnedBoardsPage() {
		if (this.currentPage instanceof po_apogen.OwnedBoardsPage) {
			po_apogen.OwnedBoardsPage page = (po_apogen.OwnedBoardsPage) this.currentPage;
			if ((page.boardName) != null) {
				page.ownedBoardsComponent.goToBoardPage(page.boardName);
				this.currentPage = new po_apogen.BoardPage(
						page.ownedBoardsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"goToBoardPage: board name is null. PO was not instantiated with boardName");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardPageOwnedBoardsPage: expected po_apogen.OwnedBoardsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnedBoardsPage
	public void goToHomePageOwnedBoardsPage() {
		if (this.currentPage instanceof po_apogen.OwnedBoardsPage) {
			po_apogen.OwnedBoardsPage page = (po_apogen.OwnedBoardsPage) this.currentPage;
			page.ownedBoardsComponent.goToHomePage();
			this.currentPage = new po_apogen.HomePage(
					page.ownedBoardsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomePageOwnedBoardsPage: expected po_apogen.OwnedBoardsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToHomePageIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToHomePage();
			this.currentPage = new po_apogen.HomePage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomePageIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToSignUpIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToSignUp();
			this.currentPage = new po_apogen.SignUpPage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSignUpIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void sign_in_formIndexPage(custom_classes.Email email,
			custom_classes.Passwords password) {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.sign_in_form(email, password);
			long timeoutInMillis = 500;
			if (page.indexComponent.waitForElementBeingPresentOnPage(
					org.openqa.selenium.By.xpath("//div[@class=\"error\"]"),
					timeoutInMillis)) {
				page.indexComponent.refreshPage();
				this.currentPage = new po_apogen.IndexPage(
						page.indexComponent.getDriver());
			} else {
				this.currentPage = new po_apogen.HomePage(
						page.indexComponent.getDriver());
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"sign_in_formIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SignUpPage
	public void goToIndexSignUpPage() {
		if (this.currentPage instanceof po_apogen.SignUpPage) {
			po_apogen.SignUpPage page = (po_apogen.SignUpPage) this.currentPage;
			page.signUpComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.signUpComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexSignUpPage: expected po_apogen.SignUpPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SignUpPage
	public void sign_up_formSignUpPage(custom_classes.PeopleNames firstName,
			custom_classes.PeopleNames lastName, custom_classes.Email email,
			custom_classes.Passwords password,
			custom_classes.Passwords passwordConfirmation) {
		if (this.currentPage instanceof po_apogen.SignUpPage) {
			po_apogen.SignUpPage page = (po_apogen.SignUpPage) this.currentPage;
			if (password.value().equals(passwordConfirmation.value())) {
				page.signUpComponent.sign_up_form(firstName, lastName, email,
						password, passwordConfirmation);
				long timeoutInMillis = 500;
				if (page.signUpComponent
						.waitForElementBeingPresentOnPage(
								org.openqa.selenium.By
										.xpath("//div[@class=\"error\"]"),
								timeoutInMillis)) {
					page.signUpComponent.refreshPage();
					this.currentPage = new po_apogen.SignUpPage(
							page.signUpComponent.getDriver());
				} else {
					this.currentPage = new po_apogen.HomePage(
							page.signUpComponent.getDriver());
				}
			} else {
				throw new NotTheRightInputValuesException(
						"signUp: the two passwords do not match.");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"sign_up_formSignUpPage: expected po_apogen.SignUpPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: InsertCardPage
	public void goToBoardPageInsertCardPage() {
		if (this.currentPage instanceof po_apogen.InsertCardPage) {
			po_apogen.InsertCardPage page = (po_apogen.InsertCardPage) this.currentPage;
			page.insertCardPageComponent.goToBoardPage(page.listName);
			this.currentPage = new po_apogen.BoardPage(
					page.insertCardPageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardPageInsertCardPage: expected po_apogen.InsertCardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: InsertCardPage
	public void new_card_formInsertCardPage(custom_classes.CardText cardText) {
		if (this.currentPage instanceof po_apogen.InsertCardPage) {
			po_apogen.InsertCardPage page = (po_apogen.InsertCardPage) this.currentPage;
			page.insertCardPageComponent.new_card_form(page.listName, cardText);
			this.currentPage = new po_apogen.BoardPage(
					page.insertCardPageComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"new_card_formInsertCardPage: expected po_apogen.InsertCardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
