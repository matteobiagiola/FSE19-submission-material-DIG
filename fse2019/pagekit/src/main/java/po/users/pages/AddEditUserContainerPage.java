package po.users.pages;

import custom_classes.Email;
import custom_classes.MenuActions;
import custom_classes.Name;
import custom_classes.NavbarActions;
import custom_classes.UserPassword;
import custom_classes.UserStatus;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.site.pages.PagesContainerPage;
import po.site.pages.WidgetsContainerPage;
import po.users.components.AddEditUserComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

public class AddEditUserContainerPage implements PageObject {

    public AddEditUserComponent addEditUserComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public AddEditUserContainerPage(WebDriver webDriver){
        this.addEditUserComponent = new AddEditUserComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditUserContainerPage not loaded properly");
        }
    }

    public AddEditUserContainerPage addUser(Username username, Name name, Email email, UserPassword userPassword, UserStatus userStatus){
        if(!this.addEditUserComponent.isEditUser()){
            this.addEditUserComponent.addUser(username, name, email, userPassword, userStatus);
            if(this.addEditUserComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))){
                //PageObjectLogging.logInfo("Username or email already exists");
                return this;
            }else{
                //PageObjectLogging.logInfo("New user created");
                return this;
            }
        }else{
            throw new IllegalArgumentException("addUser: you are in edit user mode");
        }
    }

    public AddEditUserContainerPage editUser(Username username, Name name, Email email, UserPassword userPassword){
        if(this.addEditUserComponent.isEditUser()){
            this.addEditUserComponent.editUser(username, name, email, userPassword);
            return this;
        }else{
            throw new IllegalArgumentException("editUser: you are not in edit user mode");
        }
    }

    public UserListContainerPage closeOperation(){
        this.addEditUserComponent.closeOperation();
        return new UserListContainerPage(this.addEditUserComponent.getDriver());
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
        if(this.addEditUserComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_USER.value())){
            return true;
        }
        return false;
    }
}
