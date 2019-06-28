package po.site.pages;

import custom_classes.AddEditNavbarActions;
import custom_classes.AddEditNavbarPoCallees;
import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.PageLinkStatus;
import custom_classes.SitePages;
import custom_classes.UserRoles;
import custom_classes.WidgetLoginTitles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.components.VisibilityComponent;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.SelectLinkPage;
import po.site.components.AddEditLoginWidgetComponent;
import po.shared.pages.AddEditNavbarComponent;
import po.shared.components.PageLinkDetailsComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddEditLoginWidgetContainerPage implements PageObject {

    public AddEditNavbarComponent addEditNavbarComponent;
    public PageLinkDetailsComponent pageLinkDetailsComponent;
    public AddEditLoginWidgetComponent addEditLoginWidgetComponent; //visibility navbar
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;
    public VisibilityComponent visibilityComponent;

    public AddEditLoginWidgetContainerPage(WebDriver webDriver){
        this.addEditLoginWidgetComponent = new AddEditLoginWidgetComponent(webDriver);
        this.pageLinkDetailsComponent = new PageLinkDetailsComponent(webDriver);
        this.addEditNavbarComponent = new AddEditNavbarComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        this.visibilityComponent = new VisibilityComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditLoginWidgetContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditLoginWidgetContainerPage addEditLoginDetails(WidgetLoginTitles widgetLoginTitle, PageLinkStatus pageLinkStatus){
        if(!this.addEditNavbarComponent.isNavbarItemActive("Visibility")){
            this.addEditLoginWidgetComponent.typeTitle(widgetLoginTitle.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("addEditLoginDetails: visibility tab is active, cannot add login details");
        }
    }

    //tested
    public AddEditLoginWidgetContainerPage addEditLoginDetailsRestrictAccess(WidgetLoginTitles widgetLoginTitle, PageLinkStatus pageLinkStatus, UserRoles userRole){
        if(this.pageLinkDetailsComponent.isRolePresent(userRole.value())
                && !this.addEditNavbarComponent.isNavbarItemActive("Visibility")){
            this.addEditLoginWidgetComponent.typeTitle(widgetLoginTitle.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.pageLinkDetailsComponent.clickOnRole(userRole.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalArgumentException("addEditLoginDetailsRestrictAccess: user role " + userRole.value() + " does not exist or visibility tab is active");
        }
    }

    //tested
    public AddEditLoginWidgetContainerPage addEditLoginDetailsWithUrl(WidgetLoginTitles widgetLoginTitle, PageLinkStatus pageLinkStatus){
        if(this.addEditLoginWidgetComponent.isUrlLoginPresent() && this.addEditLoginWidgetComponent.isUrlLogoutPresent()
                && !this.addEditNavbarComponent.isNavbarItemActive("Visibility")){
            this.addEditLoginWidgetComponent.typeTitle(widgetLoginTitle.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalArgumentException("addEditLoginDetailsWithUrl: url login or url logout is not present or visibility tab is active");
        }
    }

    //tested
    public SelectLinkPage selectLoginRedirectLink(){
        if(!this.addEditNavbarComponent.isNavbarItemActive("Visibility")){
            this.addEditLoginWidgetComponent.selectLoginRedirect();
            String poCallee = this.getClass().getSimpleName();
            By locatorToWaitFor = By.xpath("(//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"])[1]");
            return new SelectLinkPage(this.addEditLoginWidgetComponent.getDriver(), poCallee, locatorToWaitFor);
        }else{
            throw new IllegalStateException("selectLoginRedirectLink: visibility tab is active, cannot select login redirect link");
        }
    }

    //tested
    public SelectLinkPage selectLogoutRedirectLink(){
        if(!this.addEditNavbarComponent.isNavbarItemActive("Visibility")) {
            this.addEditLoginWidgetComponent.selectLogoutRedirect();
            String poCallee = this.getClass().getSimpleName();
            By locatorToWaitFor = By.xpath("(//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"])[2]");
            return new SelectLinkPage(this.addEditLoginWidgetComponent.getDriver(), poCallee, locatorToWaitFor);
        }else{
            throw new IllegalStateException("selectLogoutRedirectLink: visibility tab is active, cannot select logout redirect link");
        }
    }

    public AddEditLoginWidgetContainerPage restrictVisibility(SitePages sitePage){
        if(this.addEditNavbarComponent.isNavbarItemActive("Visibility") && this.visibilityComponent.isLinkOrPagePresent(sitePage.value())){
            this.visibilityComponent.clickOnPageInput(sitePage.value());
            return this;
        }else{
            throw new IllegalStateException("restrictVisibility: navbar item active is not visibility");
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

    public PagesContainerPage goToPages(){
        return (PagesContainerPage) this.navbarComponent.goTo(NavbarActions.PAGES);
    }

    public WidgetsContainerPage goToWidgets(){
        return (WidgetsContainerPage) this.navbarComponent.goTo(NavbarActions.WIDGETS);
    }

    public AddEditLoginWidgetContainerPage goToSettings(){
        return (AddEditLoginWidgetContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.LOGIN_SETTINGS);
    }

    public AddEditLoginWidgetContainerPage goToVisibility(){
        return (AddEditLoginWidgetContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.LOGIN_VISIBILITY);
    }

    public WidgetsContainerPage closeEditLogin(){
        return (WidgetsContainerPage) this.addEditNavbarComponent.close(AddEditNavbarPoCallees.MENU_TEXT_LOGIN);
    }


    @Override
    public boolean isPageLoaded() {
        if(this.addEditLoginWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_LOGIN.value())
                && this.addEditLoginWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_VISIBILITY.value())){
            return true;
        }
        if(this.addEditLoginWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_LOGIN.value()) &&
                (this.addEditLoginWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_VISIBILITY_BACKUP.value()))){
            return true;
        }
        return false;
    }
}
