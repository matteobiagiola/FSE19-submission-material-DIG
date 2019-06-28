package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;

public class ClassUnderTestApogen {

	private Object currentPage = null;

	// BOOTSTRAP POINT
	public ClassUnderTestApogen() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po_apogen.IndexPage(driver);
	}

	// PO Name: VetsPage
	public void goToIndexVetsPage() {
		if (this.currentPage instanceof po_apogen.VetsPage) {
			po_apogen.VetsPage page = (po_apogen.VetsPage) this.currentPage;
			page.vetsComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.vetsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexVetsPage: expected po_apogen.VetsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewPetPage
	public void submitNewPetPage(custom_classes.PetNames petName,
			custom_classes.Dates date, custom_classes.PetTypes petType) {
		if (this.currentPage instanceof po_apogen.NewPetPage) {
			po_apogen.NewPetPage page = (po_apogen.NewPetPage) this.currentPage;
			page.newPetComponent.submit(petName, date, petType);
			this.currentPage = new po_apogen.OwnerInformationPage(
					page.newPetComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"submitNewPetPage: expected po_apogen.NewPetPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: VisitsPage
	public void formVisitsPage(custom_classes.Dates date,
			custom_classes.VisitDescriptions visitDescription) {
		if (this.currentPage instanceof po_apogen.VisitsPage) {
			po_apogen.VisitsPage page = (po_apogen.VisitsPage) this.currentPage;
			page.visitsComponent.form(date, visitDescription);
			this.currentPage = new po_apogen.OwnerInformationPage(
					page.visitsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"formVisitsPage: expected po_apogen.VisitsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersPage
	public void goToOwnerInformationOwnersPage(
			custom_classes.FirstNames firstName,
			custom_classes.LastNames lastName) {
		if (this.currentPage instanceof po_apogen.OwnersPage) {
			po_apogen.OwnersPage page = (po_apogen.OwnersPage) this.currentPage;
			if (page.ownersComponent.isOwnerPresent(firstName, lastName)) {
				page.ownersComponent.goToOwnerInformation(firstName, lastName);
				this.currentPage = new po_apogen.OwnerInformationPage(
						page.ownersComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("goToOwnerInformation: owner " + (firstName.value())) + " ") + (lastName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnerInformationOwnersPage: expected po_apogen.OwnersPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersPage
	public void searchFilterOwnersPage(custom_classes.FirstNames firstName,
			custom_classes.LastNames lastName) {
		if (this.currentPage instanceof po_apogen.OwnersPage) {
			po_apogen.OwnersPage page = (po_apogen.OwnersPage) this.currentPage;
			if (page.ownersComponent.isOwnerPresent(firstName, lastName)) {
				page.ownersComponent.searchFilter(firstName);
				this.currentPage = new po_apogen.OwnersPage(
						page.ownersComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("searchFilter: owner " + (firstName.value())) + " ") + (lastName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"searchFilterOwnersPage: expected po_apogen.OwnersPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationPage
	public void goToAddNewPetOwnerInformationPage() {
		if (this.currentPage instanceof po_apogen.OwnerInformationPage) {
			po_apogen.OwnerInformationPage page = (po_apogen.OwnerInformationPage) this.currentPage;
			page.ownerInformationComponent.goToAddNewPet();
			this.currentPage = new po_apogen.NewPetPage(
					page.ownerInformationComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAddNewPetOwnerInformationPage: expected po_apogen.OwnerInformationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationPage
	public void goToEditOwnerOwnerInformationPage() {
		if (this.currentPage instanceof po_apogen.OwnerInformationPage) {
			po_apogen.OwnerInformationPage page = (po_apogen.OwnerInformationPage) this.currentPage;
			page.ownerInformationComponent.goToEditOwner();
			this.currentPage = new po_apogen.EditOwnerPage(
					page.ownerInformationComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditOwnerOwnerInformationPage: expected po_apogen.OwnerInformationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationPage
	public void goToEditPetThroghEditLinkOwnerInformationPage(
			custom_classes.PetNames petName) {
		if (this.currentPage instanceof po_apogen.OwnerInformationPage) {
			po_apogen.OwnerInformationPage page = (po_apogen.OwnerInformationPage) this.currentPage;
			if (page.ownerInformationComponent.petExists(petName)) {
				page.ownerInformationComponent
						.goToEditPetThroghEditLink(petName);
				this.currentPage = new po_apogen.NewPetPage(
						page.ownerInformationComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditPetThroghEditLink: pet name " + (petName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditPetThroghEditLinkOwnerInformationPage: expected po_apogen.OwnerInformationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationPage
	public void goToEditPetThroughNameOwnerInformationPage(
			custom_classes.PetNames petName) {
		if (this.currentPage instanceof po_apogen.OwnerInformationPage) {
			po_apogen.OwnerInformationPage page = (po_apogen.OwnerInformationPage) this.currentPage;
			if (page.ownerInformationComponent.petExists(petName)) {
				page.ownerInformationComponent.goToEditPetThroughName(petName);
				this.currentPage = new po_apogen.NewPetPage(
						page.ownerInformationComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditPetThroughName: pet name " + (petName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditPetThroughNameOwnerInformationPage: expected po_apogen.OwnerInformationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationPage
	public void goToIndexOwnerInformationPage() {
		if (this.currentPage instanceof po_apogen.OwnerInformationPage) {
			po_apogen.OwnerInformationPage page = (po_apogen.OwnerInformationPage) this.currentPage;
			page.ownerInformationComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.ownerInformationComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexOwnerInformationPage: expected po_apogen.OwnerInformationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationPage
	public void goToVisitsOwnerInformationPage(custom_classes.PetNames petName) {
		if (this.currentPage instanceof po_apogen.OwnerInformationPage) {
			po_apogen.OwnerInformationPage page = (po_apogen.OwnerInformationPage) this.currentPage;
			if (page.ownerInformationComponent.petExists(petName)) {
				page.ownerInformationComponent.goToVisits(petName);
				this.currentPage = new po_apogen.VisitsPage(
						page.ownerInformationComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToVisits: pet name " + (petName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVisitsOwnerInformationPage: expected po_apogen.OwnerInformationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToNewOwnerIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToNewOwner();
			this.currentPage = new po_apogen.NewOwnerPage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToNewOwnerIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToOwnersIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToOwners();
			this.currentPage = new po_apogen.OwnersPage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToVetsIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToVets();
			this.currentPage = new po_apogen.VetsPage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVetsIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: IndexPage
	public void goToWelcomeIndexPage() {
		if (this.currentPage instanceof po_apogen.IndexPage) {
			po_apogen.IndexPage page = (po_apogen.IndexPage) this.currentPage;
			page.indexComponent.goToWelcome();
			this.currentPage = new po_apogen.WelcomePage(
					page.indexComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWelcomeIndexPage: expected po_apogen.IndexPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: NewOwnerPage
	public void submitOwnerFormNewOwnerPage(
			custom_classes.FirstNames firstName,
			custom_classes.LastNames lastName,
			custom_classes.Addresses address, custom_classes.Cities city,
			custom_classes.Telephones telephone) {
		if (this.currentPage instanceof po_apogen.NewOwnerPage) {
			po_apogen.NewOwnerPage page = (po_apogen.NewOwnerPage) this.currentPage;
			page.newOwnerComponent.submitOwnerForm(firstName, lastName,
					address, city, telephone);
			this.currentPage = new po_apogen.OwnersPage(
					page.newOwnerComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"submitOwnerFormNewOwnerPage: expected po_apogen.NewOwnerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditOwnerPage
	public void editOwnerEditOwnerPage(custom_classes.FirstNames firstName,
			custom_classes.LastNames lastName,
			custom_classes.Addresses address, custom_classes.Cities city,
			custom_classes.Telephones telephone) {
		if (this.currentPage instanceof po_apogen.EditOwnerPage) {
			po_apogen.EditOwnerPage page = (po_apogen.EditOwnerPage) this.currentPage;
			page.editOwnerComponent.editOwner(firstName, lastName, address,
					city, telephone);
			this.currentPage = new po_apogen.OwnerInformationPage(
					page.editOwnerComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"editOwnerEditOwnerPage: expected po_apogen.EditOwnerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WelcomePage
	public void goToIndexWelcomePage() {
		if (this.currentPage instanceof po_apogen.WelcomePage) {
			po_apogen.WelcomePage page = (po_apogen.WelcomePage) this.currentPage;
			page.welcomeComponent.goToIndex();
			this.currentPage = new po_apogen.IndexPage(
					page.welcomeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToIndexWelcomePage: expected po_apogen.WelcomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WelcomePage
	public void goToNewOwnerWelcomePage() {
		if (this.currentPage instanceof po_apogen.WelcomePage) {
			po_apogen.WelcomePage page = (po_apogen.WelcomePage) this.currentPage;
			page.welcomeComponent.goToNewOwner();
			this.currentPage = new po_apogen.NewOwnerPage(
					page.welcomeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToNewOwnerWelcomePage: expected po_apogen.WelcomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WelcomePage
	public void goToOwnersWelcomePage() {
		if (this.currentPage instanceof po_apogen.WelcomePage) {
			po_apogen.WelcomePage page = (po_apogen.WelcomePage) this.currentPage;
			page.welcomeComponent.goToOwners();
			this.currentPage = new po_apogen.OwnersPage(
					page.welcomeComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersWelcomePage: expected po_apogen.WelcomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
