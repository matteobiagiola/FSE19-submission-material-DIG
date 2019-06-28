package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;

public class ClassUnderTest {

	private Object currentPage = null;
	private WebDriver driver;

	// BOOTSTRAP POINT
	public ClassUnderTest() {
		// start driver and browser
		driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po.login.pages.LoginContainerPage(driver);
	}

	// PO Name: HomeContainerPage
	public void createNewSessionHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.tabComponent.clickCreate();
			page.homeComponent.clickCreateSession();
			this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
					page.homeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"createNewSessionHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void goToBoardHomeContainerPage(custom_classes.BoardNames boardName) {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			if ((page.tabComponent.isPreviousTabActive())
					&& (page.homeComponent.isBoardPresent(boardName.value()))) {
				page.homeComponent.clickOnBoard(boardName.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.tabComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToBoard: previous tab is not active or board " + (boardName
								.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToBoardHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void goToHomeHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.navbarComponent.clickHomeLink();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void goToPreviousViewHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			if (page.tabComponent.isPreviousTabPresent()) {
				page.tabComponent.clickPrevious();
				this.currentPage = new po.home.pages.HomeContainerPage(
						page.tabComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"goToPreviousView: previous tab is not present");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPreviousViewHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void logoutHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.tabComponent.clickAdvanced();
			page.homeComponent.clickLogout();
			this.currentPage = new po.login.pages.LoginContainerPage(
					page.homeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"logoutHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void leaveSessionMenuPage() {
		if (this.currentPage instanceof po.retrospective.pages.modals.MenuPage) {
			po.retrospective.pages.modals.MenuPage page = (po.retrospective.pages.modals.MenuPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("((//body/div)[2]/div//button)[1]"));
			page.clickOutsideTheModal();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"leaveSessionMenuPage: expected po.retrospective.pages.modals.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void logoutMenuPage() {
		if (this.currentPage instanceof po.retrospective.pages.modals.MenuPage) {
			po.retrospective.pages.modals.MenuPage page = (po.retrospective.pages.modals.MenuPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("((//body/div)[2]/div//button)[2]"));
			page.clickOutsideTheModal();
			this.currentPage = new po.login.pages.LoginContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"logoutMenuPage: expected po.retrospective.pages.modals.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void toggleSummaryModeMenuPage() {
		if (this.currentPage instanceof po.retrospective.pages.modals.MenuPage) {
			po.retrospective.pages.modals.MenuPage page = (po.retrospective.pages.modals.MenuPage) this.currentPage;
			if (!(page.isSummaryModeOn())) {
				page.clickOn(org.openqa.selenium.By
						.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
				page.clickOutsideTheModal();
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"toggleSummaryMode: summary mode is already on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"toggleSummaryModeMenuPage: expected po.retrospective.pages.modals.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: MenuPage
	public void untoggleSummaryModeMenuPage() {
		if (this.currentPage instanceof po.retrospective.pages.modals.MenuPage) {
			po.retrospective.pages.modals.MenuPage page = (po.retrospective.pages.modals.MenuPage) this.currentPage;
			if (page.isSummaryModeOn()) {
				page.clickOn(org.openqa.selenium.By
						.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
				page.clickOutsideTheModal();
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"toggleSummaryMode: summary mode is off");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"untoggleSummaryModeMenuPage: expected po.retrospective.pages.modals.MenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void changeIdeasPostContentRetrospectiveContainerPage(
			custom_classes.Id id, custom_classes.IdeasPosts ideasPost) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isIdeasPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isIdeasPostShared(id.value)))) {
				page.retrospectiveComponent.changeContentOfIdeasPost(id.value,
						ideasPost.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("changeIdeasPostContent: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"changeIdeasPostContentRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void changeNotWentWellPostContentRetrospectiveContainerPage(
			custom_classes.Id id,
			custom_classes.NotWentWellPosts notWentWellPost) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isNotWentWellPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isNotWentWellPostShared(id.value)))) {
				page.retrospectiveComponent.changeContentOfNotWentWellPost(
						id.value, notWentWellPost.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("changeNotWentWellPostContent: summary mode is on or post with id " + id.value) + " is not present post or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"changeNotWentWellPostContentRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void changeWentWellPostContentRetrospectiveContainerPage(
			custom_classes.Id id, custom_classes.WentWellPosts wentWellPost) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isWentWellPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isWentWellPostShared(id.value)))) {
				page.retrospectiveComponent.changeContentOfWentWellPost(
						id.value, wentWellPost.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("changeWentWellPostContent: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"changeWentWellPostContentRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void createIdeasPostRetrospectiveContainerPage(
			custom_classes.IdeasPosts ideasPost) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (!(page.retrospectiveComponent.isSummaryModeOn())) {
				page.retrospectiveComponent.typeIdeasPost(ideasPost.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"createIdeasPost: summary mode is on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createIdeasPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void createNotWentWellPostRetrospectiveContainerPage(
			custom_classes.NotWentWellPosts notWentWellPost) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (!(page.retrospectiveComponent.isSummaryModeOn())) {
				page.retrospectiveComponent.typeNotWentWellPost(notWentWellPost
						.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"createNotWentWellPost: summary mode is on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createNotWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void createWentWellPostRetrospectiveContainerPage(
			custom_classes.WentWellPosts wentWellPost) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (!(page.retrospectiveComponent.isSummaryModeOn())) {
				page.retrospectiveComponent.typeWentWellPost(wentWellPost
						.value());
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"createWentWellPost: summary mode is on");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void deleteIdeasPostRetrospectiveContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isIdeasPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isIdeasPostShared(id.value)))) {
				page.retrospectiveComponent.deleteIdeasPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteIdeasPost: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteIdeasPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void deleteNotWentWellPostRetrospectiveContainerPage(
			custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isNotWentWellPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isNotWentWellPostShared(id.value)))) {
				page.retrospectiveComponent.deleteNotWentWellPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteNotWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteNotWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void deleteWentWellPostRetrospectiveContainerPage(
			custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if (((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isWentWellPostPresent(id.value)))
					&& (!(page.retrospectiveComponent
							.isWentWellPostShared(id.value)))) {
				page.retrospectiveComponent.deleteWentWellPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is shared"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void dislikeIdeasPostRetrospectiveContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if ((((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isIdeasPostPresent(id.value))) && (page.retrospectiveComponent
					.isIdeasPostShared(id.value)))
					&& (page.retrospectiveComponent
							.isIdeasPostEnabled(id.value))) {
				page.retrospectiveComponent.dislikeIdeasPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("dislikeIdeasPost: summary mode is on or post with id " + id.value) + " is not present or it is not shared or it is not enabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"dislikeIdeasPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void dislikeNotWentWellPostRetrospectiveContainerPage(
			custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if ((((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isNotWentWellPostPresent(id.value))) && (page.retrospectiveComponent
					.isNotWentWellPostShared(id.value)))
					&& (page.retrospectiveComponent
							.isNotWentWellPostEnabled(id.value))) {
				page.retrospectiveComponent.dislikeNotWentWellPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("dislikeNotWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is not shared or it is not enabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"dislikeNotWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void dislikeWentWellPostRetrospectiveContainerPage(
			custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if ((((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isWentWellPostPresent(id.value))) && (page.retrospectiveComponent
					.isWentWellPostShared(id.value)))
					&& (page.retrospectiveComponent
							.isWentWellPostEnabled(id.value))) {
				page.retrospectiveComponent.dislikeWentWellPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("dislikeWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is not shared or it is not enabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"dislikeWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void goToHomeRetrospectiveContainerPage() {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			page.navbarComponent.clickHomeLink();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void likeIdeasPostRetrospectiveContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if ((((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isIdeasPostPresent(id.value))) && (page.retrospectiveComponent
					.isIdeasPostShared(id.value)))
					&& (page.retrospectiveComponent
							.isIdeasPostEnabled(id.value))) {
				page.retrospectiveComponent.likeIdeasPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("likeIdeasPost: summary mode is on or post with id " + id.value) + " is not present or it is not shared or it is not enabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"likeIdeasPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void likeNotWentWellPostRetrospectiveContainerPage(
			custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if ((((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isNotWentWellPostPresent(id.value))) && (page.retrospectiveComponent
					.isNotWentWellPostShared(id.value)))
					&& (page.retrospectiveComponent
							.isNotWentWellPostEnabled(id.value))) {
				page.retrospectiveComponent.likeNotWentWellPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("likeNotWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is not shared or it is not enabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"likeNotWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void likeWentWellPostRetrospectiveContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			if ((((!(page.retrospectiveComponent.isSummaryModeOn())) && (page.retrospectiveComponent
					.isWentWellPostPresent(id.value))) && (page.retrospectiveComponent
					.isWentWellPostShared(id.value)))
					&& (page.retrospectiveComponent
							.isWentWellPostEnabled(id.value))) {
				page.retrospectiveComponent.likeWentWellPost(id.value);
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.retrospectiveComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("likeWentWellPost: summary mode is on or post with id " + id.value) + " is not present or it is not shared or it is not enabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"likeWentWellPostRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void openMenuRetrospectiveContainerPage() {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			page.navbarComponent.clickOpenMenu();
			this.currentPage = new po.retrospective.pages.modals.MenuPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"openMenuRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RetrospectiveContainerPage
	public void renameBoardRetrospectiveContainerPage(
			custom_classes.BoardNames newBoardName) {
		if (this.currentPage instanceof po.retrospective.pages.RetrospectiveContainerPage) {
			po.retrospective.pages.RetrospectiveContainerPage page = (po.retrospective.pages.RetrospectiveContainerPage) this.currentPage;
			page.retrospectiveComponent.renameBoard(newBoardName.value());
			this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
					page.retrospectiveComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"renameBoardRetrospectiveContainerPage: expected po.retrospective.pages.RetrospectiveContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: LoginContainerPage
	public void loginLoginContainerPage(custom_classes.PeopleNames peopleName) {
		if (this.currentPage instanceof po.login.pages.LoginContainerPage) {
			po.login.pages.LoginContainerPage page = (po.login.pages.LoginContainerPage) this.currentPage;
			page.loginComponent.typeName(peopleName.name());
			page.loginComponent.clickStart();
			long timeout = 100;
			if (page.loginComponent
					.waitForElementBeingPresentOnPage(
							org.openqa.selenium.By
									.xpath("//button[text()=\"Create a new session\"]"),
							timeout, java.util.concurrent.TimeUnit.MILLISECONDS)) {
				this.currentPage = new po.home.pages.HomeContainerPage(
						page.loginComponent.getDriver());
			} else {
				this.currentPage = new po.retrospective.pages.RetrospectiveContainerPage(
						page.loginComponent.getDriver());
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"loginLoginContainerPage: expected po.login.pages.LoginContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// will be called by es at the end of each test. It is important that it is
	// called quitDriver (search for the string in es client)
	private void quitDriver() {
		driver.quit();
	}
}
