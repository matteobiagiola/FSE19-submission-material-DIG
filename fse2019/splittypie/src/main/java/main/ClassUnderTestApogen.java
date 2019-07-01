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
		this.currentPage = new po_apogen.IndexPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTestApogen() {
		// start driver and browser
		driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po_apogen.IndexPage(driver);
	}

	// PO Name: ConfirmationPage
	public void noConfirmationPage() {
		if (this.currentPage instanceof po_apogen.ConfirmationPage) {
			po_apogen.ConfirmationPage page = (po_apogen.ConfirmationPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.IndexPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"noConfirmationPage: expected po_apogen.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationPage
	public void yesConfirmationPage() {
		if (this.currentPage instanceof po_apogen.ConfirmationPage) {
			po_apogen.ConfirmationPage page = (po_apogen.ConfirmationPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.IndexPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"yesConfirmationPage: expected po_apogen.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToEditEventHomePage(custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			if (!(page.homeComponent.isTripPresent(tripName.value()))) {
				page.homeComponent.goToEditEvent();
				this.currentPage = new po_apogen.EditEventPage(
						page.homeComponent.getDriver(), tripName);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditEvent: new trip name " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditEventHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToHomePageHomePage() {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			page.homeComponent.goToHomePage();
			this.currentPage = new po_apogen.HomePage(
					page.homeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomePageHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToIndexPageHomePage() {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			page.homeComponent.goToIndexPage();
			this.currentPage = new po_apogen.IndexPage(
					page.homeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexPageHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePage
	public void goToTransactionsHomePage() {
		if (this.currentPage instanceof po_apogen.HomePage) {
			po_apogen.HomePage page = (po_apogen.HomePage) this.currentPage;
			page.homeComponent.goToTransactions();
			this.currentPage = new po_apogen.TransactionsPage(
					page.homeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToTransactionsHomePage: expected po_apogen.HomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewTransactionPage
	public void cancelNewTransactionPage() {
		if (this.currentPage instanceof po_apogen.NewTransactionPage) {
			po_apogen.NewTransactionPage page = (po_apogen.NewTransactionPage) this.currentPage;
			page.newTransactionComponent.cancel();
			this.currentPage = new po_apogen.HomePage(
					page.newTransactionComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelNewTransactionPage: expected po_apogen.NewTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewTransactionPage
	public void formNewTransactionPage(custom_classes.Transactions transaction,
			custom_classes.Participants payer, custom_classes.Price price,
			custom_classes.Dates date) {
		if (this.currentPage instanceof po_apogen.NewTransactionPage) {
			po_apogen.NewTransactionPage page = (po_apogen.NewTransactionPage) this.currentPage;
			if (page.newTransactionComponent
					.isParticipantPresent(payer.value())) {
				page.newTransactionComponent.form(transaction, payer, price,
						date);
				this.currentPage = new po_apogen.HomePage(
						page.newTransactionComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("form: payer " + (payer.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"formNewTransactionPage: expected po_apogen.NewTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditTransactionPage
	public void cancelEditTransactionPage() {
		if (this.currentPage instanceof po_apogen.EditTransactionPage) {
			po_apogen.EditTransactionPage page = (po_apogen.EditTransactionPage) this.currentPage;
			page.editTransactionComponent.cancel();
			this.currentPage = new po_apogen.HomePage(
					page.editTransactionComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelEditTransactionPage: expected po_apogen.EditTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditTransactionPage
	public void deleteEditTransactionPage() {
		if (this.currentPage instanceof po_apogen.EditTransactionPage) {
			po_apogen.EditTransactionPage page = (po_apogen.EditTransactionPage) this.currentPage;
			if (page.editTransactionComponent.isEdit()) {
				page.editTransactionComponent.delete();
				this.currentPage = new po_apogen.ConfirmationTransactionPage(
						page.editTransactionComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"deleteTransaction: is not edit");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteEditTransactionPage: expected po_apogen.EditTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditTransactionPage
	public void formEditTransactionPage(
			custom_classes.Transactions transaction,
			custom_classes.Participants payer, custom_classes.Price price,
			custom_classes.Dates date) {
		if (this.currentPage instanceof po_apogen.EditTransactionPage) {
			po_apogen.EditTransactionPage page = (po_apogen.EditTransactionPage) this.currentPage;
			if (page.editTransactionComponent.isParticipantPresent(payer
					.value())) {
				page.editTransactionComponent.form(transaction, payer, price,
						date);
				this.currentPage = new po_apogen.HomePage(
						page.editTransactionComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("form: payer " + (payer.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"formEditTransactionPage: expected po_apogen.EditTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationTransactionPage
	public void closeConfirmationTransactionPage() {
		if (this.currentPage instanceof po_apogen.ConfirmationTransactionPage) {
			po_apogen.ConfirmationTransactionPage page = (po_apogen.ConfirmationTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.EditTransactionPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeConfirmationTransactionPage: expected po_apogen.ConfirmationTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationTransactionPage
	public void noConfirmationTransactionPage() {
		if (this.currentPage instanceof po_apogen.ConfirmationTransactionPage) {
			po_apogen.ConfirmationTransactionPage page = (po_apogen.ConfirmationTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.EditTransactionPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"noConfirmationTransactionPage: expected po_apogen.ConfirmationTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationTransactionPage
	public void yesConfirmationTransactionPage() {
		if (this.currentPage instanceof po_apogen.ConfirmationTransactionPage) {
			po_apogen.ConfirmationTransactionPage page = (po_apogen.ConfirmationTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.HomePage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"yesConfirmationTransactionPage: expected po_apogen.ConfirmationTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmDeletionPage
	public void noConfirmDeletionPage() {
		if (this.currentPage instanceof po_apogen.ConfirmDeletionPage) {
			po_apogen.ConfirmDeletionPage page = (po_apogen.ConfirmDeletionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.EditEventPage(page.getDriver(),
					page.tripName);
		} else {
			throw new NotInTheRightPageObjectException(
					"noConfirmDeletionPage: expected po_apogen.ConfirmDeletionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmDeletionPage
	public void yesConfirmDeletionPage() {
		if (this.currentPage instanceof po_apogen.ConfirmDeletionPage) {
			po_apogen.ConfirmDeletionPage page = (po_apogen.ConfirmDeletionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.IndexPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"yesConfirmDeletionPage: expected po_apogen.ConfirmDeletionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditEventPage
	public void cancelEditEventPage() {
		if (this.currentPage instanceof po_apogen.EditEventPage) {
			po_apogen.EditEventPage page = (po_apogen.EditEventPage) this.currentPage;
			page.editEventComponent.cancel();
			this.currentPage = new po_apogen.HomePage(
					page.editEventComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelEditEventPage: expected po_apogen.EditEventPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditEventPage
	public void formEditEventPage(custom_classes.Currencies currency,
			custom_classes.Participants participant1,
			custom_classes.Participants participant2) {
		if (this.currentPage instanceof po_apogen.EditEventPage) {
			po_apogen.EditEventPage page = (po_apogen.EditEventPage) this.currentPage;
			if (!(participant1.value().equals(participant2.value()))) {
				page.editEventComponent.form(page.tripName, currency,
						participant1, participant2);
				this.currentPage = new po_apogen.HomePage(
						page.editEventComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((("form: the two participants must be different " + (participant1
								.value())) + " ") + (participant2.value())));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"formEditEventPage: expected po_apogen.EditEventPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditEventPage
	public void goToConfirmDeletionPageEditEventPage() {
		if (this.currentPage instanceof po_apogen.EditEventPage) {
			po_apogen.EditEventPage page = (po_apogen.EditEventPage) this.currentPage;
			if (page.editEventComponent.isEdit()) {
				page.editEventComponent.goToConfirmDeletionPage();
				this.currentPage = new po_apogen.ConfirmDeletionPage(
						page.editEventComponent.getDriver(), page.tripName);
			} else {
				throw new NotTheRightInputValuesException(
						"deleteEvent: is not edit");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToConfirmDeletionPageEditEventPage: expected po_apogen.EditEventPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewEventPage
	public void cancelNewEventPage() {
		if (this.currentPage instanceof po_apogen.NewEventPage) {
			po_apogen.NewEventPage page = (po_apogen.NewEventPage) this.currentPage;
			page.newEventComponent.cancel();
			this.currentPage = new po_apogen.IndexPage(
					page.newEventComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelNewEventPage: expected po_apogen.NewEventPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewEventPage
	public void formNewEventPage(custom_classes.Currencies currency,
			custom_classes.Participants participant1,
			custom_classes.Participants participant2) {
		if (this.currentPage instanceof po_apogen.NewEventPage) {
			po_apogen.NewEventPage page = (po_apogen.NewEventPage) this.currentPage;
			if (!(participant1.value().equals(participant2.value()))) {
				page.newEventComponent.form(page.tripName, currency,
						participant1, participant2);
				this.currentPage = new po_apogen.HomePage(
						page.newEventComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((("form: the two participants must be different " + (participant1
								.value())) + " ") + (participant2.value())));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"formNewEventPage: expected po_apogen.NewEventPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToConfirmationIndexPage(custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			if (page.indexComponent.isEventPresent(tripName.value())) {
				page.indexComponent.goToConfirmation(tripName);
				this.currentPage = new po_apogen.ConfirmationPage(
						page.indexComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToConfirmation: tripName " + (tripName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToConfirmationIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToHomePageIndexPage(custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			if (page.indexComponent.isEventPresent(tripName.value())) {
				page.indexComponent.goToHomePage(tripName);
				this.currentPage = new po_apogen.HomePage(
						page.indexComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToHomePage: tripName " + (tripName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomePageIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToNewEventHomeIndexPage(custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			if (!(page.indexComponent.isEventPresent(tripName.value()))) {
				page.indexComponent.goToNewEventHome();
				this.currentPage = new po_apogen.NewEventPage(
						page.indexComponent.getDriver(), tripName);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToNewEventHome: tripName " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToNewEventHomeIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToNewEventNavbarIndexPage(custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			if (!(page.indexComponent.isEventPresent(tripName.value()))) {
				page.indexComponent.goToNewEventNavbar();
				this.currentPage = new po_apogen.NewEventPage(
						page.indexComponent.getDriver(), tripName);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToNewEventNavbar: tripName " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToNewEventNavbarIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void goToAddTransactionPageTransactionsPage() {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			page.transactionsComponent.goToAddTransactionPage();
			this.currentPage = new po_apogen.AddTransactionPage(
					page.transactionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAddTransactionPageTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void goToEditEventTransactionsPage(custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			if (!(page.transactionsComponent.isTripPresent(tripName.value()))) {
				page.transactionsComponent.goToEditEvent();
				this.currentPage = new po_apogen.EditEventPage(
						page.transactionsComponent.getDriver(), tripName);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditEvent: new trip name " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditEventTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void goToEditTransactionPageTransactionsPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			if (((page.transactionsComponent.isTransactionsViewActive()) && (page.transactionsComponent
					.isTransactionPresent(id.value)))
					&& (page.transactionsComponent.isTransaction(id.value))) {
				page.transactionsComponent.clickOnTransaction(id.value);
				this.currentPage = new po_apogen.EditTransactionPage(
						page.transactionsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditTransactionPage: transaction with id " + id.value) + " is not present or it is not a transaction or the transaction view is not active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditTransactionPageTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddTransactionPage
	public void addWithDetailsAddTransactionPage() {
		if (this.currentPage instanceof po_apogen.AddTransactionPage) {
			po_apogen.AddTransactionPage page = (po_apogen.AddTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Add With Details\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.NewTransactionPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addWithDetailsAddTransactionPage: expected po_apogen.AddTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddTransactionPage
	public void closeAddTransactionPage() {
		if (this.currentPage instanceof po_apogen.AddTransactionPage) {
			po_apogen.AddTransactionPage page = (po_apogen.AddTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po_apogen.TransactionsPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeAddTransactionPage: expected po_apogen.AddTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddTransactionPage
	public void quickAddAddTransactionPage(
			custom_classes.Transactions transaction,
			custom_classes.Price price, custom_classes.Dates date) {
		if (this.currentPage instanceof po_apogen.AddTransactionPage) {
			po_apogen.AddTransactionPage page = (po_apogen.AddTransactionPage) this.currentPage;
			java.lang.String input = ((((date.value()) + " ") + (price.value)) + " ")
					+ (transaction.value());
			if (page.waitForElementBeingVisibleOnPage(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-body pull-up-30\"]//input[@placeholder=\"Example: 10 tickets\"]"))) {
				page.type(
						org.openqa.selenium.By
								.xpath("//div[@class=\"modal-body pull-up-30\"]//input[@placeholder=\"Example: 10 tickets\"]"),
						input);
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Add\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po_apogen.HomePage(page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"add: failed to locate the input box");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"quickAddAddTransactionPage: expected po_apogen.AddTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// will be called by es at the end of each test. It is important that it is
	// called quitDriver (search for the string in es client)
	private void quitDriver() {
		driver.quit();
	}
}
