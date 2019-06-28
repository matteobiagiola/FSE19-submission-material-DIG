package po.site.pages;

import custom_classes.AddEditNavbarActions;
import custom_classes.AddEditNavbarPoCallees;
import custom_classes.HideInMenu;
import custom_classes.LinkTypes;
import custom_classes.MenuActions;
import custom_classes.MetaDescriptions;
import custom_classes.NavbarActions;
import custom_classes.PageLinkStatus;
import custom_classes.SiteLinks;
import custom_classes.UserRoles;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.components.PageLinkDetailsComponent;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.SelectImagePage;
import po.shared.pages.modals.SelectLinkPage;
import po.site.components.AddEditLinkFormComponent;
import po.shared.pages.AddEditNavbarComponent;
import po.shared.components.MetaComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddEditLinkContainerPage implements PageObject {

    public AddEditNavbarComponent addEditNavbarComponent;
    public AddEditLinkFormComponent addEditLinkFormComponent;
    public MetaComponent metaComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;
    public PageLinkDetailsComponent pageLinkDetailsComponent;

    public AddEditLinkContainerPage(WebDriver webDriver){
        this.addEditNavbarComponent = new AddEditNavbarComponent(webDriver);
        this.addEditLinkFormComponent = new AddEditLinkFormComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        this.metaComponent = new MetaComponent(webDriver);
        this.pageLinkDetailsComponent = new PageLinkDetailsComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddEditLinkContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditLinkContainerPage addLink(PageLinkStatus pageLinkStatus, LinkTypes linkType, SiteLinks siteLink, HideInMenu hideInMenu){
        if(this.addEditLinkFormComponent.isUrlPresent()){
            this.addEditLinkFormComponent.selectLinkType(linkType.value());
            this.pageLinkDetailsComponent.typeTitle(siteLink.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("Url is not present");
        }
    }

    public AddEditLinkContainerPage addLinkRestrictAccess(PageLinkStatus pageLinkStatus, LinkTypes linkType, UserRoles userRole, SiteLinks siteLink, HideInMenu hideInMenu){
        if(this.addEditLinkFormComponent.isUrlPresent() && this.pageLinkDetailsComponent.isRolePresent(userRole.value())){
            this.addEditLinkFormComponent.selectLinkType(linkType.value());
            this.pageLinkDetailsComponent.typeTitle(siteLink.value());
            this.pageLinkDetailsComponent.clickOnRole(userRole.value());
            this.pageLinkDetailsComponent.selectStatus(pageLinkStatus.value());
            this.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
            this.addEditNavbarComponent.save();
            return this;
        }else{
            throw new IllegalStateException("Url is not present or user role " + userRole.value() + " is not present");
        }
    }

    //tested
    public SelectLinkPage selectUrl(){
        this.addEditLinkFormComponent.selectUrl();
        String poCallee = this.getClass().getSimpleName();
        return new SelectLinkPage(this.addEditLinkFormComponent.getDriver(), poCallee);
    }

    //tested
    public SelectImagePage addEditMeta(SiteLinks siteLink, MetaDescriptions metaDescription){
        if(this.addEditNavbarComponent.isNavbarItemActive("Meta")){
            this.metaComponent.typeTitle(siteLink.value());
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

    public AddEditLinkContainerPage goToSettings(){
        return (AddEditLinkContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.LINK_SETTINGS);
    }

    public AddEditLinkContainerPage goToMeta(){
        return (AddEditLinkContainerPage) this.addEditNavbarComponent.goTo(AddEditNavbarActions.LINK_META);
    }

    public PagesContainerPage closeEditLink(){
        return (PagesContainerPage) this.addEditNavbarComponent.close(AddEditNavbarPoCallees.PAGE_LINK);
    }

    @Override
    public boolean isPageLoaded() {
        if(this.addEditLinkFormComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_LINK.value())){
            return true;
        }
        return false;
    }
}
