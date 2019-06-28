package po.users.pages;

import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.UserRoles;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.site.pages.PagesContainerPage;
import po.users.components.PermissionsComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class PermissionsContainerPage implements PageObject {

    public PermissionsComponent permissionsComponent; // Invent methods for 1. modify permissions for authenticated user; 2. modify permissions for anonymous user
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public PermissionsContainerPage(WebDriver webDriver){
        this.permissionsComponent = new PermissionsComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("PermissionsContainerPage not loaded properly");
        }
    }

    public PermissionsContainerPage giveOrRemoveAllPermissionsToUserRole(UserRoles userRoles){
        if(this.permissionsComponent.getUserRoles().contains(userRoles.value()) && !this.permissionsComponent.isRoleAdministrator(userRoles)){
            this.permissionsComponent.giveOrRemoveAllPermissionsToUserRole(userRoles);
            return this;
        }else{
            throw new IllegalArgumentException("giveOrRemoveAllPermissionsToUserRole: user role " + userRoles.value() + " does not exist or it is an administrator role.");
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
        if(this.permissionsComponent.waitForElementBeingPresentOnPage(ConstantLocators.PERMISSIONS.value())){
            return true;
        }
        return false;
    }
}
