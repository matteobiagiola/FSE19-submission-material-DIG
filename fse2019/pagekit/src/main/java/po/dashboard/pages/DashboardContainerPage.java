package po.dashboard.pages;

import custom_classes.Id;
import custom_classes.MenuActions;
import custom_classes.WidgetFeedNumberOfPosts;
import custom_classes.WidgetFeedPostContent;
import custom_classes.WidgetFeedTitle;
import custom_classes.WidgetFeedUrl;
import custom_classes.WidgetLocation;
import custom_classes.WidgetNumberOfUsers;
import custom_classes.WidgetTotalUser;
import custom_classes.WidgetUnit;
import custom_classes.WidgetUserDisplay;
import custom_classes.WidgetUserType;
import custom_classes.Widgets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.dashboard.components.WidgetComponent;
import po.dashboard.components.WidgetsListComponent;
import po.shared.pages.modals.DeleteItemPage;
import po.shared.pages.MenuComponent;
import po.site.pages.PagesContainerPage;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.List;

public class DashboardContainerPage implements PageObject{

    public WidgetComponent widgetComponent;
    public WidgetsListComponent widgetsListComponent;
    public MenuComponent menuComponent;

    public DashboardContainerPage(WebDriver webDriver){
        this.widgetComponent = new WidgetComponent(webDriver);
        this.widgetsListComponent = new WidgetsListComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("DashboardContainerPage not loaded properly");
        }
    }

    public DashboardContainerPage addUserWidget(WidgetUserType widgetUserType, WidgetUserDisplay widgetUserDisplay,
                                                WidgetTotalUser widgetTotalUser, WidgetNumberOfUsers widgetNumberOfUsers){
        this.widgetsListComponent.addWidget(Widgets.USER);
        this.widgetComponent.addEditUserWidget(widgetUserType, widgetUserDisplay, widgetTotalUser, widgetNumberOfUsers);
        return this;
    }

    public DashboardContainerPage editUserWidget(Id id, WidgetUserType widgetUserType, WidgetUserDisplay widgetUserDisplay,
                                                 WidgetTotalUser widgetTotalUser, WidgetNumberOfUsers widgetNumberOfUsers){
        List<WebElement> widgetsOnPage = this.widgetsListComponent.getWidgetsOnPage();
        if(id.value - 1 < widgetsOnPage.size() && this.widgetComponent.isUserWidget(widgetsOnPage.get(id.value - 1)) && !this.widgetComponent.isWidgetFormOpen(widgetsOnPage.get(id.value - 1))){
            this.widgetComponent.clickOnEditWidget(widgetsOnPage.get(id.value - 1));
            this.widgetComponent.addEditUserWidget(widgetUserType, widgetUserDisplay, widgetTotalUser, widgetNumberOfUsers);
            return this;
        }else{
            throw new IllegalArgumentException("WebElement with id " + id.value + " is not a user widget or the widget form is open");
        }
    }

    public AddEditUserContainerPage goToEditUser(Id id){
        List<WebElement> widgetsOnPage = this.widgetsListComponent.getWidgetsOnPage();
        if(id.value - 1 < widgetsOnPage.size() && this.widgetComponent.isUserWidget(widgetsOnPage.get(id.value - 1))){
            this.widgetComponent.clickOnEditUserLink(widgetsOnPage.get(id.value - 1));
            return new AddEditUserContainerPage(this.widgetComponent.getDriver());
        }else{
            throw new IllegalArgumentException("WebElement with id " + id.value + " is not a user widget");
        }
    }

    public DashboardContainerPage addLocationWidget(WidgetLocation widgetLocation, WidgetUnit widgetUnit){
        this.widgetsListComponent.addWidget(Widgets.LOCATION);
        this.widgetComponent.addEditLocationWidget(widgetLocation, widgetUnit);
        return this;
    }


    public DashboardContainerPage editLocationWidget(Id id, WidgetLocation widgetLocation, WidgetUnit widgetUnit){
        List<WebElement> widgetsOnPage = this.widgetsListComponent.getWidgetsOnPage();
        if(id.value - 1 < widgetsOnPage.size() && this.widgetComponent.isLocationWidget(widgetsOnPage.get(id.value - 1)) && !this.widgetComponent.isWidgetFormOpen(widgetsOnPage.get(id.value - 1))){
            this.widgetComponent.clickOnEditWidget(widgetsOnPage.get(id.value - 1));
            this.widgetComponent.addEditLocationWidget(widgetLocation, widgetUnit);
            return this;
        }else{
            throw new IllegalArgumentException("WebElement with id " + id.value + " is not a location widget or the widget form is open");
        }
    }


    public DashboardContainerPage addFeedWidget(WidgetFeedTitle widgetFeedTitle, WidgetFeedUrl widgetFeedUrl,
                                                WidgetFeedNumberOfPosts widgetFeedNumberOfPosts, WidgetFeedPostContent widgetFeedPostContent){
        this.widgetsListComponent.addWidget(Widgets.FEED);
        this.widgetComponent.addEditFeedWidget(widgetFeedTitle, widgetFeedUrl, widgetFeedNumberOfPosts, widgetFeedPostContent);
        return this;
    }

    public DashboardContainerPage editFeedWidget(Id id, WidgetFeedTitle widgetFeedTitle, WidgetFeedUrl widgetFeedUrl,
                                                 WidgetFeedNumberOfPosts widgetFeedNumberOfPosts, WidgetFeedPostContent widgetFeedPostContent){
        List<WebElement> widgetsOnPage = this.widgetsListComponent.getWidgetsOnPage();
        if(id.value - 1 < widgetsOnPage.size() && this.widgetComponent.isFeedWidget(widgetsOnPage.get(id.value - 1)) && !this.widgetComponent.isWidgetFormOpen(widgetsOnPage.get(id.value - 1))){
            this.widgetComponent.clickOnEditWidget(widgetsOnPage.get(id.value - 1));
            this.widgetComponent.addEditFeedWidget(widgetFeedTitle, widgetFeedUrl, widgetFeedNumberOfPosts, widgetFeedPostContent);
            return this;
        }else{
            throw new IllegalArgumentException("WebElement with id " + id.value + " is not a feed widget or the widget form is open");
        }
    }

    public DeleteItemPage deleteUserOrFeedWidget(Id id){
        List<WebElement> widgetsOnPage = this.widgetsListComponent.getWidgetsOnPage();
        if(id.value - 1 < widgetsOnPage.size()
                && !this.widgetComponent.isLocationWidget(widgetsOnPage.get(id.value - 1))){
            this.widgetComponent.clickOnEditWidget(widgetsOnPage.get(id.value - 1));
            this.widgetComponent.deleteWidget(widgetsOnPage.get(id.value - 1));
            String poCallee = "DashboardContainerPage";
            return new DeleteItemPage(this.widgetComponent.getDriver(), poCallee);
        }else{
            throw new IllegalArgumentException("WebElement with id " + id.value + " is not a widget");
        }
    }

    public DashboardContainerPage deleteLocationWidget(Id id){
        List<WebElement> widgetsOnPage = this.widgetsListComponent.getWidgetsOnPage();
        if(id.value - 1 < widgetsOnPage.size()
                && this.widgetComponent.isLocationWidget(widgetsOnPage.get(id.value - 1))){
            this.widgetComponent.clickOnEditWidget(widgetsOnPage.get(id.value - 1));
            this.widgetComponent.deleteWidget(widgetsOnPage.get(id.value - 1));
            return this;
        }else{
            throw new IllegalArgumentException("WebElement with id " + id.value + " is not a widget");
        }
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

    public AddEditUserContainerPage goToEditCurrentUser(){
        this.menuComponent.goToEditCurrentUser();
        return new AddEditUserContainerPage(this.menuComponent.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.widgetsListComponent.waitForElementBeingPresentOnPage(ConstantLocators.DASHBOARD.value())){
            return true;
        }
        return false;
    }
}
