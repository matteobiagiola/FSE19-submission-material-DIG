package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;

public class ClassUnderTest {

	private Object currentPage = null;

	// BOOTSTRAP POINT
	public ClassUnderTest() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();
		po.shared.components.NavbarComponent navbarComponent = new po.shared.components.NavbarComponent(
				driver);
		navbarComponent.goToRegisterPage();
		po.home.pages.RegisterPage registerPage = new po.home.pages.RegisterPage(
				driver);
		registerPage.register(Username.ASD, Email.ASD, Password.ASD);
		this.currentPage = new po.wallets.pages.WalletsManagerPage(driver);
	}

	// PO Name: CreateGoalSecondStepPage
	public void addFurtherDetailsCreateGoalSecondStepPage(
			custom_classes.Amount toKeep, custom_classes.Date date) {
		if (this.currentPage instanceof po.goals.pages.CreateGoalSecondStepPage) {
			po.goals.pages.CreateGoalSecondStepPage page = (po.goals.pages.CreateGoalSecondStepPage) this.currentPage;
			page.goalSettingsComponent.addFurtherDetails(toKeep, date);
			this.currentPage = new po.goals.pages.GoalDetailsPage(
					page.goalSettingsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addFurtherDetailsCreateGoalSecondStepPage: expected po.goals.pages.CreateGoalSecondStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalSecondStepPage
	public void goBackToPreviousStepCreateGoalSecondStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalSecondStepPage) {
			po.goals.pages.CreateGoalSecondStepPage page = (po.goals.pages.CreateGoalSecondStepPage) this.currentPage;
			page.goalSettingsComponent.goBackToPreviousStep();
			this.currentPage = new po.goals.pages.CreateGoalFirstStepPage(
					page.goalSettingsComponent.getDriver(), page.editGoal);
		} else {
			throw new NotInTheRightPageObjectException(
					"goBackToPreviousStepCreateGoalSecondStepPage: expected po.goals.pages.CreateGoalSecondStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalSecondStepPage
	public void goToGoalsManagerPageCreateGoalSecondStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalSecondStepPage) {
			po.goals.pages.CreateGoalSecondStepPage page = (po.goals.pages.CreateGoalSecondStepPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsManagerPageCreateGoalSecondStepPage: expected po.goals.pages.CreateGoalSecondStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalSecondStepPage
	public void goToHomeUserLoggedInCreateGoalSecondStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalSecondStepPage) {
			po.goals.pages.CreateGoalSecondStepPage page = (po.goals.pages.CreateGoalSecondStepPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.HOME);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeUserLoggedInCreateGoalSecondStepPage: expected po.goals.pages.CreateGoalSecondStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalSecondStepPage
	public void goToWalletsManagerPageCreateGoalSecondStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalSecondStepPage) {
			po.goals.pages.CreateGoalSecondStepPage page = (po.goals.pages.CreateGoalSecondStepPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageCreateGoalSecondStepPage: expected po.goals.pages.CreateGoalSecondStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void createNewGoalsManagerPage() {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			page.goalsListComponent.createNew();
			boolean editGoal = false;
			this.currentPage = new po.goals.pages.CreateGoalFirstStepPage(
					page.goalsListComponent.getDriver(), editGoal);
		} else {
			throw new NotInTheRightPageObjectException(
					"createNewGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void editGoalGoalsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			if (page.goalsListComponent.goalExist(id)) {
				page.goalsListComponent.editGoal(id);
				boolean editGoal = true;
				this.currentPage = new po.goals.pages.CreateGoalFirstStepPage(
						page.goalsListComponent.getDriver(), editGoal);
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": goal with id ") + id.value) + " doesn't exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editGoalGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void goToGoalsManagerPageGoalsManagerPage() {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsManagerPageGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void goToHomeUserLoggedInGoalsManagerPage() {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.HOME);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeUserLoggedInGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void goToWalletsManagerPageGoalsManagerPage() {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void removeGoalGoalsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			if (page.goalsListComponent.goalExist(id)) {
				page.goalsListComponent.removeGoal(id);
				this.currentPage = new po.shared.pages.modals.ConfirmationPage(
						page.goalsListComponent.getDriver(), "GoalsManagerPage");
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": goal with id ") + id.value) + " doesn't exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"removeGoalGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalsManagerPage
	public void viewGoalReportGoalsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.goals.pages.GoalsManagerPage) {
			po.goals.pages.GoalsManagerPage page = (po.goals.pages.GoalsManagerPage) this.currentPage;
			if (page.goalsListComponent.goalExist(id)) {
				page.goalsListComponent.viewGoalReport(id);
				this.currentPage = new po.goals.pages.GoalDetailsPage(
						page.goalsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": goal with id ") + id.value) + " doesn't exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"viewGoalReportGoalsManagerPage: expected po.goals.pages.GoalsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalFirstStepPage
	public void addGoalToWalletCreateGoalFirstStepPage(
			custom_classes.Goals goal, custom_classes.WalletNames walletName) {
		if (this.currentPage instanceof po.goals.pages.CreateGoalFirstStepPage) {
			po.goals.pages.CreateGoalFirstStepPage page = (po.goals.pages.CreateGoalFirstStepPage) this.currentPage;
			if ((!(page.goalBasicSettingsComponent.isEdit()))
					&& ((page.goalBasicSettingsComponent
							.isWalletPresent(walletName)) != (-1))) {
				page.goalBasicSettingsComponent.addGoalToWallet(goal,
						walletName);
				this.currentPage = new po.goals.pages.CreateGoalSecondStepPage(
						page.goalBasicSettingsComponent.getDriver(),
						page.editGoal);
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add goal to wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addGoalToWalletCreateGoalFirstStepPage: expected po.goals.pages.CreateGoalFirstStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalFirstStepPage
	public void editGoalCreateGoalFirstStepPage(custom_classes.Goals goal) {
		if (this.currentPage instanceof po.goals.pages.CreateGoalFirstStepPage) {
			po.goals.pages.CreateGoalFirstStepPage page = (po.goals.pages.CreateGoalFirstStepPage) this.currentPage;
			if (page.goalBasicSettingsComponent.isEdit()) {
				page.goalBasicSettingsComponent.editGoal(goal);
				this.currentPage = new po.goals.pages.CreateGoalSecondStepPage(
						page.goalBasicSettingsComponent.getDriver(),
						page.editGoal);
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add goal to wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editGoalCreateGoalFirstStepPage: expected po.goals.pages.CreateGoalFirstStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalFirstStepPage
	public void goBackCreateGoalFirstStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalFirstStepPage) {
			po.goals.pages.CreateGoalFirstStepPage page = (po.goals.pages.CreateGoalFirstStepPage) this.currentPage;
			page.goalBasicSettingsComponent.goBack();
			this.currentPage = new po.goals.pages.GoalsManagerPage(
					page.goalBasicSettingsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goBackCreateGoalFirstStepPage: expected po.goals.pages.CreateGoalFirstStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalFirstStepPage
	public void goToGoalsManagerPageCreateGoalFirstStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalFirstStepPage) {
			po.goals.pages.CreateGoalFirstStepPage page = (po.goals.pages.CreateGoalFirstStepPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsManagerPageCreateGoalFirstStepPage: expected po.goals.pages.CreateGoalFirstStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalFirstStepPage
	public void goToHomeUserLoggedInCreateGoalFirstStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalFirstStepPage) {
			po.goals.pages.CreateGoalFirstStepPage page = (po.goals.pages.CreateGoalFirstStepPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.HOME);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeUserLoggedInCreateGoalFirstStepPage: expected po.goals.pages.CreateGoalFirstStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: CreateGoalFirstStepPage
	public void goToWalletsManagerPageCreateGoalFirstStepPage() {
		if (this.currentPage instanceof po.goals.pages.CreateGoalFirstStepPage) {
			po.goals.pages.CreateGoalFirstStepPage page = (po.goals.pages.CreateGoalFirstStepPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageCreateGoalFirstStepPage: expected po.goals.pages.CreateGoalFirstStepPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalDetailsPage
	public void goToGoalsManagerPageGoalDetailsPage() {
		if (this.currentPage instanceof po.goals.pages.GoalDetailsPage) {
			po.goals.pages.GoalDetailsPage page = (po.goals.pages.GoalDetailsPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsManagerPageGoalDetailsPage: expected po.goals.pages.GoalDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalDetailsPage
	public void goToHomeUserLoggedInGoalDetailsPage() {
		if (this.currentPage instanceof po.goals.pages.GoalDetailsPage) {
			po.goals.pages.GoalDetailsPage page = (po.goals.pages.GoalDetailsPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.HOME);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeUserLoggedInGoalDetailsPage: expected po.goals.pages.GoalDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalDetailsPage
	public void goToPlanYourExpensesGoalDetailsPage() {
		if (this.currentPage instanceof po.goals.pages.GoalDetailsPage) {
			po.goals.pages.GoalDetailsPage page = (po.goals.pages.GoalDetailsPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPlanYourExpensesGoalDetailsPage: expected po.goals.pages.GoalDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalDetailsPage
	public void goToWalletsManagerPageGoalDetailsPage() {
		if (this.currentPage instanceof po.goals.pages.GoalDetailsPage) {
			po.goals.pages.GoalDetailsPage page = (po.goals.pages.GoalDetailsPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageGoalDetailsPage: expected po.goals.pages.GoalDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: GoalDetailsPage
	public void refreshStatsGoalDetailsPage() {
		if (this.currentPage instanceof po.goals.pages.GoalDetailsPage) {
			po.goals.pages.GoalDetailsPage page = (po.goals.pages.GoalDetailsPage) this.currentPage;
			page.goalDetailsComponent.refreshStats();
			this.currentPage = new po.goals.pages.GoalDetailsPage(
					page.goalDetailsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"refreshStatsGoalDetailsPage: expected po.goals.pages.GoalDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationPage
	public void acceptOperationConfirmationPage() {
		if (this.currentPage instanceof po.shared.pages.modals.ConfirmationPage) {
			po.shared.pages.modals.ConfirmationPage page = (po.shared.pages.modals.ConfirmationPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]"));
			if (page.poNameCaller.equals("WalletsManagerPage")) {
				this.currentPage = new po.wallets.pages.WalletsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("WalletAccessManagerPage")) {
				this.currentPage = new po.wallets.pages.modals.WalletAccessManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("GoalsManagerPage")) {
				this.currentPage = new po.goals.pages.GoalsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("TransactionDetailsPage")) {
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": poNameCaller ") + page.poNameCaller) + " not valid"));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"acceptOperationConfirmationPage: expected po.shared.pages.modals.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationPage
	public void closeConfirmationPage() {
		if (this.currentPage instanceof po.shared.pages.modals.ConfirmationPage) {
			po.shared.pages.modals.ConfirmationPage page = (po.shared.pages.modals.ConfirmationPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			if (page.poNameCaller.equals("WalletsManagerPage")) {
				this.currentPage = new po.wallets.pages.WalletsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("WalletAccessManagerPage")) {
				this.currentPage = new po.wallets.pages.WalletsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("GoalsManagerPage")) {
				this.currentPage = new po.goals.pages.GoalsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("TransactionDetailsPage")) {
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": poNameCaller ") + page.poNameCaller) + " not valid"));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"closeConfirmationPage: expected po.shared.pages.modals.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: ConfirmationPage
	public void denyOperationConfirmationPage() {
		if (this.currentPage instanceof po.shared.pages.modals.ConfirmationPage) {
			po.shared.pages.modals.ConfirmationPage page = (po.shared.pages.modals.ConfirmationPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@class=\"btn btn-primary pull-left\"]"));
			if (page.poNameCaller.equals("WalletsManagerPage")) {
				this.currentPage = new po.wallets.pages.WalletsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("WalletAccessManagerPage")) {
				this.currentPage = new po.wallets.pages.modals.WalletAccessManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("GoalsManagerPage")) {
				this.currentPage = new po.goals.pages.GoalsManagerPage(
						page.getDriver());
			} else if (page.poNameCaller.equals("TransactionDetailsPage")) {
				this.currentPage = new po.wallets.pages.modals.TransactionDetailsPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": poNameCaller ") + page.poNameCaller) + " not valid"));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"denyOperationConfirmationPage: expected po.shared.pages.modals.ConfirmationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionDetailsPage
	public void closeTransactionDetailsPage() {
		if (this.currentPage instanceof po.wallets.pages.modals.TransactionDetailsPage) {
			po.wallets.pages.modals.TransactionDetailsPage page = (po.wallets.pages.modals.TransactionDetailsPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			this.currentPage = new po.wallets.pages.TransactionManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeTransactionDetailsPage: expected po.wallets.pages.modals.TransactionDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionDetailsPage
	public void removeTransactionTransactionDetailsPage() {
		if (this.currentPage instanceof po.wallets.pages.modals.TransactionDetailsPage) {
			po.wallets.pages.modals.TransactionDetailsPage page = (po.wallets.pages.modals.TransactionDetailsPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By.id("remove_transaction_button"));
			this.currentPage = new po.shared.pages.modals.ConfirmationPage(
					page.getDriver(), page.getClass().getSimpleName());
		} else {
			throw new NotInTheRightPageObjectException(
					"removeTransactionTransactionDetailsPage: expected po.wallets.pages.modals.TransactionDetailsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletAccessManagerPage
	public void addAccessWalletAccessManagerPage(custom_classes.Email email) {
		if (this.currentPage instanceof po.wallets.pages.modals.WalletAccessManagerPage) {
			po.wallets.pages.modals.WalletAccessManagerPage page = (po.wallets.pages.modals.WalletAccessManagerPage) this.currentPage;
			page.typeJS(
					org.openqa.selenium.By
							.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_email\"]"),
					email.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-footer\"]//input[@type=\"submit\"]"));
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addAccessWalletAccessManagerPage: expected po.wallets.pages.modals.WalletAccessManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletAccessManagerPage
	public void closeWalletAccessManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.modals.WalletAccessManagerPage) {
			po.wallets.pages.modals.WalletAccessManagerPage page = (po.wallets.pages.modals.WalletAccessManagerPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeWalletAccessManagerPage: expected po.wallets.pages.modals.WalletAccessManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletAccessManagerPage
	public void removeAccessWalletAccessManagerPage(custom_classes.Email email) {
		if (this.currentPage instanceof po.wallets.pages.modals.WalletAccessManagerPage) {
			po.wallets.pages.modals.WalletAccessManagerPage page = (po.wallets.pages.modals.WalletAccessManagerPage) this.currentPage;
			int indexElement = page.associatedEmailExist(email);
			if (indexElement != (-1)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((("(//div[@class=\"modal-body modal-body-default\"]/div[@class=\"table-responsive\"]//a)[" + (indexElement + 1)) + "]")));
				this.currentPage = new po.shared.pages.modals.ConfirmationPage(
						page.getDriver(), page.getClass().getSimpleName());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": email ") + (email
								.value())) + " has not been associated with any wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"removeAccessWalletAccessManagerPage: expected po.wallets.pages.modals.WalletAccessManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SetTotalIncomeToWalletPage
	public void closeSetTotalIncomeToWalletPage() {
		if (this.currentPage instanceof po.wallets.pages.modals.SetTotalIncomeToWalletPage) {
			po.wallets.pages.modals.SetTotalIncomeToWalletPage page = (po.wallets.pages.modals.SetTotalIncomeToWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			this.currentPage = new po.wallets.pages.TransactionManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeSetTotalIncomeToWalletPage: expected po.wallets.pages.modals.SetTotalIncomeToWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SetTotalIncomeToWalletPage
	public void setTotalIncomeSetTotalIncomeToWalletPage(
			custom_classes.Amount amount) {
		if (this.currentPage instanceof po.wallets.pages.modals.SetTotalIncomeToWalletPage) {
			po.wallets.pages.modals.SetTotalIncomeToWalletPage page = (po.wallets.pages.modals.SetTotalIncomeToWalletPage) this.currentPage;
			if (page.isElementPresentOnPage(org.openqa.selenium.By
					.id("input_total"))) {
				page.typeJS(org.openqa.selenium.By.id("input_total"),
						java.lang.String.valueOf(amount.value));
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot set total income"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"setTotalIncomeSetTotalIncomeToWalletPage: expected po.wallets.pages.modals.SetTotalIncomeToWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddWalletPage
	public void addAddWalletPage(custom_classes.WalletNames walletName) {
		if (this.currentPage instanceof po.wallets.pages.modals.AddWalletPage) {
			po.wallets.pages.modals.AddWalletPage page = (po.wallets.pages.modals.AddWalletPage) this.currentPage;
			page.typeJS(
					org.openqa.selenium.By
							.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_name\"]"),
					walletName.value());
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addAddWalletPage: expected po.wallets.pages.modals.AddWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddWalletPage
	public void closeAddWalletPage() {
		if (this.currentPage instanceof po.wallets.pages.modals.AddWalletPage) {
			po.wallets.pages.modals.AddWalletPage page = (po.wallets.pages.modals.AddWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeAddWalletPage: expected po.wallets.pages.modals.AddWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddIncomeToWalletPage
	public void addIncomeAddIncomeToWalletPage(
			custom_classes.IncomeDescription description,
			custom_classes.Amount amount) {
		if (this.currentPage instanceof po.wallets.pages.modals.AddIncomeToWalletPage) {
			po.wallets.pages.modals.AddIncomeToWalletPage page = (po.wallets.pages.modals.AddIncomeToWalletPage) this.currentPage;
			if (page.isElementPresentOnPage(org.openqa.selenium.By
					.id("input_amount"))) {
				page.typeJS(org.openqa.selenium.By.id("input_amount"),
						java.lang.String.valueOf(amount.value));
				page.typeJS(org.openqa.selenium.By.id("input_description"),
						description.value());
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@type=\"submit\"]"));
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add income"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addIncomeAddIncomeToWalletPage: expected po.wallets.pages.modals.AddIncomeToWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddIncomeToWalletPage
	public void closeAddIncomeToWalletPage() {
		if (this.currentPage instanceof po.wallets.pages.modals.AddIncomeToWalletPage) {
			po.wallets.pages.modals.AddIncomeToWalletPage page = (po.wallets.pages.modals.AddIncomeToWalletPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//div[@class=\"modal-header\"]//button[@class=\"close\"]"));
			this.currentPage = new po.wallets.pages.TransactionManagerPage(
					page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeAddIncomeToWalletPage: expected po.wallets.pages.modals.AddIncomeToWalletPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void addIncomeTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			page.walletIncomeManagerComponent.addIncome();
			this.currentPage = new po.wallets.pages.modals.AddIncomeToWalletPage(
					page.walletIncomeManagerComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addIncomeTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void addTransactionTransactionManagerPage(
			custom_classes.TransactionDescription description,
			custom_classes.Amount amount) {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			page.addTransactionComponent.addTransaction(description, amount);
			this.currentPage = new po.wallets.pages.TransactionManagerPage(
					page.addTransactionComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addTransactionTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToCurrentMonthTransactionViewTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			if (page.transactionsListComponent.isCurrentMonthViewAvailable()) {
				page.transactionsListComponent.goToTransactionsCurrentMonth();
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.transactionsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": current month view not available"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToCurrentMonthTransactionViewTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToGoalsManagerPageTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsManagerPageTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToHomeUserLoggedInTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.HOME);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeUserLoggedInTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToNextMonthTransactionViewTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			if (page.transactionsListComponent.isNextMonthViewAvailable()) {
				page.transactionsListComponent.goToTransactionsNextMonth();
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.transactionsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": next month view not available"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToNextMonthTransactionViewTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToPreviousMonthTransactionViewTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			page.transactionsListComponent.goToTransactionsPreviousMonth();
			this.currentPage = new po.wallets.pages.TransactionManagerPage(
					page.transactionsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPreviousMonthTransactionViewTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToWalletsManagerPageBreadcrumbTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageBreadcrumbTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void goToWalletsManagerPageNavbarTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageNavbarTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void selectTransactionTransactionManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			if ((page.transactionsListComponent.areTransactionsPresent())
					&& (page.transactionsListComponent.isTransactionPresent(id))) {
				page.transactionsListComponent.selectTransaction(id);
				this.currentPage = new po.wallets.pages.modals.TransactionDetailsPage(
						page.transactionsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": transaction with id ") + id.value) + " not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectTransactionTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: TransactionManagerPage
	public void setTotalIncomeTransactionManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.TransactionManagerPage) {
			po.wallets.pages.TransactionManagerPage page = (po.wallets.pages.TransactionManagerPage) this.currentPage;
			page.walletIncomeManagerComponent.setTotalIncome();
			this.currentPage = new po.wallets.pages.modals.SetTotalIncomeToWalletPage(
					page.walletIncomeManagerComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"setTotalIncomeTransactionManagerPage: expected po.wallets.pages.TransactionManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void addWalletWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if (((page.filterComponent.getActiveFilter().equals("Active")) && (!(page.accessComponent
					.getActiveAccessFilter().equals("Shared with you"))))
					|| ((page.accessComponent.getActiveAccessFilter()
							.equals("Shared with you")) && (page.walletsListComponent
							.existWalletShared()))) {
				page.walletsListComponent.addWallet();
				this.currentPage = new po.wallets.pages.modals.AddWalletPage(
						page.walletsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((page.getClass().getName()) + ": cannot add wallet"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addWalletWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void editWalletWalletsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if ((((page.walletsListComponent.isWalletPresent(id)) && (!(page.walletsListComponent
					.isWalletShared(id)))) && (page.filterComponent
					.getActiveFilter().equals("Active")))
					&& (!(page.accessComponent.getActiveAccessFilter()
							.equals("Shared with you")))) {
				page.walletsListComponent.editWallet(id);
				this.currentPage = new po.wallets.pages.modals.AddWalletPage(
						page.walletsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editWalletWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void goToGoalsManagerPageWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.GOALS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToGoalsManagerPageWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void goToHomeUserLoggedInWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			this.currentPage = page.breadcrumbComponent
					.goTo(BreadcrumbActions.HOME);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeUserLoggedInWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void goToWalletsManagerPageWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WALLETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWalletsManagerPageWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void hideWalletWalletsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if ((((page.walletsListComponent.isWalletPresent(id)) && (!(page.walletsListComponent
					.isWalletShared(id)))) && (page.filterComponent
					.getActiveFilter().equals("Active")))
					&& (!(page.accessComponent.getActiveAccessFilter()
							.equals("Shared with you")))) {
				page.walletsListComponent.hideWallet(id);
				this.currentPage = new po.shared.pages.modals.ConfirmationPage(
						page.walletsListComponent.getDriver(), page.getClass()
								.getSimpleName());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"hideWalletWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void manageWalletAccessWalletsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if ((((page.walletsListComponent.isWalletPresent(id)) && (!(page.walletsListComponent
					.isWalletShared(id)))) && (page.filterComponent
					.getActiveFilter().equals("Active")))
					&& (!(page.accessComponent.getActiveAccessFilter()
							.equals("Shared with you")))) {
				page.walletsListComponent.manageWalletAccess(id);
				this.currentPage = new po.wallets.pages.modals.WalletAccessManagerPage(
						page.walletsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"manageWalletAccessWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void removeWalletWalletsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if ((page.walletsListComponent.isWalletPresent(id))
					&& (page.filterComponent.getActiveFilter().equals("Trash"))) {
				page.walletsListComponent.removeWallet(id);
				this.currentPage = new po.shared.pages.modals.ConfirmationPage(
						page.walletsListComponent.getDriver(), page.getClass()
								.getSimpleName());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"removeWalletWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void restoreWalletWalletsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if ((page.walletsListComponent.isWalletPresent(id))
					&& (page.filterComponent.getActiveFilter().equals("Trash"))) {
				page.walletsListComponent.restoreWallet(id);
				this.currentPage = new po.wallets.pages.WalletsManagerPage(
						page.walletsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present or filters in wrong state"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"restoreWalletWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void selectActiveFilterWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			page.filterComponent.clickOnFilter("Active");
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.filterComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"selectActiveFilterWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void selectBothAccessWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			page.accessComponent.clickOnAccess("Both");
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.accessComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"selectBothAccessWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void selectSharedWithYouAccessWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			page.accessComponent.clickOnAccess("Shared with you");
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.accessComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"selectSharedWithYouAccessWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void selectTrashFilterWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			page.filterComponent.clickOnFilter("Trash");
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.filterComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"selectTrashFilterWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void selectWalletWalletsManagerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			if (page.walletsListComponent.isWalletPresent(id)) {
				page.walletsListComponent.selectWallet(id);
				this.currentPage = new po.wallets.pages.TransactionManagerPage(
						page.walletsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((((page.getClass().getName()) + ": wallet with id ") + id.value) + " not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectWalletWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WalletsManagerPage
	public void selectYoursAccessWalletsManagerPage() {
		if (this.currentPage instanceof po.wallets.pages.WalletsManagerPage) {
			po.wallets.pages.WalletsManagerPage page = (po.wallets.pages.WalletsManagerPage) this.currentPage;
			page.accessComponent.clickOnAccess("Yours");
			this.currentPage = new po.wallets.pages.WalletsManagerPage(
					page.accessComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"selectYoursAccessWalletsManagerPage: expected po.wallets.pages.WalletsManagerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
