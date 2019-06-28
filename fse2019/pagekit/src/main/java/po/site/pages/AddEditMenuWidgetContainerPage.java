package po.site.pages;

import custom_classes.AddEditNavbarActions;
import custom_classes.AddEditNavbarPoCallees;
import custom_classes.Depth;
import custom_classes.MenuActions;
import custom_classes.MenuSubItems;
import custom_classes.NavbarActions;
import custom_classes.PageLinkStatus;
import custom_classes.SiteLinkOrPage;
import custom_classes.SiteMenus;
import custom_classes.SitePages;
import custom_classes.StartLevel;
import custom_classes.UserRoles;
import custom_classes.WidgetMenuTitles;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.components.VisibilityComponent;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.site.components.AddEditMenuWidgetComponent;
import po.shared.pages.AddEditNavbarComponent;
import po.shared.components.PageLinkDetailsComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddEditMenuWidgetContainerPage implements PageObject{

    public AddEditNavbarComponent addEditNavbarComponent;
    public PageLinkDetailsComponent pageLinkDetailsComponent;
    public AddEditMenuWidgetComponent addEditMenuWidgetComponent; //visibility navbar
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;
    public VisibilityComponent visibilityComponent;

    public AddEditMenuWidgetContainerPage(WebDriver webDriver){
        this.addEditMenuWidgetComponent = new AddEditMenuWidgetComponent(webDriver);
        this.addEditNavbarComponent = new AddEditNavbarComponent(webDriver);
        this.pageLinkDetailsComponent = new PageLinkDetailsComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        this.visibilityComponent = new VisibilityComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditMenuWidgetContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditMenuWidgetContainerPage addEditMenuDetails(WidgetMenuTitles widgetMenuTitle, SiteMenus siteMenu, StartLevel startLevel, Depth depth, MenuSubItems menuSubItem, PageLinkStatus pageLinkStatus){
        if(this.addEditMenuWidgetComponent.isMenuPresent(siteMenu.value())){
            this.addEditMenuWidgetComponent.typeTitle(widgetMenuTitle.value());
            this.addEditMenuWidgetComponent.selectMenu(siteMenu.value());
            this.addEditMenuWidgetComponent.selectLevel(startLevel.value);
            this.addEditMenuWidgetComponent.selectDepth(depth.value());
            this.addEditMenuWidgetComponent.selectSubItems(menuSubItem.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalArgumentException("addEditMenuDetails: siteMenu " + siteMenu.value() + " does not exist");
        }
    }

    //tested
    public AddEditMenuWidgetContainerPage addEditMenuDetailsRestrictAccess(WidgetMenuTitles widgetMenuTitle, SiteMenus siteMenu, StartLevel startLevel, Depth depth, MenuSubItems menuSubItem, PageLinkStatus pageLinkStatus, UserRoles userRole){
        if(this.addEditMenuWidgetComponent.isMenuPresent(siteMenu.value()) && this.pageLinkDetailsComponent.isRolePresent(userRole.value())){
            this.addEditMenuWidgetComponent.typeTitle(widgetMenuTitle.value());
            this.addEditMenuWidgetComponent.selectMenu(siteMenu.value());
            this.addEditMenuWidgetComponent.selectLevel(startLevel.value);
            this.addEditMenuWidgetComponent.selectDepth(depth.value());
            this.addEditMenuWidgetComponent.selectSubItems(menuSubItem.value());
            this.pageLinkDetailsComponent.clickOnRole(userRole.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalArgumentException("addEditMenuDetails: siteMenu " + siteMenu.value() + " does not exist or user role " + userRole.value() + " does not exist");
        }
    }

    //tested
    public AddEditMenuWidgetContainerPage addEditVisibilityToLinkOrPage(SiteLinkOrPage siteLinkOrPage) {
        if (this.addEditNavbarComponent.isNavbarItemActive("Visibility") && this.visibilityComponent.isLinkOrPagePresent(siteLinkOrPage.value())) {
            this.visibilityComponent.clickOnPageInput(siteLinkOrPage.value());
            return this;
        }else{
            throw new IllegalStateException("addEditVisibility: navbar item active is not Visibility or link or page " + siteLinkOrPage.value() + " does not exist");
        }
    }

    public AddEditMenuWidgetContainerPage restrictVisibility(SitePages sitePage){
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

    public AddEditMenuWidgetContainerPage goToSettings(){
        return (AddEditMenuWidgetContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.MENU_SETTINGS);
    }

    public AddEditMenuWidgetContainerPage goToVisibility(){
        return (AddEditMenuWidgetContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.MENU_VISIBILITY);
    }

    public WidgetsContainerPage closeEditMenu(){
        return (WidgetsContainerPage) this.addEditNavbarComponent.close(AddEditNavbarPoCallees.MENU_TEXT_LOGIN);
    }

    @Override
    public boolean isPageLoaded() {
        if(this.addEditMenuWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_MENU.value())
                && this.visibilityComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_VISIBILITY.value())){
            return true;
        }
        if(this.addEditMenuWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_MENU.value()) &&
                (this.addEditMenuWidgetComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_VISIBILITY_BACKUP.value()))){
            return true;
        }
        return false;
    }
}
