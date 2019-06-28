package po.users.pages;

import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.RegistrationUserSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.SelectLinkPage;
import po.site.pages.PagesContainerPage;
import po.users.components.UserSettingsComponent;
import po_utils.PageObject;

public class UserSettingsContainerPage implements PageObject {

    public UserSettingsComponent userSettingsComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public UserSettingsContainerPage(WebDriver webDriver){
        this.userSettingsComponent = new UserSettingsComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("UserSettingsContainerPage not loaded properly");
        }
    }

    public SelectLinkPage changeSettings(RegistrationUserSettings registrationUserSettings){
        this.userSettingsComponent.changeSettings(registrationUserSettings);
        String poCallee = "UserSettingsContainerPage";
        return new SelectLinkPage(this.userSettingsComponent.getDriver(), poCallee);
    }

    public UserSettingsContainerPage saveSettings(){
        this.userSettingsComponent.clickOn(By.xpath("//div[@id=\"settings\"]//button[@class=\"uk-button uk-button-primary\"]"));
        return this;
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
        if(this.userSettingsComponent.waitForElementBeingPresentOnPage(By.id("settings"))){
            return true;
        }
        return false;
    }
}
