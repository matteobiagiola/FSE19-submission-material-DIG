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
		this.currentPage = new po.home.pages.HomeContainerPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTest() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();

		this.currentPage = new po.home.pages.HomeContainerPage(driver);
	}

	// PO Name: HomeContainerPage
	public void goToHomeThroughLogoHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void goToHomeThroughNavbarHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void goToOwnersListHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void goToVeterinariansHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HomeContainerPage
	public void registerNewOwnerHomeContainerPage() {
		if (this.currentPage instanceof po.home.pages.HomeContainerPage) {
			po.home.pages.HomeContainerPage page = (po.home.pages.HomeContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerHomeContainerPage: expected po.home.pages.HomeContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditVisitContainerPage
	public void addVisitAddEditVisitContainerPage(custom_classes.Dates date,
			custom_classes.VisitDescriptions visitDescription) {
		if (this.currentPage instanceof po.pets.pages.AddEditVisitContainerPage) {
			po.pets.pages.AddEditVisitContainerPage page = (po.pets.pages.AddEditVisitContainerPage) this.currentPage;
			page.addEditVisitComponent.addVisit(date, visitDescription);
			this.currentPage = new po.owners.pages.OwnerInformationContainerPage(
					page.addEditVisitComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addVisitAddEditVisitContainerPage: expected po.pets.pages.AddEditVisitContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditVisitContainerPage
	public void goToHomeThroughLogoAddEditVisitContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditVisitContainerPage) {
			po.pets.pages.AddEditVisitContainerPage page = (po.pets.pages.AddEditVisitContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoAddEditVisitContainerPage: expected po.pets.pages.AddEditVisitContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditVisitContainerPage
	public void goToHomeThroughNavbarAddEditVisitContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditVisitContainerPage) {
			po.pets.pages.AddEditVisitContainerPage page = (po.pets.pages.AddEditVisitContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarAddEditVisitContainerPage: expected po.pets.pages.AddEditVisitContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditVisitContainerPage
	public void goToOwnersListAddEditVisitContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditVisitContainerPage) {
			po.pets.pages.AddEditVisitContainerPage page = (po.pets.pages.AddEditVisitContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListAddEditVisitContainerPage: expected po.pets.pages.AddEditVisitContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditVisitContainerPage
	public void goToVeterinariansAddEditVisitContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditVisitContainerPage) {
			po.pets.pages.AddEditVisitContainerPage page = (po.pets.pages.AddEditVisitContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansAddEditVisitContainerPage: expected po.pets.pages.AddEditVisitContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditVisitContainerPage
	public void registerNewOwnerAddEditVisitContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditVisitContainerPage) {
			po.pets.pages.AddEditVisitContainerPage page = (po.pets.pages.AddEditVisitContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerAddEditVisitContainerPage: expected po.pets.pages.AddEditVisitContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void addNewPetAddEditPetContainerPage(
			custom_classes.LongPetNames petName, custom_classes.Dates date,
			custom_classes.PetTypes petType) {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			if ((petName.value().length()) <= 40) {
				page.addEditPetComponent.addNewPet(petName.value(),
						date.value(), petType.value());
				this.currentPage = new po.owners.pages.OwnerInformationContainerPage(
						page.addEditPetComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addNewPet: pet name is too long");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewPetAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void addNewPetWithLongNameAddEditPetContainerPage(
			custom_classes.LongPetNames petName, custom_classes.Dates date,
			custom_classes.PetTypes petType) {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			if ((petName.value().length()) > 40) {
				page.addEditPetComponent.addNewPet(petName.value(),
						date.value(), petType.value());
				this.currentPage = new po.pets.pages.AddEditPetContainerPage(
						page.addEditPetComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addNewPet: pet name is too short");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addNewPetWithLongNameAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void goToHomeThroughLogoAddEditPetContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void goToHomeThroughNavbarAddEditPetContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void goToOwnersListAddEditPetContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void goToVeterinariansAddEditPetContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPetContainerPage
	public void registerNewOwnerAddEditPetContainerPage() {
		if (this.currentPage instanceof po.pets.pages.AddEditPetContainerPage) {
			po.pets.pages.AddEditPetContainerPage page = (po.pets.pages.AddEditPetContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerAddEditPetContainerPage: expected po.pets.pages.AddEditPetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: VeterinariansContainerPage
	public void goToHomeThroughLogoVeterinariansContainerPage() {
		if (this.currentPage instanceof po.veterinarians.pages.VeterinariansContainerPage) {
			po.veterinarians.pages.VeterinariansContainerPage page = (po.veterinarians.pages.VeterinariansContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoVeterinariansContainerPage: expected po.veterinarians.pages.VeterinariansContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: VeterinariansContainerPage
	public void goToHomeThroughNavbarVeterinariansContainerPage() {
		if (this.currentPage instanceof po.veterinarians.pages.VeterinariansContainerPage) {
			po.veterinarians.pages.VeterinariansContainerPage page = (po.veterinarians.pages.VeterinariansContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarVeterinariansContainerPage: expected po.veterinarians.pages.VeterinariansContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: VeterinariansContainerPage
	public void goToOwnersListVeterinariansContainerPage() {
		if (this.currentPage instanceof po.veterinarians.pages.VeterinariansContainerPage) {
			po.veterinarians.pages.VeterinariansContainerPage page = (po.veterinarians.pages.VeterinariansContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListVeterinariansContainerPage: expected po.veterinarians.pages.VeterinariansContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: VeterinariansContainerPage
	public void goToVeterinariansVeterinariansContainerPage() {
		if (this.currentPage instanceof po.veterinarians.pages.VeterinariansContainerPage) {
			po.veterinarians.pages.VeterinariansContainerPage page = (po.veterinarians.pages.VeterinariansContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansVeterinariansContainerPage: expected po.veterinarians.pages.VeterinariansContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: VeterinariansContainerPage
	public void registerNewOwnerVeterinariansContainerPage() {
		if (this.currentPage instanceof po.veterinarians.pages.VeterinariansContainerPage) {
			po.veterinarians.pages.VeterinariansContainerPage page = (po.veterinarians.pages.VeterinariansContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerVeterinariansContainerPage: expected po.veterinarians.pages.VeterinariansContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToAddNewPetOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.ownerInformationComponent.goToAddNewPet();
			this.currentPage = new po.pets.pages.AddEditPetContainerPage(
					page.ownerInformationComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAddNewPetOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToEditOwnerOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.ownerInformationComponent.goToEditOwner();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.ownerInformationComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditOwnerOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToEditPetThroghEditLinkOwnerInformationContainerPage(
			custom_classes.PetNames petName) {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			if (page.ownerInformationComponent.petExists(petName)) {
				page.ownerInformationComponent
						.goToEditPetThroghEditLink(petName);
				this.currentPage = new po.pets.pages.AddEditPetContainerPage(
						page.ownerInformationComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditPetThroghEditLink: pet name " + (petName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditPetThroghEditLinkOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToEditPetThroughNameOwnerInformationContainerPage(
			custom_classes.PetNames petName) {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			if (page.ownerInformationComponent.petExists(petName)) {
				page.ownerInformationComponent.goToEditPetThroughName(petName);
				this.currentPage = new po.pets.pages.AddEditPetContainerPage(
						page.ownerInformationComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToEditPetThroughName: pet name " + (petName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditPetThroughNameOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToHomeThroughLogoOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToHomeThroughNavbarOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToOwnersListOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToVeterinariansOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void goToVisitsOwnerInformationContainerPage(
			custom_classes.PetNames petName) {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			if (page.ownerInformationComponent.petExists(petName)) {
				page.ownerInformationComponent.goToVisits(petName);
				this.currentPage = new po.pets.pages.AddEditVisitContainerPage(
						page.ownerInformationComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToVisits: pet name " + (petName.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVisitsOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnerInformationContainerPage
	public void registerNewOwnerOwnerInformationContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnerInformationContainerPage) {
			po.owners.pages.OwnerInformationContainerPage page = (po.owners.pages.OwnerInformationContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerOwnerInformationContainerPage: expected po.owners.pages.OwnerInformationContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void goToHomeThroughLogoAddEditOwnerContainerPage() {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void goToHomeThroughNavbarAddEditOwnerContainerPage() {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void goToOwnersListAddEditOwnerContainerPage() {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void goToVeterinariansAddEditOwnerContainerPage() {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void registerNewOwnerAddEditOwnerContainerPage() {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void registerOwnerAddEditOwnerContainerPage(
			custom_classes.LongFirstNames firstName,
			custom_classes.LastNames lastName,
			custom_classes.Addresses address, custom_classes.Cities city,
			custom_classes.LongTelephones telephone) {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			if (((telephone.value().length()) <= 10)
					&& ((firstName.value().length()) < 40)) {
				page.addEditOwnerComponent.registerOwner(firstName.value(),
						lastName.value(), address.value(), city.value(),
						telephone.value());
				this.currentPage = new po.owners.pages.OwnersListContainerPage(
						page.addEditOwnerComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"registerOwner: either telephone number or first name is too long");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"registerOwnerAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditOwnerContainerPage
	public void registerOwnerWithLongFirstNameAddEditOwnerContainerPage(
			custom_classes.LongFirstNames firstName,
			custom_classes.LastNames lastName,
			custom_classes.Addresses address, custom_classes.Cities city,
			custom_classes.LongTelephones telephone) {
		if (this.currentPage instanceof po.owners.pages.AddEditOwnerContainerPage) {
			po.owners.pages.AddEditOwnerContainerPage page = (po.owners.pages.AddEditOwnerContainerPage) this.currentPage;
			if (((firstName.value().length()) > 40)
					&& ((telephone.value().length()) <= 10)) {
				page.addEditOwnerComponent.registerOwner(firstName.value(),
						lastName.value(), address.value(), city.value(),
						telephone.value());
				this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
						page.addEditOwnerComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"registerOwnerWithLongFirstName: first name too short or telephone number is too long");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"registerOwnerWithLongFirstNameAddEditOwnerContainerPage: expected po.owners.pages.AddEditOwnerContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void goToHomeThroughLogoOwnersListContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			page.navbarComponent.clickOnLogo();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughLogoOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void goToHomeThroughNavbarOwnersListContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			page.navbarComponent.clickOnHome();
			this.currentPage = new po.home.pages.HomeContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeThroughNavbarOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void goToOwnerInformationOwnersListContainerPage(
			custom_classes.FirstNames firstName,
			custom_classes.LastNames lastName) {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			if (page.ownersListComponent.isOwnerPresent(firstName, lastName)) {
				page.ownersListComponent.goToOwnerInformation(firstName,
						lastName);
				this.currentPage = new po.owners.pages.OwnerInformationContainerPage(
						page.ownersListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("goToOwnerInformation: owner " + (firstName.value())) + " ") + (lastName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnerInformationOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void goToOwnersListOwnersListContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			page.navbarComponent.clickOnAll();
			this.currentPage = new po.owners.pages.OwnersListContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToOwnersListOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void goToVeterinariansOwnersListContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			page.navbarComponent.clickOnVets();
			this.currentPage = new po.veterinarians.pages.VeterinariansContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVeterinariansOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void registerNewOwnerOwnersListContainerPage() {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			page.navbarComponent.clickOnRegister();
			this.currentPage = new po.owners.pages.AddEditOwnerContainerPage(
					page.navbarComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"registerNewOwnerOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: OwnersListContainerPage
	public void searchFilterOwnersListContainerPage(
			custom_classes.FirstNames firstName,
			custom_classes.LastNames lastName) {
		if (this.currentPage instanceof po.owners.pages.OwnersListContainerPage) {
			po.owners.pages.OwnersListContainerPage page = (po.owners.pages.OwnersListContainerPage) this.currentPage;
			if (page.ownersListComponent.isOwnerPresent(firstName, lastName)) {
				page.ownersListComponent.searchFilter(firstName);
				this.currentPage = new po.owners.pages.OwnersListContainerPage(
						page.ownersListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("searchFilter: owner " + (firstName.value())) + " ") + (lastName
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"searchFilterOwnersListContainerPage: expected po.owners.pages.OwnersListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
