package po.site.pages;

import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.SiteAddPage;
import custom_classes.SiteLinkOrPage;
import custom_classes.SiteLinks;
import custom_classes.SiteMenus;
import custom_classes.SitePages;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.shared.pages.modals.AddEditItemPage;
import po.shared.pages.modals.DeleteItemPage;
import po.site.components.PagesListComponent;
import po.site.components.PagesSidebarComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class PagesContainerPage implements PageObject {

    public PagesListComponent pagesListComponent;
    public PagesSidebarComponent pagesSidebarComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public PagesContainerPage(WebDriver webDriver){
        this.pagesListComponent = new PagesListComponent(webDriver);
        this.pagesSidebarComponent = new PagesSidebarComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("PagesContainerPage not loaded properly");
        }
    }

    //tested
    public AddEditItemPage editMenuWithExistingMenu(SiteMenus siteMenu, SiteMenus newNameSiteMenu){
        if(this.pagesSidebarComponent.isMenuPresent(siteMenu)
                && this.pagesSidebarComponent.isMenuEditable(siteMenu)
                && this.pagesSidebarComponent.isMenuPresent(newNameSiteMenu)
                && !this.pagesSidebarComponent.isSpecialMenu(newNameSiteMenu)
                && !siteMenu.value().equals(newNameSiteMenu.value())){
            this.pagesSidebarComponent.editMenu(siteMenu);
            String poCallee = this.getClass().getSimpleName();
            String expectingFailure = "Menu already exists";
            return new AddEditItemPage(this.pagesSidebarComponent.getDriver(), poCallee, newNameSiteMenu, expectingFailure);
        }else{
            throw new IllegalArgumentException("editMenuWithExistingMenu: menu " + siteMenu.value() + " not present or not editable. Or new menu " + newNameSiteMenu.value() + " does not exist or it is a special menu. Or the two menus are the same");
        }
    }

    //tested
    public AddEditItemPage editMenu(SiteMenus siteMenu, SiteMenus newNameSiteMenu){
        if(this.pagesSidebarComponent.isMenuPresent(siteMenu) && this.pagesSidebarComponent.isMenuEditable(siteMenu) && !this.pagesSidebarComponent.isSpecialMenu(newNameSiteMenu) && !this.pagesSidebarComponent.isMenuPresent(newNameSiteMenu)){
            this.pagesSidebarComponent.editMenu(siteMenu);
            String poCallee = this.getClass().getSimpleName();
            return new AddEditItemPage(this.pagesSidebarComponent.getDriver(), poCallee, newNameSiteMenu);
        }else{
            throw new IllegalArgumentException("editMenu: menu " + siteMenu.value() + " not present or not editable. Or new menu " + newNameSiteMenu.value() + " is a special menu or it already exists");
        }
    }

    //tested
    public AddEditItemPage addExistingMenu(SiteMenus siteMenu){
        if(this.pagesSidebarComponent.isMenuPresent(siteMenu) && !this.pagesSidebarComponent.isSpecialMenu(siteMenu)){
            this.pagesSidebarComponent.addMenu();
            String poCallee = this.getClass().getSimpleName();
            String expectingFailure = "Menu already exists";
            return new AddEditItemPage(this.pagesSidebarComponent.getDriver(), poCallee, siteMenu, expectingFailure);
        }else{
            throw new IllegalStateException("addExistingMenu: menu " + siteMenu.value() + " is not present or it is a special menu.");
        }
    }

    //tested
    public AddEditItemPage addMenu(SiteMenus siteMenu){
        if(!this.pagesSidebarComponent.isMenuPresent(siteMenu) && !this.pagesSidebarComponent.isSpecialMenu(siteMenu)){
            this.pagesSidebarComponent.addMenu();
            String poCallee = this.getClass().getSimpleName();
            return new AddEditItemPage(this.pagesSidebarComponent.getDriver(), poCallee, siteMenu);
        }else{
            throw new IllegalStateException("addMenu: menu " + siteMenu.value() + " is present or it is a special menu.");
        }
    }

    //tested
    public PagesContainerPage publishAllPagesAndLinks(){
        if(this.pagesListComponent.isOneElementPresent()){
            this.pagesListComponent.selectAll();
            this.pagesListComponent.publish();
            return this;
        }else{
            throw new IllegalStateException("publishAllPagesAndLinks: there must be at least one element in the list");
        }
    }

    //tested
