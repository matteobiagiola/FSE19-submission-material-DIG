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
		po.signin.SignIn signIn = new po.signin.SignIn(driver);
		signIn.singIn(Username.ADMIN, UserPassword.ADMIN);
		this.currentPage = new po_apogen.DashboardPage(driver);
	}

	// BOOTSTRAP POINT
	public ClassUnderTestApogen() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();
		po.signin.SignIn signIn = new po.signin.SignIn(driver);
		signIn.singIn(Username.ADMIN, UserPassword.ADMIN);
		this.currentPage = new po_apogen.DashboardPage(driver);
	}

	// PO Name: EditUserPage
	public void goToPermissionsEditUserPage() {
		if (this.currentPage instanceof po_apogen.EditUserPage) {
			po_apogen.EditUserPage page = (po_apogen.EditUserPage) this.currentPage;
			page.editUserComponent.goToPermissions();
			this.currentPage = new po_apogen.PermissionsPage(
					page.editUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsEditUserPage: expected po_apogen.EditUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditUserPage
	public void goToRolesEditUserPage() {
		if (this.currentPage instanceof po_apogen.EditUserPage) {
			po_apogen.EditUserPage page = (po_apogen.EditUserPage) this.currentPage;
			page.editUserComponent.goToRoles();
			this.currentPage = new po_apogen.AnonymousPage(
					page.editUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesEditUserPage: expected po_apogen.EditUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditUserPage
	public void goToUserEditUserPage() {
		if (this.currentPage instanceof po_apogen.EditUserPage) {
			po_apogen.EditUserPage page = (po_apogen.EditUserPage) this.currentPage;
			page.editUserComponent.goToUser();
			this.currentPage = new po_apogen.UsersPage(
					page.editUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserEditUserPage: expected po_apogen.EditUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditUserPage
	public void goToUsersEditUserPage() {
		if (this.currentPage instanceof po_apogen.EditUserPage) {
			po_apogen.EditUserPage page = (po_apogen.EditUserPage) this.currentPage;
			page.editUserComponent.goToUsers();
			this.currentPage = new po_apogen.UsersPage(
					page.editUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersEditUserPage: expected po_apogen.EditUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditUserPage
	public void user_editEditUserPage(custom_classes.Username username,
			custom_classes.Name name, custom_classes.Email email,
			custom_classes.UserPassword userPassword) {
		if (this.currentPage instanceof po_apogen.EditUserPage) {
			po_apogen.EditUserPage page = (po_apogen.EditUserPage) this.currentPage;
			if (page.editUserComponent.isEditUser()) {
				page.editUserComponent.user_edit(username, name, email,
						userPassword);
				this.currentPage = new po_apogen.EditUserPage(
						page.editUserComponent.getDriver(), true);
			} else {
				throw new NotTheRightInputValuesException(
						"user_edit: you are not in edit user mode");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"user_editEditUserPage: expected po_apogen.EditUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardPage
	public void deletePagekitDashboardPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.DashboardPage) {
			po_apogen.DashboardPage page = (po_apogen.DashboardPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.dashboardComponent
					.getWidgetsOnPage();
			if (((((id.value) - 1) < (widgetsOnPage.size())) && (page.dashboardComponent
					.isFeedWidget(widgetsOnPage.get((id.value - 1)))))
					&& (!(page.dashboardComponent
							.isWidgetFormOpen(widgetsOnPage.get((id.value - 1)))))) {
				page.dashboardComponent.deletePagekit(widgetsOnPage
						.get((id.value - 1)));
				this.currentPage = new po_apogen.DeleteWidgetPage(
						page.dashboardComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a widget"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deletePagekitDashboardPage: expected po_apogen.DashboardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardPage
	public void goToDashBoardMenuDashboardPage() {
		if (this.currentPage instanceof po_apogen.DashboardPage) {
			po_apogen.DashboardPage page = (po_apogen.DashboardPage) this.currentPage;
			page.dashboardComponent.goToDashBoardMenu();
			this.currentPage = new po_apogen.DashBoardMenuPage(
					page.dashboardComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashBoardMenuDashboardPage: expected po_apogen.DashboardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardPage
	public void goToEditComponentLocationDashboardPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.DashboardPage) {
			po_apogen.DashboardPage page = (po_apogen.DashboardPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.dashboardComponent
					.getWidgetsOnPage();
			if (((((id.value) - 1) < (widgetsOnPage.size())) && (page.dashboardComponent
					.isLocationWidget(widgetsOnPage.get((id.value - 1)))))
					&& (!(page.dashboardComponent
							.isWidgetFormOpen(widgetsOnPage.get((id.value - 1)))))) {
				page.dashboardComponent.goToEditComponentLocation(widgetsOnPage
						.get((id.value - 1)));
				this.currentPage = new po_apogen.EditLocationPage(
						page.dashboardComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a location widget or the widget form is open"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditComponentLocationDashboardPage: expected po_apogen.DashboardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardPage
	public void goToEditRegisteredUserDashboardPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.DashboardPage) {
			po_apogen.DashboardPage page = (po_apogen.DashboardPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.dashboardComponent
					.getWidgetsOnPage();
			if (((((id.value) - 1) < (widgetsOnPage.size())) && (page.dashboardComponent
					.isUserWidget(widgetsOnPage.get((id.value - 1)))))
					&& (!(page.dashboardComponent
							.isWidgetFormOpen(widgetsOnPage.get((id.value - 1)))))) {
				page.dashboardComponent.goToEditRegisteredUser(widgetsOnPage
						.get((id.value - 1)));
				this.currentPage = new po_apogen.EditRegisteredUserPage(
						page.dashboardComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a user widget or the widget form is open"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditRegisteredUserDashboardPage: expected po_apogen.DashboardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardPage
	public void goToEditUserDashboardPage() {
		if (this.currentPage instanceof po_apogen.DashboardPage) {
			po_apogen.DashboardPage page = (po_apogen.DashboardPage) this.currentPage;
			page.dashboardComponent.goToEditUser();
			this.currentPage = new po_apogen.EditUserPage(
					page.dashboardComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditUserDashboardPage: expected po_apogen.DashboardPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddUserPage
	public void addUserAddUserPage(custom_classes.Username username,
			custom_classes.Name name, custom_classes.Email email,
			custom_classes.UserPassword userPassword,
			custom_classes.UserStatus userStatus) {
		if (this.currentPage instanceof po_apogen.AddUserPage) {
			po_apogen.AddUserPage page = (po_apogen.AddUserPage) this.currentPage;
			if (!(page.addUserComponent.isEditUser())) {
				page.addUserComponent.addUser(username, name, email,
						userPassword, userStatus);
				if (page.addUserComponent
						.waitForElementBeingPresentOnPage(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))) {
					this.currentPage = new po_apogen.AddUserPage(
							page.addUserComponent.getDriver());
				} else {
					this.currentPage = new po_apogen.AddUserPage(
							page.addUserComponent.getDriver());
				}
			} else {
				throw new NotTheRightInputValuesException(
						"addUser: you are in edit user mode");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addUserAddUserPage: expected po_apogen.AddUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddUserPage
	public void goToPermissionsAddUserPage() {
		if (this.currentPage instanceof po_apogen.AddUserPage) {
			po_apogen.AddUserPage page = (po_apogen.AddUserPage) this.currentPage;
			page.addUserComponent.goToPermissions();
			this.currentPage = new po_apogen.PermissionsPage(
					page.addUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsAddUserPage: expected po_apogen.AddUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddUserPage
	public void goToRolesAddUserPage() {
		if (this.currentPage instanceof po_apogen.AddUserPage) {
			po_apogen.AddUserPage page = (po_apogen.AddUserPage) this.currentPage;
			page.addUserComponent.goToRoles();
			this.currentPage = new po_apogen.AnonymousPage(
					page.addUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesAddUserPage: expected po_apogen.AddUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddUserPage
	public void goToUserAddUserPage() {
		if (this.currentPage instanceof po_apogen.AddUserPage) {
			po_apogen.AddUserPage page = (po_apogen.AddUserPage) this.currentPage;
			page.addUserComponent.goToUser();
			this.currentPage = new po_apogen.UsersPage(
					page.addUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserAddUserPage: expected po_apogen.AddUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddUserPage
	public void goToUsersAddUserPage() {
		if (this.currentPage instanceof po_apogen.AddUserPage) {
			po_apogen.AddUserPage page = (po_apogen.AddUserPage) this.currentPage;
			page.addUserComponent.goToUsers();
			this.currentPage = new po_apogen.UsersPage(
					page.addUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddUserPage: expected po_apogen.AddUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsPage
	public void goToAnonymousPermissionsPage() {
		if (this.currentPage instanceof po_apogen.PermissionsPage) {
			po_apogen.PermissionsPage page = (po_apogen.PermissionsPage) this.currentPage;
			page.permissionsComponent.goToAnonymous();
			this.currentPage = new po_apogen.AnonymousPage(
					page.permissionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAnonymousPermissionsPage: expected po_apogen.PermissionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsPage
	public void goToPermissionsPermissionsPage() {
		if (this.currentPage instanceof po_apogen.PermissionsPage) {
			po_apogen.PermissionsPage page = (po_apogen.PermissionsPage) this.currentPage;
			page.permissionsComponent.goToPermissions();
			this.currentPage = new po_apogen.PermissionsPage(
					page.permissionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsPermissionsPage: expected po_apogen.PermissionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsPage
	public void goToUsersPermissionsPage() {
		if (this.currentPage instanceof po_apogen.PermissionsPage) {
			po_apogen.PermissionsPage page = (po_apogen.PermissionsPage) this.currentPage;
			page.permissionsComponent.goToUsers();
			this.currentPage = new po_apogen.UsersPage(
					page.permissionsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersPermissionsPage: expected po_apogen.PermissionsPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashBoardMenuPage
	public void goToDashboardDashBoardMenuPage() {
		if (this.currentPage instanceof po_apogen.DashBoardMenuPage) {
			po_apogen.DashBoardMenuPage page = (po_apogen.DashBoardMenuPage) this.currentPage;
			page.dashBoardMenuComponent.goToDashboard();
			this.currentPage = new po_apogen.DashboardPage(
					page.dashBoardMenuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardDashBoardMenuPage: expected po_apogen.DashBoardMenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashBoardMenuPage
	public void goToUsersDashBoardMenuPage() {
		if (this.currentPage instanceof po_apogen.DashBoardMenuPage) {
			po_apogen.DashBoardMenuPage page = (po_apogen.DashBoardMenuPage) this.currentPage;
			page.dashBoardMenuComponent.goToUsers();
			this.currentPage = new po_apogen.UsersPage(
					page.dashBoardMenuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersDashBoardMenuPage: expected po_apogen.DashBoardMenuPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AnonymousPage
	public void deleteUserRoleAnonymousPage(
			custom_classes.UserRoles userRoleToDelete) {
		if (this.currentPage instanceof po_apogen.AnonymousPage) {
			po_apogen.AnonymousPage page = (po_apogen.AnonymousPage) this.currentPage;
			if ((page.anonymousComponent.isRolePresent(userRoleToDelete))
					&& (page.anonymousComponent
							.isRoleEditable(userRoleToDelete))) {
				page.anonymousComponent.goToDeleteRole(userRoleToDelete);
				this.currentPage = new po_apogen.DeleteRolePage(
						page.anonymousComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteUserRole: user role " + (userRoleToDelete
								.value())) + " is not present or not editable"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteUserRoleAnonymousPage: expected po_apogen.AnonymousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AnonymousPage
	public void goToAddRoleAnonymousPage(custom_classes.UserRoles userRole) {
		if (this.currentPage instanceof po_apogen.AnonymousPage) {
			po_apogen.AnonymousPage page = (po_apogen.AnonymousPage) this.currentPage;
			if (!(page.anonymousComponent.isRolePresent(userRole))) {
				page.anonymousComponent.goToAddRole();
				this.currentPage = new po_apogen.AddRolePage(
						page.anonymousComponent.getDriver(), userRole);
			} else {
				throw new NotTheRightInputValuesException(
						(("goToAddRole: user role " + (userRole.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAddRoleAnonymousPage: expected po_apogen.AnonymousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AnonymousPage
	public void goToAdministratorAnonymousPage() {
		if (this.currentPage instanceof po_apogen.AnonymousPage) {
			po_apogen.AnonymousPage page = (po_apogen.AnonymousPage) this.currentPage;
			page.anonymousComponent.goToAdministrator();
			this.currentPage = new po_apogen.AdministratorPage(
					page.anonymousComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAdministratorAnonymousPage: expected po_apogen.AnonymousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AnonymousPage
	public void goToEditRoleAnonymousPage(
			custom_classes.UserRoles userRoleToEdit,
			custom_classes.UserRoles newUserRole) {
		if (this.currentPage instanceof po_apogen.AnonymousPage) {
			po_apogen.AnonymousPage page = (po_apogen.AnonymousPage) this.currentPage;
			if (((page.anonymousComponent.isRolePresent(userRoleToEdit)) && (page.anonymousComponent
					.isRoleEditable(userRoleToEdit)))
					&& (!(page.anonymousComponent.isRolePresent(newUserRole)))) {
				page.anonymousComponent.goToEditRole(userRoleToEdit);
				this.currentPage = new po_apogen.EditRolePage(
						page.anonymousComponent.getDriver(), newUserRole);
			} else {
				throw new NotTheRightInputValuesException(
						(((("goToEditRole: user role " + (userRoleToEdit
								.value())) + " is not editable, it does not exist or new user role ") + (newUserRole
								.value())) + " already exists."));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditRoleAnonymousPage: expected po_apogen.AnonymousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AnonymousPage
	public void goToHomeAnonymousPage() {
		if (this.currentPage instanceof po_apogen.AnonymousPage) {
			po_apogen.AnonymousPage page = (po_apogen.AnonymousPage) this.currentPage;
			page.anonymousComponent.clickOn(org.openqa.selenium.By
					.xpath("//i[@class=\"tm-icon-menu\"]"));
			page.anonymousComponent.clickOn(org.openqa.selenium.By
					.xpath("//img[@alt=\"Dashboard\"]"));
			this.currentPage = new po_apogen.DashboardPage(
					page.anonymousComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToHomeAnonymousPage: expected po_apogen.AnonymousPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditRegisteredUserPage
	public void formEditRegisteredUserPage(
			custom_classes.WidgetUserType widgetUserType,
			custom_classes.WidgetUserDisplay widgetUserDisplay,
			custom_classes.WidgetTotalUser widgetTotalUser,
			custom_classes.WidgetNumberOfUsers widgetNumberOfUsers) {
		if (this.currentPage instanceof po_apogen.EditRegisteredUserPage) {
			po_apogen.EditRegisteredUserPage page = (po_apogen.EditRegisteredUserPage) this.currentPage;
			page.editRegisteredUserComponent.form(widgetUserType,
					widgetUserDisplay, widgetTotalUser, widgetNumberOfUsers);
			this.currentPage = new po_apogen.DashboardPage(
					page.editRegisteredUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"formEditRegisteredUserPage: expected po_apogen.EditRegisteredUserPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AdministratorPage
	public void goToAdministratorAdministratorPage() {
		if (this.currentPage instanceof po_apogen.AdministratorPage) {
			po_apogen.AdministratorPage page = (po_apogen.AdministratorPage) this.currentPage;
			page.administratorComponent.goToAdministrator();
			this.currentPage = new po_apogen.AdministratorPage(
					page.administratorComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAdministratorAdministratorPage: expected po_apogen.AdministratorPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AdministratorPage
	public void goToAnonymousAdministratorPage() {
		if (this.currentPage instanceof po_apogen.AdministratorPage) {
			po_apogen.AdministratorPage page = (po_apogen.AdministratorPage) this.currentPage;
			page.administratorComponent.goToAnonymous();
			this.currentPage = new po_apogen.AnonymousPage(
					page.administratorComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAnonymousAdministratorPage: expected po_apogen.AdministratorPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditRolePage
	public void editRoleEditRolePage() {
		if (this.currentPage instanceof po_apogen.EditRolePage) {
			po_apogen.EditRolePage page = (po_apogen.EditRolePage) this.currentPage;
			page.type(org.openqa.selenium.By
					.xpath((page.xpathPrefix + "//input")), page.userRole
					.value());
			page.clickOn(org.openqa.selenium.By
					.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]")));
			this.currentPage = new po_apogen.AnonymousPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"editRoleEditRolePage: expected po_apogen.EditRolePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditRolePage
	public void goToAnonymousEditRolePage() {
		if (this.currentPage instanceof po_apogen.EditRolePage) {
			po_apogen.EditRolePage page = (po_apogen.EditRolePage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
			this.currentPage = new po_apogen.AnonymousPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAnonymousEditRolePage: expected po_apogen.EditRolePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DeleteWidgetPage
	public void cancelDeleteWidgetPage() {
		if (this.currentPage instanceof po_apogen.DeleteWidgetPage) {
			po_apogen.DeleteWidgetPage page = (po_apogen.DeleteWidgetPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
			this.currentPage = new po_apogen.DashboardPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelDeleteWidgetPage: expected po_apogen.DeleteWidgetPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DeleteWidgetPage
	public void okDeleteWidgetPage() {
		if (this.currentPage instanceof po_apogen.DeleteWidgetPage) {
			po_apogen.DeleteWidgetPage page = (po_apogen.DeleteWidgetPage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
			this.currentPage = new po_apogen.DashboardPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"okDeleteWidgetPage: expected po_apogen.DeleteWidgetPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UsersPage
	public void add_userUsersPage() {
		if (this.currentPage instanceof po_apogen.UsersPage) {
			po_apogen.UsersPage page = (po_apogen.UsersPage) this.currentPage;
			page.usersComponent.add_user();
			this.currentPage = new po_apogen.AddUserPage(
					page.usersComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"add_userUsersPage: expected po_apogen.UsersPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UsersPage
	public void goToAnonymousUsersPage() {
		if (this.currentPage instanceof po_apogen.UsersPage) {
			po_apogen.UsersPage page = (po_apogen.UsersPage) this.currentPage;
			page.usersComponent.goToAnonymous();
			this.currentPage = new po_apogen.AnonymousPage(
					page.usersComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAnonymousUsersPage: expected po_apogen.UsersPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UsersPage
	public void goToEditUsersPage(custom_classes.Id id) {
		if (this.currentPage instanceof po_apogen.UsersPage) {
			po_apogen.UsersPage page = (po_apogen.UsersPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> users = page.usersComponent
					.getUsersInList();
			if (((id.value) - 1) < (users.size())) {
				page.usersComponent.goToEdit(id);
				this.currentPage = new po_apogen.EditUserPage(
						page.usersComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("User with id " + id.value) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditUsersPage: expected po_apogen.UsersPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UsersPage
	public void goToPermissionsUsersPage() {
		if (this.currentPage instanceof po_apogen.UsersPage) {
			po_apogen.UsersPage page = (po_apogen.UsersPage) this.currentPage;
			page.usersComponent.goToPermissions();
			this.currentPage = new po_apogen.PermissionsPage(
					page.usersComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsUsersPage: expected po_apogen.UsersPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DeleteRolePage
	public void deleteRoleDeleteRolePage() {
		if (this.currentPage instanceof po_apogen.DeleteRolePage) {
			po_apogen.DeleteRolePage page = (po_apogen.DeleteRolePage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
			this.currentPage = new po_apogen.AnonymousPage(page.getDriver(),
					"wait");
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteRoleDeleteRolePage: expected po_apogen.DeleteRolePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DeleteRolePage
	public void goToAnonymousDeleteRolePage() {
		if (this.currentPage instanceof po_apogen.DeleteRolePage) {
			po_apogen.DeleteRolePage page = (po_apogen.DeleteRolePage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
			this.currentPage = new po_apogen.AnonymousPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToAnonymousDeleteRolePage: expected po_apogen.DeleteRolePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddRolePage
	public void addRoleAddRolePage() {
		if (this.currentPage instanceof po_apogen.AddRolePage) {
			po_apogen.AddRolePage page = (po_apogen.AddRolePage) this.currentPage;
			page.type(org.openqa.selenium.By
					.xpath((page.xpathPrefix + "//input")), page.userRole
					.value());
			page.clickOn(org.openqa.selenium.By
					.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]")));
			this.currentPage = new po_apogen.AnonymousPage(page.getDriver(),
					true);
		} else {
			throw new NotInTheRightPageObjectException(
					"addRoleAddRolePage: expected po_apogen.AddRolePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddRolePage
	public void cancelAddRolePage() {
		if (this.currentPage instanceof po_apogen.AddRolePage) {
			po_apogen.AddRolePage page = (po_apogen.AddRolePage) this.currentPage;
			page.clickOn(org.openqa.selenium.By
					.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
			this.currentPage = new po_apogen.AnonymousPage(page.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelAddRolePage: expected po_apogen.AddRolePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: EditLocationPage
	public void formEditLocationPage(
			custom_classes.WidgetLocation widgetLocation,
			custom_classes.WidgetUnit widgetUnit) {
		if (this.currentPage instanceof po_apogen.EditLocationPage) {
			po_apogen.EditLocationPage page = (po_apogen.EditLocationPage) this.currentPage;
			page.editLocationComponent.form(widgetLocation, widgetUnit);
			this.currentPage = new po_apogen.DashboardPage(
					page.editLocationComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"formEditLocationPage: expected po_apogen.EditLocationPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
