package po.site.pages;

import custom_classes.AddEditNavbarActions;
import custom_classes.AddEditNavbarPoCallees;
import custom_classes.HTMLSnippets;
import custom_classes.HideInMenu;
import custom_classes.MenuActions;
import custom_classes.MetaDescriptions;
import custom_classes.NavbarActions;
import custom_classes.PageLinkStatus;
import custom_classes.SitePages;
import custom_classes.UserRoles;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.SelectImagePage;
import po.shared.components.PageLinkDetailsComponent;
import po.shared.components.HTMLFormComponent;
import po.shared.pages.AddEditNavbarComponent;
import po.shared.components.MetaComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddEditPageContainerPage implements PageObject {

    public HTMLFormComponent HTMLFormComponent;
    public AddEditNavbarComponent addEditNavbarComponent;
    public PageLinkDetailsComponent pageLinkDetailsComponent;
    public MetaComponent metaComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public AddEditPageContainerPage(WebDriver webDriver){
        this.HTMLFormComponent = new HTMLFormComponent(webDriver);
        this.addEditNavbarComponent = new AddEditNavbarComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        this.metaComponent = new MetaComponent(webDriver);
        this.pageLinkDetailsComponent = new PageLinkDetailsComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditPageContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditPageContainerPage addEditPage(SitePages sitePage, HTMLSnippets htmlSnippet, PageLinkStatus pageLinkStatus, HideInMenu hideInMenu){
        if(this.addEditNavbarComponent.isNavbarItemActive("Content")){
            this.HTMLFormComponent.enterTitle(sitePage.value());
            this.HTMLFormComponent.writeIntoTextarea(htmlSnippet.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("addEditPage: navbar action active is not page");
        }
    }

    public AddEditPageContainerPage addEditPageRestrictAccess(SitePages sitePage, HTMLSnippets htmlSnippet, PageLinkStatus pageLinkStatus, UserRoles userRole, HideInMenu hideInMenu){
        if(this.addEditNavbarComponent.isNavbarItemActive("Content") && this.pageLinkDetailsComponent.isRolePresent(userRole.value())){
            this.HTMLFormComponent.enterTitle(sitePage.value());
            this.HTMLFormComponent.writeIntoTextarea(htmlSnippet.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.pageLinkDetailsComponent.clickOnRole(userRole.value());
            this.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("addEditPage: navbar action active is not page or user role " + userRole.value() + " does not exist");
        }
    }

    //tested
    public SelectImagePage addEditMeta(SitePages sitePage, MetaDescriptions metaDescription){
        if(this.addEditNavbarComponent.isNavbarItemActive("Meta")){
            this.metaComponent.typeTitle(sitePage.value());
            this.metaComponent.typeDescription(metaDescription.value());
            this.metaComponent.selectImage();
            String poCallee = this.getClass().getSimpleName();
            return new SelectImagePage(this.menuComponent.getDriver(), poCallee);
        }else{
            throw new IllegalStateException("addEditMeta: navbar action active is not meta");
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

    public AddEditPageContainerPage goToContent(){
        return (AddEditPageContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.PAGE_CONTENT);
    }

    public AddEditPageContainerPage goToMeta(){
        return (AddEditPageContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.PAGE_META);
    }

    public PagesContainerPage closeEditPage(){
        return (PagesContainerPage) this.addEditNavbarComponent.close(AddEditNavbarPoCallees.PAGE_LINK);
    }

    @Override
    public boolean isPageLoaded() {
        if(this.HTMLFormComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_PAGE.value())){
            return true;
        }
        return false;
    }
}
