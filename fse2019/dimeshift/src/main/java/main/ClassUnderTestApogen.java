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
		po.shared.components.NavbarComponent navbarComponent = new po.shared.components.NavbarComponent(
				driver);
		navbarComponent.goToRegisterPage();
		po.home.pages.RegisterPage registerPage = new po.home.pages.RegisterPage(
				driver);
		registerPage.register(Username.ASD, Email.ASD, Password.ASD);
		this.currentPage = new po_apogen.WalletPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTestApogen() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();
		po.shared.components.NavbarComponent navbarComponent = new po.shared.components.NavbarComponent(
				driver);
		navbarComponent.goToRegisterPage();
		po.home.pages.RegisterPage registerPage = new po.home.pages.RegisterPage(
				driver);
		registerPage.register(Username.ASD, Email.ASD, Password.ASD);
		this.currentPage = new po_apogen.WalletPage(driver);
	}

	// PO Name: AddIncomePage
	public void addIncomeAddIncomePage(
			custom_classes.IncomeDescription description,
			custom_classes.Amount amount) {
		if (this.currentPage instanceof po_apogen.AddIncomePage) {
			po_apogen.AddIncomePage page = (po_apogen.AddIncomePage) this.currentPage;
			if (page.isElementPresentOnPage(org.openqa.selenium.By
					.id("input_amount"))) {
				page.typeJS(org.openqa.selenium.By.id("input_amount"),
						java.lang.String.valueOf(amount.value));
				page.typeJS(org.openqa.selenium.By.id("input_description"),
						description.value());
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
				this.currentPage = new po_apogen.TransactionsPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add income"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addIncomeAddIncomePage: expected po_apogen.AddIncomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddIncomePage
	public void goToTransactionsAddIncomePage() {
		if (this.currentPage instanceof po_apogen.AddIncomePage) {
			po_apogen.AddIncomePage page = (po_apogen.AddIncomePage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			this.currentPage = new po_apogen.TransactionsPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToTransactionsAddIncomePage: expected po_apogen.AddIncomePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanPage
	public void editGoalCreatePlanPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.CreatePlanPage) {
			po_apogen.CreatePlanPage page = (po_apogen.CreatePlanPage) this.currentPage;
			if (page.createPlanComponent.planExists(id)) {
				page.createPlanComponent.editPlan(id);
				this.currentPage = new po_apogen.CreatePlanBasicSettingsPage(
						page.createPlanComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": plan with id ") + id.value) + " doesn't exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editGoalCreatePlanPage: expected po_apogen.CreatePlanPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanPage
	public void goToCreateNewPlanPageCreatePlanPage() {
		if (this.currentPage instanceof po_apogen.CreatePlanPage) {
			po_apogen.CreatePlanPage page = (po_apogen.CreatePlanPage) this.currentPage;
			page.createPlanComponent.goToPlanBasicSettings();
			this.currentPage = new po_apogen.CreatePlanBasicSettingsPage(
					page.createPlanComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToCreateNewPlanPageCreatePlanPage: expected po_apogen.CreatePlanPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanPage
	public void viewPlanPageCreatePlanPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.CreatePlanPage) {
			po_apogen.CreatePlanPage page = (po_apogen.CreatePlanPage) this.currentPage;
			if (page.createPlanComponent.planExists(id)) {
				page.createPlanComponent.viewPlanReport(id);
				this.currentPage = new po_apogen.ViewPlanPage(
						page.createPlanComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": plan with id ") + id.value) + " doesn't exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"viewPlanPageCreatePlanPage: expected po_apogen.CreatePlanPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TrashPage
	public void goToWalletPageTrashPage() {
		if (this.currentPage instanceof po_apogen.TrashPage) {
			po_apogen.TrashPage page = (po_apogen.TrashPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[2]/a[1]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletPageTrashPage: expected po_apogen.TrashPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AccessEmailPage
	public void addAccessAccessEmailPage(custom_classes.Email email) {
		if (this.currentPage instanceof po_apogen.AccessEmailPage) {
			po_apogen.AccessEmailPage page = (po_apogen.AccessEmailPage) this.currentPage;
			page.typeJS(
					org.openqa.selenium.By
							.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_email\"]"),
					email.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@type=\"submit\"]"));
			this.currentPage = new po_apogen.AccessEmailPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addAccessAccessEmailPage: expected po_apogen.AccessEmailPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AccessEmailPage
	public void closeAccessEmailPage() {
		if (this.currentPage instanceof po_apogen.AccessEmailPage) {
			po_apogen.AccessEmailPage page = (po_apogen.AccessEmailPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeAccessEmailPage: expected po_apogen.AccessEmailPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AccessEmailPage
	public void removeAccessAccessEmailPage(custom_classes.Email email) {
		if (this.currentPage instanceof po_apogen.AccessEmailPage) {
			po_apogen.AccessEmailPage page = (po_apogen.AccessEmailPage) this.currentPage;
			int indexElement = page.associatedEmailExist(email);
			if (indexElement != (-1)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((("(//div[@class=\"modal-body modal-body-default\"]/div[@class=\"table-responsive\"]//a)[" + (indexElement + 1)) + "]")));
				this.currentPage = new po_apogen.RemoveEmailAccessPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": email ") + (email
								.value())) + " has not been associated with any wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"removeAccessAccessEmailPage: expected po_apogen.AccessEmailPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HideWalletPage
	public void cancelHideWalletPage() {
		if (this.currentPage instanceof po_apogen.HideWalletPage) {
			po_apogen.HideWalletPage page = (po_apogen.HideWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@class=\"btn btn-primary pull-left\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelHideWalletPage: expected po_apogen.HideWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HideWalletPage
	public void goToWalletPageHideWalletPage() {
		if (this.currentPage instanceof po_apogen.HideWalletPage) {
			po_apogen.HideWalletPage page = (po_apogen.HideWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletPageHideWalletPage: expected po_apogen.HideWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: HideWalletPage
	public void hideHideWalletPage() {
		if (this.currentPage instanceof po_apogen.HideWalletPage) {
			po_apogen.HideWalletPage page = (po_apogen.HideWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"hideHideWalletPage: expected po_apogen.HideWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RemoveEmailAccessPage
	public void cancelRemoveEmailAccessPage() {
		if (this.currentPage instanceof po_apogen.RemoveEmailAccessPage) {
			po_apogen.RemoveEmailAccessPage page = (po_apogen.RemoveEmailAccessPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@class=\"btn btn-primary pull-left\"]"));
			this.currentPage = new po_apogen.AccessEmailPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelRemoveEmailAccessPage: expected po_apogen.RemoveEmailAccessPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RemoveEmailAccessPage
	public void removeRemoveEmailAccessPage() {
		if (this.currentPage instanceof po_apogen.RemoveEmailAccessPage) {
			po_apogen.RemoveEmailAccessPage page = (po_apogen.RemoveEmailAccessPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]"));
			this.currentPage = new po_apogen.AccessEmailPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"removeRemoveEmailAccessPage: expected po_apogen.RemoveEmailAccessPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanAdvancedSettingsPage
	public void confirmAndSaveCreatePlanAdvancedSettingsPage() {
		if (this.currentPage instanceof po_apogen.CreatePlanAdvancedSettingsPage) {
			po_apogen.CreatePlanAdvancedSettingsPage page = (po_apogen.CreatePlanAdvancedSettingsPage) this.currentPage;
			page.createPlanAdvancedSettingsComponent.confirmAndSave();
			this.currentPage = new po_apogen.ViewPlanPage(
					page.createPlanAdvancedSettingsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"confirmAndSaveCreatePlanAdvancedSettingsPage: expected po_apogen.CreatePlanAdvancedSettingsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanAdvancedSettingsPage
	public void goToPlanBasicSettingsPageCreatePlanAdvancedSettingsPage() {
		if (this.currentPage instanceof po_apogen.CreatePlanAdvancedSettingsPage) {
			po_apogen.CreatePlanAdvancedSettingsPage page = (po_apogen.CreatePlanAdvancedSettingsPage) this.currentPage;
			page.createPlanAdvancedSettingsComponent
					.goToPlanBasicSettingsPage();
			this.currentPage = new po_apogen.CreatePlanBasicSettingsPage(
					page.createPlanAdvancedSettingsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPlanBasicSettingsPageCreatePlanAdvancedSettingsPage: expected po_apogen.CreatePlanAdvancedSettingsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanBasicSettingsPage
	public void goToPlanAdvancedSettingsCreatePlanBasicSettingsPage(
			custom_classes.Goals goal, custom_classes.WalletNames walletName) {
		if (this.currentPage instanceof po_apogen.CreatePlanBasicSettingsPage) {
			po_apogen.CreatePlanBasicSettingsPage page = (po_apogen.CreatePlanBasicSettingsPage) this.currentPage;
			if ((page.createPlanBasicSettingsComponent
					.isWalletPresent(walletName)) != (-1)) {
				page.createPlanBasicSettingsComponent.goToPlanAdvancedSettings(
						goal, walletName);
				this.currentPage = new po_apogen.CreatePlanAdvancedSettingsPage(
						page.createPlanBasicSettingsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add goal to wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPlanAdvancedSettingsCreatePlanBasicSettingsPage: expected po_apogen.CreatePlanBasicSettingsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreatePlanBasicSettingsPage
	public void goToPlanPageCreatePlanBasicSettingsPage() {
		if (this.currentPage instanceof po_apogen.CreatePlanBasicSettingsPage) {
			po_apogen.CreatePlanBasicSettingsPage page = (po_apogen.CreatePlanBasicSettingsPage) this.currentPage;
			page.createPlanBasicSettingsComponent.goToPlanPage();
			this.currentPage = new po_apogen.CreatePlanPage(
					page.createPlanBasicSettingsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPlanPageCreatePlanBasicSettingsPage: expected po_apogen.CreatePlanBasicSettingsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddWalletPage
	public void editAddWalletPage(custom_classes.WalletNames walletName) {
		if (this.currentPage instanceof po_apogen.AddWalletPage) {
			po_apogen.AddWalletPage page = (po_apogen.AddWalletPage) this.currentPage;
			page.typeJS(
					org.openqa.selenium.By
							.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_name\"]"),
					walletName.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"editAddWalletPage: expected po_apogen.AddWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddWalletPage
	public void goToWalletPageAddWalletPage() {
		if (this.currentPage instanceof po_apogen.AddWalletPage) {
			po_apogen.AddWalletPage page = (po_apogen.AddWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletPageAddWalletPage: expected po_apogen.AddWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void addTransactionTransactionsPage(
			custom_classes.TransactionDescription description,
			custom_classes.Amount amount) {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			page.transactionsComponent.addTransaction(description, amount);
			this.currentPage = new po_apogen.TransactionsPage(
					page.transactionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addTransactionTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void goToAddIncomePageTransactionsPage() {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			page.transactionsComponent.goToAddIncomePage();
			this.currentPage = new po_apogen.AddIncomePage(
					page.transactionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAddIncomePageTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void goToSetTotalPageTransactionsPage() {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			page.transactionsComponent.goToSetTotalPage();
			this.currentPage = new po_apogen.SetTotalPage(
					page.transactionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSetTotalPageTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionsPage
	public void goToWalletPageTransactionsPage() {
		if (this.currentPage instanceof po_apogen.TransactionsPage) {
			po_apogen.TransactionsPage page = (po_apogen.TransactionsPage) this.currentPage;
			page.transactionsComponent.goToWalletPage();
			this.currentPage = new po_apogen.WalletPage(
					page.transactionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletPageTransactionsPage: expected po_apogen.TransactionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GiveAccessToWalletPage
	public void addAccessGiveAccessToWalletPage(custom_classes.Email email) {
		if (this.currentPage instanceof po_apogen.GiveAccessToWalletPage) {
			po_apogen.GiveAccessToWalletPage page = (po_apogen.GiveAccessToWalletPage) this.currentPage;
			page.typeJS(
					org.openqa.selenium.By
							.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_email\"]"),
					email.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@type=\"submit\"]"));
			this.currentPage = new po_apogen.AccessEmailPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addAccessGiveAccessToWalletPage: expected po_apogen.GiveAccessToWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GiveAccessToWalletPage
	public void goToWalletPageGiveAccessToWalletPage() {
		if (this.currentPage instanceof po_apogen.GiveAccessToWalletPage) {
			po_apogen.GiveAccessToWalletPage page = (po_apogen.GiveAccessToWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletPageGiveAccessToWalletPage: expected po_apogen.GiveAccessToWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ViewPlanPage
	public void goToWalletPageViewPlanPage() {
		if (this.currentPage instanceof po_apogen.ViewPlanPage) {
			po_apogen.ViewPlanPage page = (po_apogen.ViewPlanPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[2]/a[1]"));
			this.currentPage = new po_apogen.WalletPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletPageViewPlanPage: expected po_apogen.ViewPlanPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void editWalletWalletPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			if ((((page.walletComponent.isWalletPresent(id)) && (!(page.walletComponent
					.isWalletShared(id)))) && (page.walletComponent
					.getActiveFilter().equals("Active")))
					&& (!(page.walletComponent.getActiveAccessFilter()
							.equals("Shared with you")))) {
				page.walletComponent.editWallet(id);
				this.currentPage = new po_apogen.AddWalletPage(
						page.walletComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editWalletWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void goToAddWalletPageWalletPage() {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			if (((page.walletComponent.getActiveFilter().equals("Active")) && (!(page.walletComponent
					.getActiveAccessFilter().equals("Shared with you"))))
					|| ((page.walletComponent.getActiveAccessFilter()
							.equals("Shared with you")) && (page.walletComponent
							.existWalletShared()))) {
				page.walletComponent.goToAddWalletPage();
				this.currentPage = new po_apogen.AddWalletPage(
						page.walletComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAddWalletPageWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void goToGoalsPageWalletPage() {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			page.walletComponent.goToGoalsPage();
			this.currentPage = new po_apogen.CreatePlanPage(
					page.walletComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsPageWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void goToTransactionsWalletPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			if (page.walletComponent.isWalletPresent(id)) {
				page.walletComponent.goToTransaction(id);
				this.currentPage = new po_apogen.TransactionsPage(
						page.walletComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToTransactionsWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void goToTrashPageWalletPage() {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			page.walletComponent.goToTrashPage();
			this.currentPage = new po_apogen.TrashPage(
					page.walletComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToTrashPageWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void hideWalletWalletPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			if ((((page.walletComponent.isWalletPresent(id)) && (!(page.walletComponent
					.isWalletShared(id)))) && (page.walletComponent
					.getActiveFilter().equals("Active")))
					&& (!(page.walletComponent.getActiveAccessFilter()
							.equals("Shared with you")))) {
				page.walletComponent.hideWallet(id);
				this.currentPage = new po_apogen.HideWalletPage(
						page.walletComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"hideWalletWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletPage
	public void manageWalletAccessWalletPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.WalletPage) {
			po_apogen.WalletPage page = (po_apogen.WalletPage) this.currentPage;
			if ((((page.walletComponent.isWalletPresent(id)) && (!(page.walletComponent
					.isWalletShared(id)))) && (page.walletComponent
					.getActiveFilter().equals("Active")))
					&& (!(page.walletComponent.getActiveAccessFilter()
							.equals("Shared with you")))) {
				page.walletComponent.manageWalletAccess(id);
				this.currentPage = new po_apogen.GiveAccessToWalletPage(
						page.walletComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"manageWalletAccessWalletPage: expected po_apogen.WalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SetTotalPage
	public void addProfitSetTotalPage(custom_classes.Amount amount) {
		if (this.currentPage instanceof po_apogen.SetTotalPage) {
			po_apogen.SetTotalPage page = (po_apogen.SetTotalPage) this.currentPage;
			page.typeJS(org.openqa.selenium.By.id("input_total"),
					java.lang.String.valueOf(amount.value));
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
			this.currentPage = new po_apogen.TransactionsPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addProfitSetTotalPage: expected po_apogen.SetTotalPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
