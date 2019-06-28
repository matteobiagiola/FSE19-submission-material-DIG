package po.users.pages;

import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.UserRoles;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.AddEditItemPage;
import po.shared.pages.modals.DeleteItemPage;
import po.site.pages.PagesContainerPage;
import po.users.components.RolesMainComponent;
import po.users.components.RolesSidebarComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class RolesContainerPage implements PageObject {

    public RolesSidebarComponent rolesSidebarComponent;
    public RolesMainComponent rolesMainComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public RolesContainerPage(WebDriver webDriver){
        this.rolesMainComponent = new RolesMainComponent(webDriver);
        this.rolesSidebarComponent = new RolesSidebarComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("RolesContainerPage not loaded properly");
        }
    }

    public RolesContainerPage giveOrRemoveAllPermissionsToUserRole(UserRoles userRoles){
        if(this.rolesSidebarComponent.isRolePresent(userRoles) && !this.rolesSidebarComponent.isRoleAdmin(userRoles)){
            this.rolesSidebarComponent.clickOnRole(userRoles);
            this.rolesMainComponent.giveOrRemoveAllPermissionsToUserRole();
            return this;
        }else{
            throw new IllegalArgumentException("giveOrRemoveAllPermissionsToUserRole: user role " + userRoles.value() + " is not present or it is an admin role");
        }
    }

    public AddEditItemPage addExistingUserRole(UserRoles userRole){
        if(this.rolesSidebarComponent.isRolePresent(userRole)){
            String poCallee = "RolesContainerPage";
            String expectingFailure = "Role already exists";
            this.rolesSidebarComponent.addRole();
            return new AddEditItemPage(this.rolesSidebarComponent.getDriver(), poCallee, userRole, expectingFailure);
        }else{
            throw new IllegalArgumentException("addExistingUserRole: user role " + userRole.value() + " does not exist");
        }
    }

    public AddEditItemPage addUserRole(UserRoles userRole){
        if(!this.rolesSidebarComponent.isRolePresent(userRole)){
            this.rolesSidebarComponent.addRole();
            String poCallee = "RolesContainerPage";
            return new AddEditItemPage(this.rolesSidebarComponent.getDriver(), poCallee, userRole);
        }else{
            throw new IllegalArgumentException("addUserRole: user role " + userRole.value() + " already exists");
        }
    }

    public AddEditItemPage editUserRoleWithExistingOne(UserRoles userRoleToEdit, UserRoles existingUserRole){
        if(this.rolesSidebarComponent.isRolePresent(userRoleToEdit) && this.rolesSidebarComponent.isRoleEditable(userRoleToEdit) && this.rolesSidebarComponent.isRolePresent(existingUserRole)){
            this.rolesSidebarComponent.editRole(userRoleToEdit);
            String poCallee = "RolesContainerPage";
            String expectingFailure = "Role already exists";
            return new AddEditItemPage(this.rolesMainComponent.getDriver(), poCallee, existingUserRole, expectingFailure);
        }else{
            throw new IllegalArgumentException("editUserRole: user role " + userRoleToEdit.value() + " is not editable or it does not exist or new user role " + existingUserRole.value() + " does not exist");
        }
    }

    public AddEditItemPage editUserRole(UserRoles userRoleToEdit, UserRoles newUserRole){
        if(this.rolesSidebarComponent.isRolePresent(userRoleToEdit) && this.rolesSidebarComponent.isRoleEditable(userRoleToEdit)){
            this.rolesSidebarComponent.editRole(userRoleToEdit);
            String poCallee = "RolesContainerPage";
            return new AddEditItemPage(this.rolesMainComponent.getDriver(), poCallee, newUserRole);
        }else{
            throw new IllegalArgumentException("editUserRole: user role " + userRoleToEdit.value() + " is not editable or it does not exist");
        }
    }

    public DeleteItemPage deleteUserRole(UserRoles userRoleToDelete){
        if(this.rolesSidebarComponent.isRolePresent(userRoleToDelete) && this.rolesSidebarComponent.isRoleEditable(userRoleToDelete)){
            this.rolesSidebarComponent.deleteRole(userRoleToDelete);
            String poCallee = "RolesContainerPage";
            return new DeleteItemPage(this.rolesSidebarComponent.getDriver(), poCallee);
        }else{
            throw new IllegalArgumentException("deleteUserRole: user role " + userRoleToDelete.value() + " is not present or not editable");
        }
    }

    public AddEditUserContainerPage goToEditCurrentUser(){
        this.menuComponent.goToEditCurrentUser();
        return new AddEditUserContainerPage(this.menuComponent.getDriver());
    }

    public DashboardContainerPage goToDashboard(){
        return (DashboardContainerPage) this.menuComponent.goTo(MenuActions.DASHBOARD);
    }

    public PagesContainerPage goToSite(){
        return (PagesContainerPage) this.menuComponent.goTo(MenuActions.SITE);
    }

    public UserListContainerPage goToUsers(){
        return (UserListContainerPage) this.menuComponent.goTo(MenuActions.USERS);
    }

    public UserListContainerPage goToUserList(){
        return (UserListContainerPage) this.navbarComponent.goTo(NavbarActions.LIST);
    }

    public PermissionsContainerPage goToPermissions(){
        return (PermissionsContainerPage) this.navbarComponent.goTo(NavbarActions.PERMISSIONS);
    }

    public RolesContainerPage goToRoles(){
        return (RolesContainerPage) this.navbarComponent.goTo(NavbarActions.PERMISSIONS);
    }

    public UserSettingsContainerPage goToSettings(){
        return (UserSettingsContainerPage) this.navbarComponent.goTo(NavbarActions.USER_SETTINGS);
    }


    @Override
    public boolean isPageLoaded() {
        if(this.rolesMainComponent.waitForElementBeingPresentOnPage(ConstantLocators.ROLES.value())){
            return true;
        }
        return false;
    }
}
