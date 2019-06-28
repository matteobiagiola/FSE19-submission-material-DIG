package po.users.pages;

import custom_classes.Id;
import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.DeleteItemPage;
import po.site.pages.PagesContainerPage;
import po.users.components.UserListComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.List;

public class UserListContainerPage implements PageObject {

    public UserListComponent userListComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public UserListContainerPage(WebDriver webDriver){
        this.userListComponent = new UserListComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("UserListContainerPage not loaded properly");
        }
    }

    public AddEditUserContainerPage addUser(){
        this.userListComponent.clickOn(By.xpath("//a[@class=\"uk-button uk-button-primary\"]"));
        return new AddEditUserContainerPage(this.userListComponent.getDriver());
    }

    public UserListContainerPage activateUser(Id id){
        List<WebElement> users = this.userListComponent.getUsersInList();
        if(id.value - 1 < users.size() && !this.userListComponent.isUserSelected(users.get(id.value - 1))){
            this.userListComponent.activateUser(users.get(id.value - 1));
            return this;
        }else{
            throw new IllegalArgumentException("User with id " + id.value + " is not a valid user");
        }
    }

    public UserListContainerPage blockUser(Id id){
        List<WebElement> users = this.userListComponent.getUsersInList();
        if(id.value - 1 < users.size() && !this.userListComponent.isUserSelected(users.get(id.value - 1))){
            this.userListComponent.blockUser(users.get(id.value - 1));
            if(this.userListComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))){
                //PageObjectLogging.logInfo("Unable to block yourself message");
                return this;
            }else{
                //PageObjectLogging.logInfo("User blocked");
                return this;
            }
        }else{
            throw new IllegalArgumentException("User with id " + id.value + " is not a valid user");
        }
    }

    public DeleteItemPage deleteAdminUser(Id id){
        List<WebElement> users = this.userListComponent.getUsersInList();
        if(id.value - 1 < users.size() && !this.userListComponent.isUserSelected(users.get(id.value - 1)) && this.userListComponent.isAdminUser(users.get(id.value - 1))){
            this.userListComponent.deleteUser(users.get(id.value - 1));
            String poCallee = "UserListContainerPage";
            String expectingFailure = "Delete admin user";
            return new DeleteItemPage(this.userListComponent.getDriver(), poCallee, expectingFailure);
        }else{
            throw new IllegalArgumentException("User with id " + id.value + " is not a valid user");
        }
    }

    public DeleteItemPage deleteUser(Id id){
        List<WebElement> users = this.userListComponent.getUsersInList();
        if(id.value - 1 < users.size() && !this.userListComponent.isUserSelected(users.get(id.value - 1))){
            this.userListComponent.deleteUser(users.get(id.value - 1));
            String poCallee = "UserListContainerPage";
            return new DeleteItemPage(this.userListComponent.getDriver(), poCallee);
        }else{
            throw new IllegalArgumentException("User with id " + id.value + " is not a valid user");
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
        if(this.userListComponent.waitForElementBeingPresentOnPage(ConstantLocators.USER_LIST.value())){
            return true;
        }
        return false;
    }
}