//    public DeleteItemPage deleteAllPagesAndLinks(){
//        if(this.pagesListComponent.isOneElementPresent()){
//            this.pagesListComponent.selectAll();
//            this.pagesListComponent.delete();
//            String poCallee = this.getClass().getSimpleName();
//            return new DeleteItemPage(this.pagesListComponent.getDriver(), poCallee);
//        }else{
//            throw new IllegalStateException("deleteAllPagesAndLinks: there must be at least one element in the list");
//        }
//    }

    public PagesContainerPage deleteAllPagesAndLinks(){
        if(this.pagesListComponent.isOneElementPresent()){
            this.pagesListComponent.selectAll();
            this.pagesListComponent.delete();
            return this;
        }else{
            throw new IllegalStateException("deleteAllPagesAndLinks: there must be at least one element in the list");
        }
    }

    //tested
    public PagesContainerPage unpublishAllPagesAndLinks(){
        if(this.pagesListComponent.isOneElementPresent()){
            this.pagesListComponent.selectAll();
            this.pagesListComponent.unpublish();
            return this;
        }else{
            throw new IllegalStateException("unpublishAllPagesAndLinks: there must be at least one element in the list");
        }
    }

    //tested
    public PagesContainerPage moveAllPagesAndLinksToMenu(SiteMenus siteMenu){
        if(this.pagesSidebarComponent.isMenuPresent(siteMenu) && this.pagesListComponent.isOneElementPresent()
                && !this.pagesSidebarComponent.isTrashMenu(siteMenu)){
            this.pagesListComponent.selectAll();
            this.pagesListComponent.move(siteMenu);
            return this;
        }else{
            throw new IllegalStateException("moveAllPagesAndLinksToMenu: siteMenu " + siteMenu.value() + " does not exist or there are no links nor pages");
        }
    }

    //tested
    public PagesContainerPage goToMenu(SiteMenus siteMenu){
        if(this.pagesSidebarComponent.isMenuPresent(siteMenu)){
            this.pagesSidebarComponent.clickOnLink(siteMenu);
            return this;
        }else{
            throw new IllegalStateException("goToMenu: siteMenu " + siteMenu.value() + " not present");
        }
    }

    //tested
//    public DeleteItemPage deleteLinkOrPage(SiteLinkOrPage siteLinkOrPage){
//        if(this.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage)){
//            this.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
//            this.pagesListComponent.delete();
//            String poCallee = this.getClass().getSimpleName();
//            return new DeleteItemPage(this.pagesListComponent.getDriver(), poCallee);
//        }else{
//            throw new IllegalStateException("deleteLinkOrPage: siteLinkOrPage " + siteLinkOrPage.value() + " does not exist");
//        }
//    }

    public PagesContainerPage deleteLinkOrPage(SiteLinkOrPage siteLinkOrPage){
        if(this.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage)){
            this.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
            this.pagesListComponent.delete();
            return this;
        }else{
            throw new IllegalStateException("deleteLinkOrPage: siteLinkOrPage " + siteLinkOrPage.value() + " does not exist");
        }
    }

    //tested
    public PagesContainerPage publishLinkOrPage(SiteLinkOrPage siteLinkOrPage){
        if(this.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage) && !this.pagesListComponent.isStatusActive(siteLinkOrPage)){
            this.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
            this.pagesListComponent.publish();
            return this;
        }else{
            throw new IllegalStateException("publishLinkOrPage: siteLinkOrPage " + siteLinkOrPage.value() + " does not exist or it is active");
        }
    }

    //tested
    public PagesContainerPage unpublishLinkOrPage(SiteLinkOrPage siteLinkOrPage){
        if(this.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage) && this.pagesListComponent.isStatusActive(siteLinkOrPage)){
            this.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
            this.pagesListComponent.unpublish();
            return this;
        }else{
            throw new IllegalStateException("unpublishLinkOrPage: siteLinkOrPage " + siteLinkOrPage.value() + " does not exist or it is not active");
        }
    }

    //tested
    public PagesContainerPage moveLinkOrPageToMenu(SiteLinkOrPage siteLinkOrPage, SiteMenus siteMenu){
        if(this.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage) &&
                this.pagesSidebarComponent.isMenuPresent(siteMenu) && !this.pagesSidebarComponent.isTrashMenu(siteMenu)){
            this.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
            this.pagesListComponent.move(siteMenu);
            return this;
        }else{
            throw new IllegalStateException("moveLinkOrPageToMenu: siteLinkOrPage " + siteLinkOrPage.value() + " does not exist or siteMenu " + siteMenu.value() + " does not exist");
        }
    }

    //tested
    public AddEditLinkContainerPage editLink(SiteLinks siteLink){
        if(this.pagesListComponent.isLinkOrPagePresent(siteLink)){
            this.pagesListComponent.editLinkOrPage(siteLink);
            return new AddEditLinkContainerPage(this.pagesListComponent.getDriver());
        }else{
            throw new IllegalStateException("editLink: siteLink " + siteLink.value() + " does not exist");
        }
    }

    //tested
    public AddEditPageContainerPage editPage(SitePages sitePage){
        if(this.pagesListComponent.isLinkOrPagePresent(sitePage)){
            this.pagesListComponent.editLinkOrPage(sitePage);
            return new AddEditPageContainerPage(this.pagesListComponent.getDriver());
        }else{
            throw new IllegalStateException("editPage: sitePage " + sitePage.value() + " does not exist");
        }
    }

    //tested
    public AddEditLinkContainerPage addLink(){
        if(!this.pagesSidebarComponent.isTrashMenuActive()){
            this.pagesListComponent.addLinkOrPage(SiteAddPage.LINK);
            return new AddEditLinkContainerPage(this.pagesListComponent.getDriver());
        }else{
            throw new IllegalStateException("addLink: trash menu active, not possible to add page");
        }
    }

    //tested
    public AddEditPageContainerPage addPage(){
        if(!this.pagesSidebarComponent.isTrashMenuActive()){
            this.pagesListComponent.addLinkOrPage(SiteAddPage.PAGE);
            return new AddEditPageContainerPage(this.pagesListComponent.getDriver());
        }else{
            throw new IllegalStateException("addPage: trash menu active, not possible to add page");
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

    @Override
    public boolean isPageLoaded() {
        if(this.pagesListComponent.waitForElementBeingPresentOnPage(ConstantLocators.PAGES.value())){
            return true;
        }
        return false;
    }
}
