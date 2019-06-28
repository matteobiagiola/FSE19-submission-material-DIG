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

		this.currentPage = new po.home.pages.HomePageContainerPage(driver);
	}

	// PO Name: HomePageContainerPage
	public void createNewEventHomeHomePageContainerPage(
			custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			if (!(page.homeComponent.isEventPresent(tripName.value()))) {
				page.homeComponent.createNewEvent();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.event.pages.AddEditEventContainerPage(
						page.homeComponent.getDriver(), poCallee,
						tripName.value());
			} else {
				throw new NotTheRightInputValuesException(
						(("createNewEventHome: tripName " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createNewEventHomeHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePageContainerPage
	public void createNewEventNavbarHomePageContainerPage(
			custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			if (!(page.homeComponent.isEventPresent(tripName.value()))) {
				page.navbarComponent.createNewEvent();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.event.pages.AddEditEventContainerPage(
						page.navbarComponent.getDriver(), poCallee,
						tripName.value());
			} else {
				throw new NotTheRightInputValuesException(
						(("createNewEventNavbar: tripName " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createNewEventNavbarHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePageContainerPage
	public void deleteEventHomePageContainerPage(
			custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			if (page.homeComponent.isEventPresent(tripName.value())) {
				page.homeComponent.deleteTrip(tripName.value());
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.ConfirmationPage(
						page.homeComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteEvent: tripName " + (tripName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteEventHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePageContainerPage
	public void goToAboutHomePageContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			page.navbarComponent.goToAbout();
			this.currentPage = new po.home.pages.HomePageContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAboutHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePageContainerPage
	public void goToEventsHomePageContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			if (page.navbarComponent.isEventsPresent()) {
				page.navbarComponent.goToEvents();
				this.currentPage = new po.home.pages.HomePageContainerPage(
						page.navbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"goToEvents: events are not present");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEventsHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePageContainerPage
	public void goToFeaturesHomePageContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			page.navbarComponent.goToFeatures();
			this.currentPage = new po.home.pages.HomePageContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToFeaturesHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomePageContainerPage
	public void seeEventDetailsHomePageContainerPage(
			custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po.home.pages.HomePageContainerPage) {
			po.home.pages.HomePageContainerPage page = (po.home.pages.HomePageContainerPage) this.currentPage;
			if (page.homeComponent.isEventPresent(tripName.value())) {
				page.homeComponent.clickOnTrip(tripName.value());
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.homeComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("seeEventDetails: tripName " + (tripName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"seeEventDetailsHomePageContainerPage: expected po.home.pages.HomePageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationPage
	public void cancelConfirmationPage() {
		if (this.currentPage instanceof po.shared.pages.ConfirmationPage) {
			po.shared.pages.ConfirmationPage page = (po.shared.pages.ConfirmationPage) this.currentPage;
			if (page.poCallee.equals(page.addEditEvent)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.event.pages.AddEditEventContainerPage(
						page.getDriver(), page.previousPoCallee, page.tripName);
			} else if (page.poCallee.equals(page.eventDetails)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.home)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.home.pages.HomePageContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.addEditTransaction)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.event.pages.AddEditTransactionContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("confirm: unknown po callee " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"cancelConfirmationPage: expected po.shared.pages.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationPage
	public void confirmConfirmationPage() {
		if (this.currentPage instanceof po.shared.pages.ConfirmationPage) {
			po.shared.pages.ConfirmationPage page = (po.shared.pages.ConfirmationPage) this.currentPage;
			if (page.poCallee.equals(page.addEditEvent)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.home.pages.HomePageContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.eventDetails)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.home)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.home.pages.HomePageContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.addEditTransaction)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("confirm: unknown po callee " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"confirmConfirmationPage: expected po.shared.pages.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void addNewEventEventDetailsContainerPage(
			custom_classes.TripNames tripName) {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			if (!(page.eventDetailsNavbarComponent.isTripPresent(tripName
					.value()))) {
				page.eventDetailsNavbarComponent.addNewEvent();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.event.pages.AddEditEventContainerPage(
						page.eventDetailsComponent.getDriver(),
						tripName.value(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("addNewEvent: trip " + (tripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewEventEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void addTransactionEventDetailsContainerPage() {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			page.eventDetailsComponent.addTransaction();
			this.currentPage = new po.event.pages.modals.QuickAddTransactionPage(
					page.eventDetailsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addTransactionEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void editEventEventDetailsContainerPage(
			custom_classes.TripNames newTripName) {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			if (!(page.eventDetailsNavbarComponent.isTripPresent(newTripName
					.value()))) {
				page.eventDetailsNavbarComponent.clickOnEdit();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.event.pages.AddEditEventContainerPage(
						page.eventDetailsComponent.getDriver(), poCallee,
						newTripName.value());
			} else {
				throw new NotTheRightInputValuesException(
						(("editEvent: new trip name " + (newTripName.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editEventEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void editTransactionEventDetailsContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			if (((page.eventDetailsNavbarComponent.isTransactionsViewActive()) && (page.eventDetailsComponent
					.isTransactionPresent(id.value)))
					&& (page.eventDetailsComponent.isTransaction(id.value))) {
				page.eventDetailsComponent.clickOnTransaction(id.value);
				this.currentPage = new po.event.pages.AddEditTransactionContainerPage(
						page.eventDetailsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("editTransaction: transaction with id " + id.value) + " is not present or it is not a transaction or the transaction view is not active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editTransactionEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void goToHomeEventDetailsContainerPage() {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			page.navbarComponent.goToHomePage();
			this.currentPage = new po.home.pages.HomePageContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void goToOverviewEventDetailsContainerPage() {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			page.eventDetailsNavbarComponent.clickOnOverview();
			this.currentPage = new po.event.pages.EventDetailsContainerPage(
					page.eventDetailsNavbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOverviewEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void goToTransactionEventDetailsContainerPage() {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			page.eventDetailsNavbarComponent.clickOnTransactions();
			this.currentPage = new po.event.pages.EventDetailsContainerPage(
					page.eventDetailsNavbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToTransactionEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void settleUpEventDetailsContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			if ((page.eventDetailsNavbarComponent.isOverviewTabActive())
					&& (page.eventDetailsComponent
							.isSettlementPresent(id.value))) {
				page.eventDetailsComponent.settleUp(id.value);
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.ConfirmationPage(
						page.eventDetailsComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("settleUp: settlement with id " + id.value) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"settleUpEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void shareEventEventDetailsContainerPage() {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			page.eventDetailsNavbarComponent.clickOnShare();
			this.currentPage = new po.event.pages.modals.ShareEventPage(
					page.eventDetailsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"shareEventEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void viewDetailsAsEventDetailsContainerPage(
			custom_classes.Participants participant) {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			if (page.navbarComponent.isParticipantPresent(participant.value())) {
				page.navbarComponent.viewingAs(participant.value());
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.navbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("viewDetailsAs: participant " + (participant.value())) + " is not present or it is the current participant"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"viewDetailsAsEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EventDetailsContainerPage
	public void viewTransferEventDetailsContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.event.pages.EventDetailsContainerPage) {
			po.event.pages.EventDetailsContainerPage page = (po.event.pages.EventDetailsContainerPage) this.currentPage;
			if (((page.eventDetailsNavbarComponent.isTransactionsViewActive()) && (page.eventDetailsComponent
					.isTransactionPresent(id.value)))
					&& (page.eventDetailsComponent
							.isSettlementTransaction(id.value))) {
				page.eventDetailsComponent.clickOnTransaction(id.value);
				this.currentPage = new po.event.pages.ViewTransferContainerPage(
						page.eventDetailsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("viewTransfer: transaction with id " + id.value) + " is not present or it is not a settlement transaction or the transaction view is not active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"viewTransferEventDetailsContainerPage: expected po.event.pages.EventDetailsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditEventContainerPage
	public void addParticipantAddEditEventContainerPage(
			custom_classes.Participants participant) {
		if (this.currentPage instanceof po.event.pages.AddEditEventContainerPage) {
			po.event.pages.AddEditEventContainerPage page = (po.event.pages.AddEditEventContainerPage) this.currentPage;
			if ((page.addEditEventComponent.isEdit())
					&& (!(page.addEditEventComponent
							.isParticipantPresent(participant.value())))) {
				page.addEditEventComponent.addParticipant();
				int index = page.addEditEventComponent
						.getIndexOfEmptyParticipant();
				page.addEditEventComponent.typeParticipant(participant.value(),
						index);
				page.addEditEventComponent.save();
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.addEditEventComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addParticipant: is not edit or participant " + (participant
								.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addParticipantAddEditEventContainerPage: expected po.event.pages.AddEditEventContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditEventContainerPage
	public void cancelAddEditEventContainerPage() {
		if (this.currentPage instanceof po.event.pages.AddEditEventContainerPage) {
			po.event.pages.AddEditEventContainerPage page = (po.event.pages.AddEditEventContainerPage) this.currentPage;
			if (page.poCallee.equals(page.eventDetails)) {
				page.addEditEventComponent.cancel();
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.addEditEventComponent.getDriver());
			} else if (page.poCallee.equals(page.home)) {
				page.addEditEventComponent.cancel();
				this.currentPage = new po.home.pages.HomePageContainerPage(
						page.addEditEventComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("cancel: unknown poCallee " + page.poCallee));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelAddEditEventContainerPage: expected po.event.pages.AddEditEventContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditEventContainerPage
	public void createEventAddEditEventContainerPage(
			custom_classes.Currencies currency,
			custom_classes.Participants participant1,
			custom_classes.Participants participant2) {
		if (this.currentPage instanceof po.event.pages.AddEditEventContainerPage) {
			po.event.pages.AddEditEventContainerPage page = (po.event.pages.AddEditEventContainerPage) this.currentPage;
			if (!(participant1.value().equals(participant2.value()))) {
				page.addEditEventComponent.typeTripName(page.tripName);
				page.addEditEventComponent.selectCurrency(currency.value());
				page.addEditEventComponent.typeParticipant(
						participant1.value(), 0);
				page.addEditEventComponent.typeParticipant(
						participant2.value(), 1);
				page.addEditEventComponent.createOrSave();
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.addEditEventComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((("createEvent: the two participants must be different " + (participant1
								.value())) + " ") + (participant2.value())));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"createEventAddEditEventContainerPage: expected po.event.pages.AddEditEventContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditEventContainerPage
	public void deleteEventAddEditEventContainerPage() {
		if (this.currentPage instanceof po.event.pages.AddEditEventContainerPage) {
			po.event.pages.AddEditEventContainerPage page = (po.event.pages.AddEditEventContainerPage) this.currentPage;
			if (page.addEditEventComponent.isEdit()) {
				page.addEditEventComponent.delete();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.ConfirmationPage(
						page.addEditEventComponent.getDriver(), poCallee,
						page.poCallee, page.tripName);
			} else {
				throw new NotTheRightInputValuesException(
						"deleteEvent: is not edit");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteEventAddEditEventContainerPage: expected po.event.pages.AddEditEventContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditEventContainerPage
	public void deleteParticipantAddEditEventContainerPage(
			custom_classes.Participants participant) {
		if (this.currentPage instanceof po.event.pages.AddEditEventContainerPage) {
			po.event.pages.AddEditEventContainerPage page = (po.event.pages.AddEditEventContainerPage) this.currentPage;
			if (((page.addEditEventComponent.isEdit()) && (page.addEditEventComponent
					.isParticipantPresent(participant.value())))
					&& (page.addEditEventComponent.isButtonEnabled(participant
							.value()))) {
				page.addEditEventComponent.deleteParticipant(participant
						.value());
				page.addEditEventComponent.save();
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.addEditEventComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteParticipant: is not edit or participant " + (participant
								.value())) + " not present or button disabled"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteParticipantAddEditEventContainerPage: expected po.event.pages.AddEditEventContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditEventContainerPage
	public void goToHomePageAddEditEventContainerPage() {
		if (this.currentPage instanceof po.event.pages.AddEditEventContainerPage) {
			po.event.pages.AddEditEventContainerPage page = (po.event.pages.AddEditEventContainerPage) this.currentPage;
			page.navbarComponent.goToHomePage();
			this.currentPage = new po.home.pages.HomePageContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomePageAddEditEventContainerPage: expected po.event.pages.AddEditEventContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ShareEventPage
	public void closeShareEventPage() {
		if (this.currentPage instanceof po.event.pages.modals.ShareEventPage) {
			po.event.pages.modals.ShareEventPage page = (po.event.pages.modals.ShareEventPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po.event.pages.EventDetailsContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeShareEventPage: expected po.event.pages.modals.ShareEventPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: QuickAddTransactionPage
	public void addQuickAddTransactionPage(
			custom_classes.Transactions transaction,
			custom_classes.Price price, custom_classes.Dates date) {
		if (this.currentPage instanceof po.event.pages.modals.QuickAddTransactionPage) {
			po.event.pages.modals.QuickAddTransactionPage page = (po.event.pages.modals.QuickAddTransactionPage) this.currentPage;
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
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"add: failed to locate the input box");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addQuickAddTransactionPage: expected po.event.pages.modals.QuickAddTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: QuickAddTransactionPage
	public void addWithDetailsQuickAddTransactionPage() {
		if (this.currentPage instanceof po.event.pages.modals.QuickAddTransactionPage) {
			po.event.pages.modals.QuickAddTransactionPage page = (po.event.pages.modals.QuickAddTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Add With Details\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po.event.pages.AddEditTransactionContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addWithDetailsQuickAddTransactionPage: expected po.event.pages.modals.QuickAddTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: QuickAddTransactionPage
	public void closeQuickAddTransactionPage() {
		if (this.currentPage instanceof po.event.pages.modals.QuickAddTransactionPage) {
			po.event.pages.modals.QuickAddTransactionPage page = (po.event.pages.modals.QuickAddTransactionPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			page.waitForTimeoutExpires(500);
			this.currentPage = new po.event.pages.EventDetailsContainerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeQuickAddTransactionPage: expected po.event.pages.modals.QuickAddTransactionPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ViewTransferContainerPage
	public void cancelViewTransferContainerPage() {
		if (this.currentPage instanceof po.event.pages.ViewTransferContainerPage) {
			po.event.pages.ViewTransferContainerPage page = (po.event.pages.ViewTransferContainerPage) this.currentPage;
			page.viewTransferComponent.cancel();
			this.currentPage = new po.event.pages.EventDetailsContainerPage(
					page.viewTransferComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelViewTransferContainerPage: expected po.event.pages.ViewTransferContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ViewTransferContainerPage
	public void goToHomeViewTransferContainerPage() {
		if (this.currentPage instanceof po.event.pages.ViewTransferContainerPage) {
			po.event.pages.ViewTransferContainerPage page = (po.event.pages.ViewTransferContainerPage) this.currentPage;
			page.navbarComponent.goToHomePage();
			this.currentPage = new po.home.pages.HomePageContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeViewTransferContainerPage: expected po.event.pages.ViewTransferContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTransactionContainerPage
	public void cancelAddEditTransactionContainerPage() {
		if (this.currentPage instanceof po.event.pages.AddEditTransactionContainerPage) {
			po.event.pages.AddEditTransactionContainerPage page = (po.event.pages.AddEditTransactionContainerPage) this.currentPage;
			page.addEditTransactionComponent.cancel();
			this.currentPage = new po.event.pages.EventDetailsContainerPage(
					page.addEditTransactionComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelAddEditTransactionContainerPage: expected po.event.pages.AddEditTransactionContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTransactionContainerPage
	public void deleteTransactionAddEditTransactionContainerPage() {
		if (this.currentPage instanceof po.event.pages.AddEditTransactionContainerPage) {
			po.event.pages.AddEditTransactionContainerPage page = (po.event.pages.AddEditTransactionContainerPage) this.currentPage;
			if (page.addEditTransactionComponent.isEdit()) {
				page.addEditTransactionComponent.deleteTransaction();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.ConfirmationPage(
						page.addEditTransactionComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						"deleteTransaction: is not edit");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteTransactionAddEditTransactionContainerPage: expected po.event.pages.AddEditTransactionContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTransactionContainerPage
	public void goToHomeAddEditTransactionContainerPage() {
		if (this.currentPage instanceof po.event.pages.AddEditTransactionContainerPage) {
			po.event.pages.AddEditTransactionContainerPage page = (po.event.pages.AddEditTransactionContainerPage) this.currentPage;
			page.navbarComponent.goToHomePage();
			this.currentPage = new po.home.pages.HomePageContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeAddEditTransactionContainerPage: expected po.event.pages.AddEditTransactionContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTransactionContainerPage
	public void shareTransactionAmongAllAddEditTransactionContainerPage(
			custom_classes.Transactions transaction,
			custom_classes.Participants payer, custom_classes.Price price,
			custom_classes.Dates date) {
		if (this.currentPage instanceof po.event.pages.AddEditTransactionContainerPage) {
			po.event.pages.AddEditTransactionContainerPage page = (po.event.pages.AddEditTransactionContainerPage) this.currentPage;
			if (page.addEditTransactionComponent.isParticipantPresent(payer
					.value())) {
				page.addEditTransactionComponent.selectParticipant(payer
						.value());
				page.addEditTransactionComponent
						.typeTransactionName(transaction.value());
				page.addEditTransactionComponent.typeAmount(price.value);
				page.addEditTransactionComponent.pickDateFromCalendar(date);
				page.addEditTransactionComponent.createOrSave();
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.addEditTransactionComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("shareTransactionAmongAll: payer " + (payer.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"shareTransactionAmongAllAddEditTransactionContainerPage: expected po.event.pages.AddEditTransactionContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTransactionContainerPage
	public void shareTransactionExcludingParticipantAddEditTransactionContainerPage(
			custom_classes.Transactions transaction,
			custom_classes.Participants payer, custom_classes.Price price,
			custom_classes.Dates date,
			custom_classes.Participants participantToExclude) {
		if (this.currentPage instanceof po.event.pages.AddEditTransactionContainerPage) {
			po.event.pages.AddEditTransactionContainerPage page = (po.event.pages.AddEditTransactionContainerPage) this.currentPage;
			if ((page.addEditTransactionComponent.isParticipantPresent(payer
					.value()))
					&& (page.addEditTransactionComponent
							.isParticipantPresent(participantToExclude.value()))) {
				page.addEditTransactionComponent.selectParticipant(payer
						.value());
				page.addEditTransactionComponent
						.typeTransactionName(transaction.value());
				page.addEditTransactionComponent.typeAmount(price.value);
				page.addEditTransactionComponent.pickDateFromCalendar(date);
				page.addEditTransactionComponent
						.excludeFromSharing(participantToExclude.value());
				page.addEditTransactionComponent.createOrSave();
				this.currentPage = new po.event.pages.EventDetailsContainerPage(
						page.addEditTransactionComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("shareTransactionExcludingParticipant: payer " + (payer
								.value())) + " is not present or participant to exclude ") + (participantToExclude
								.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"shareTransactionExcludingParticipantAddEditTransactionContainerPage: expected po.event.pages.AddEditTransactionContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// will be called by es at the end of each test. It is important that it is
	// called quitDriver (search for the string in es client)
	private void quitDriver() {
		driver.quit();
	}
}
