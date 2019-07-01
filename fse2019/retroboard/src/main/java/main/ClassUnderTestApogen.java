package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;

public class ClassUnderTestApogen {

	private Object currentPage = null;
	private WebDriver driver;

	public ClassUnderTestApogen(WebDriver driver) {
		this.currentPage = new po_apogen.LoginPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTestApogen() {
		// start driver and browser
		driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po_apogen.LoginPage(driver);
	}

	// PO Name: PreviousPage
	public void goToAdvancedPreviousPage() {
		if (this.currentPage instanceof po_apogen.PreviousPage) {
			po_apogen.PreviousPage page = (po_apogen.PreviousPage) this.currentPage;
			page.previousComponent.goToAdvanced();
			this.currentPage = new po_apogen.AdvancedPage(
					page.previousComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAdvancedPreviousPage: expected po_apogen.PreviousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PreviousPage
	public void goToIndexPreviousPage() {
		if (this.currentPage instanceof po_apogen.PreviousPage) {
			po_apogen.PreviousPage page = (po_apogen.PreviousPage) this.currentPage;
			page.previousComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.previousComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexPreviousPage: expected po_apogen.PreviousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PreviousPage
	public void goToPreviousPreviousPage() {
		if (this.currentPage instanceof po_apogen.PreviousPage) {
			po_apogen.PreviousPage page = (po_apogen.PreviousPage) this.currentPage;
			page.previousComponent.goToPrevious();
			this.currentPage = new po_apogen.PreviousPage(
					page.previousComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPreviousPreviousPage: expected po_apogen.PreviousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PreviousPage
	public void goToRetrospectivePreviousPage(
			custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po_apogen.PreviousPage) {
			po_apogen.PreviousPage page = (po_apogen.PreviousPage) this.currentPage;
			if (page.previousComponent.isBoardPresent(boardName.value())) {
				page.previousComponent.goToRetrospective(boardName);
				this.currentPage = new po_apogen.RetrospectivePage(
						page.previousComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToRetrospective: board " + (boardName.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRetrospectivePreviousPage: expected po_apogen.PreviousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void goToIndexMenuPage() {
		if (this.currentPage instanceof po_apogen.MenuPage) {
			po_apogen.MenuPage page = (po_apogen.MenuPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("((//body/div)[2]/div//button)[1]"));
			page.clickOutsideTheModal();
			this.currentPage = new po_apogen.IndexPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexMenuPage: expected po_apogen.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void logoutMenuPage() {
		if (this.currentPage instanceof po_apogen.MenuPage) {
			po_apogen.MenuPage page = (po_apogen.MenuPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("((//body/div)[2]/div//button)[2]"));
			page.clickOutsideTheModal();
			this.currentPage = new po_apogen.LoginPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"logoutMenuPage: expected po_apogen.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void toggleSummaryModeMenuPage() {
		if (this.currentPage instanceof po_apogen.MenuPage) {
			po_apogen.MenuPage page = (po_apogen.MenuPage) this.currentPage;
			if (!(page.isSummaryModeOn())) {
				page.clickOn(org.openqa.selenium.By
						.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
				page.clickOutsideTheModal();
				this.currentPage = new po_apogen.RetrospectivePage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"toggleSummaryMode: summary mode is already on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"toggleSummaryModeMenuPage: expected po_apogen.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void untoggleSummaryModeMenuPage() {
		if (this.currentPage instanceof po_apogen.MenuPage) {
			po_apogen.MenuPage page = (po_apogen.MenuPage) this.currentPage;
			if (page.isSummaryModeOn()) {
				page.clickOn(org.openqa.selenium.By
						.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
				page.clickOutsideTheModal();
				this.currentPage = new po_apogen.RetrospectivePage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"toggleSummaryMode: summary mode is off");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"untoggleSummaryModeMenuPage: expected po_apogen.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AdvancedPage
	public void goToAdvancedAdvancedPage() {
		if (this.currentPage instanceof po_apogen.AdvancedPage) {
			po_apogen.AdvancedPage page = (po_apogen.AdvancedPage) this.currentPage;
			page.advancedComponent.goToAdvanced();
			this.currentPage = new po_apogen.AdvancedPage(
					page.advancedComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAdvancedAdvancedPage: expected po_apogen.AdvancedPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AdvancedPage
	public void goToIndexAdvancedPage() {
		if (this.currentPage instanceof po_apogen.AdvancedPage) {
			po_apogen.AdvancedPage page = (po_apogen.AdvancedPage) this.currentPage;
			page.advancedComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.advancedComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexAdvancedPage: expected po_apogen.AdvancedPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AdvancedPage
	public void goToLoginAdvancedPage() {
		if (this.currentPage instanceof po_apogen.AdvancedPage) {
			po_apogen.AdvancedPage page = (po_apogen.AdvancedPage) this.currentPage;
			page.advancedComponent.goToLogin();
			this.currentPage = new po_apogen.LoginPage(
					page.advancedComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToLoginAdvancedPage: expected po_apogen.AdvancedPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AdvancedPage
	public void goToPreviousAdvancedPage() {
		if (this.currentPage instanceof po_apogen.AdvancedPage) {
			po_apogen.AdvancedPage page = (po_apogen.AdvancedPage) this.currentPage;
			if (page.advancedComponent.isPreviousTabPresent()) {
				page.advancedComponent.goToPrevious();
				this.currentPage = new po_apogen.PreviousPage(
						page.advancedComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"goToPreviousView: previous tab is not present");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPreviousAdvancedPage: expected po_apogen.AdvancedPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToAdvancedIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToAdvanced();
			this.currentPage = new po_apogen.AdvancedPage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAdvancedIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToIndexIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToPreviousIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			if (page.indexComponent.isPreviousTabPresent()) {
				page.indexComponent.goToPrevious();
				this.currentPage = new po_apogen.PreviousPage(
						page.indexComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"goToPreviousView: previous tab is not present");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPreviousIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToRetrospectiveIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToRetrospective();
			this.currentPage = new po_apogen.RetrospectivePage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRetrospectiveIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: LoginPage
	public void loginLoginPage(custom_classes.PeopleNames peopleName) {
		if (this.currentPage instanceof po_apogen.LoginPage) {
			po_apogen.LoginPage page = (po_apogen.LoginPage) this.currentPage;
			page.loginComponent.typeName(peopleName.name());
			page.loginComponent.clickStart();
			long timeout = 100;
			if (page.loginComponent
					.waitForElementBeingPresentOnPage(
							org.openqa.selenium.By
									.xpath("//button[text()=\"Create a new session\"]"),
							timeout)) {
				this.currentPage = new po_apogen.IndexPage(
						page.loginComponent.getDriver());
			} else {
				this.currentPage = new po_apogen.RetrospectivePage(
						page.loginComponent.getDriver());
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"loginLoginPage: expected po_apogen.LoginPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void createIdeasPostRetrospectivePage(
			custom_classes.IdeasPosts ideasPost) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			if (!(page.retrospectiveComponent.isSummaryModeOn())) {
				page.retrospectiveComponent.typeIdeasPost(ideasPost.value());
				this.currentPage = new po_apogen.RetrospectivePage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"createIdeasPost: summary mode is on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createIdeasPostRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void createNotWentWellPostRetrospectivePage(
			custom_classes.NotWentWellPosts notWentWellPost) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			if (!(page.retrospectiveComponent.isSummaryModeOn())) {
				page.retrospectiveComponent.typeNotWentWellPost(notWentWellPost
						.value());
				this.currentPage = new po_apogen.RetrospectivePage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"createNotWentWellPost: summary mode is on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createNotWentWellPostRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void createWentWellPostRetrospectivePage(
			custom_classes.WentWellPosts wentWellPost) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			if (!(page.retrospectiveComponent.isSummaryModeOn())) {
				page.retrospectiveComponent.typeWentWellPost(wentWellPost
						.value());
				this.currentPage = new po_apogen.RetrospectivePage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"createWentWellPost: summary mode is on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createWentWellPostRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void deleteIdeasPostRetrospectivePage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isIdeasPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isIdeasPostShared(id.value)))) {
				page.retrospectiveComponent.deleteIdeasPost(id.value);
				this.currentPage = new po_apogen.RetrospectivePage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteIdeasPost: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteIdeasPostRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void deleteNotWentWellPostRetrospectivePage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isNotWentWellPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isNotWentWellPostShared(id.value)))) {
				page.retrospectiveComponent.deleteNotWentWellPost(id.value);
				this.currentPage = new po_apogen.RetrospectivePage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteNotWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteNotWentWellPostRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void deleteWentWellPostRetrospectivePage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isWentWellPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isWentWellPostShared(id.value)))) {
				page.retrospectiveComponent.deleteWentWellPost(id.value);
				this.currentPage = new po_apogen.RetrospectivePage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteWentWellPostRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void goToMenuRetrospectivePage() {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			page.retrospectiveComponent.goToMenu();
			this.currentPage = new po_apogen.MenuPage(
					page.retrospectiveComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToMenuRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectivePage
	public void renameBoardRetrospectivePage(
			custom_classes.BoardNames newBoardName) {
		if (this.currentPage instanceof po_apogen.RetrospectivePage) {
			po_apogen.RetrospectivePage page = (po_apogen.RetrospectivePage) this.currentPage;
			page.retrospectiveComponent.renameBoard(newBoardName.value());
			this.currentPage = new po_apogen.RetrospectivePage(
					page.retrospectiveComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"renameBoardRetrospectivePage: expected po_apogen.RetrospectivePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// will be called by es at the end of each test. It is important that it is
	// called quitDriver (search for the string in es client)
	private void quitDriver() {
		driver.quit();
	}
}
