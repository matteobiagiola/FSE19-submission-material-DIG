package po.site.pages;

import custom_classes.AddEditNavbarActions;
import custom_classes.AddEditNavbarPoCallees;
import custom_classes.HTMLSnippets;
import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.PageLinkStatus;
import custom_classes.SiteLinkOrPage;
import custom_classes.SitePages;
import custom_classes.UserRoles;
import custom_classes.WidgetTextTitles;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.components.VisibilityComponent;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.AddEditNavbarComponent;
import po.shared.components.PageLinkDetailsComponent;
import po.shared.components.HTMLFormComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddEditTextWidgetContainerPage implements PageObject {

    public AddEditNavbarComponent addEditNavbarComponent;
    public HTMLFormComponent htmlFormComponent;
    public PageLinkDetailsComponent pageLinkDetailsComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;
    public VisibilityComponent visibilityComponent;


    public AddEditTextWidgetContainerPage(WebDriver webDriver){
        this.addEditNavbarComponent = new AddEditNavbarComponent(webDriver);
        this.htmlFormComponent = new HTMLFormComponent(webDriver);
        this.pageLinkDetailsComponent = new PageLinkDetailsComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        this.visibilityComponent = new VisibilityComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditTextWidgetContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditTextWidgetContainerPage addEditTextWidget(WidgetTextTitles widgetTextTitle, HTMLSnippets htmlSnippet, PageLinkStatus pageLinkStatus){
        if(this.addEditNavbarComponent.isNavbarItemActive("Settings")){
            this.htmlFormComponent.enterTitle(widgetTextTitle.value());
            this.htmlFormComponent.writeIntoTextarea(htmlSnippet.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("addEditTextWidget: navbar item active is not Settings");
        }
    }

    //tested
    public AddEditTextWidgetContainerPage addEditTextWidgetRestrictAccess(WidgetTextTitles widgetTextTitle, HTMLSnippets htmlSnippet, PageLinkStatus pageLinkStatus, UserRoles userRole){
        if(this.addEditNavbarComponent.isNavbarItemActive("Settings") && this.pageLinkDetailsComponent.isRolePresent(userRole.value())){
            this.htmlFormComponent.enterTitle(widgetTextTitle.value());
            this.htmlFormComponent.writeIntoTextarea(htmlSnippet.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.pageLinkDetailsComponent.clickOnRole(userRole.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("addEditTextWidget: navbar item active is not Settings or user role " + userRole.value() + " does not exist");
        }
    }

    //tested
    public AddEditTextWidgetContainerPage addEditVisibilityToLinkOrPage(SiteLinkOrPage siteLinkOrPage) {
        if (this.addEditNavbarComponent.isNavbarItemActive("Visibility") && this.visibilityComponent.isLinkOrPagePresent(siteLinkOrPage.value())) {
            this.visibilityComponent.clickOnPageInput(siteLinkOrPage.value());
            return this;
        }else{
            throw new IllegalStateException("addEditVisibility: navbar item active is not Visibility or link or page " + siteLinkOrPage.value() + " does not exist");
        }
    }

    public AddEditTextWidgetContainerPage restrictVisibility(SitePages sitePage){
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

    public AddEditTextWidgetContainerPage goToSettings(){
        return (AddEditTextWidgetContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.TEXT_SETTINGS);
    }

    public AddEditTextWidgetContainerPage goToVisibility(){
        return (AddEditTextWidgetContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.TEXT_VISIBILITY);
    }

    public WidgetsContainerPage closeEditText(){
        return (WidgetsContainerPage) this.addEditNavbarComponent.close(AddEditNavbarPoCallees.MENU_TEXT_LOGIN);
    }

    @Override
    public boolean isPageLoaded() {
        if(this.htmlFormComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_TEXT.value())
                && this.visibilityComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_VISIBILITY.value())){
            return true;
        }
        if(this.htmlFormComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_TEXT.value()) &&
                (this.htmlFormComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_VISIBILITY_BACKUP.value()))){
            return true;
        }
        return false;
    }
}
