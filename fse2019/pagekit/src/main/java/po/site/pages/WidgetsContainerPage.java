package po.site.pages;

import custom_classes.MenuActions;
import custom_classes.NavbarActions;
import custom_classes.SiteAddWidget;
import custom_classes.Widget;
import custom_classes.WidgetLoginTitles;
import custom_classes.WidgetMenuTitles;
import custom_classes.WidgetMenus;
import custom_classes.WidgetTextTitles;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.dashboard.pages.DashboardContainerPage;
import po.shared.pages.MenuComponent;
import po.shared.pages.NavbarComponent;
import po.site.components.WidgetsListComponent;
import po.site.components.WidgetsSidebarComponent;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class WidgetsContainerPage implements PageObject {

    public WidgetsSidebarComponent widgetsSidebarComponent;
    public WidgetsListComponent widgetsListComponent;
    public MenuComponent menuComponent;
    public NavbarComponent navbarComponent;

    public WidgetsContainerPage(WebDriver webDriver){
        this.widgetsListComponent = new WidgetsListComponent(webDriver);
        this.widgetsSidebarComponent = new WidgetsSidebarComponent(webDriver);
        this.menuComponent = new MenuComponent(webDriver);
        this.navbarComponent = new NavbarComponent(webDriver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("WidgetsContainerPage not loaded properly");
        }
    }

    //tested --> bug of the application: status does not change
    public WidgetsContainerPage publishAllWidgets(){
        if(this.widgetsListComponent.isOneElementPresent()){
            this.widgetsListComponent.selectAll();
            this.widgetsListComponent.publish();
            return this;
        }else{
            throw new IllegalStateException("publishAllWidgets: there must be at least one element in the list");
        }
    }

    //tested
    public WidgetsContainerPage deleteAllWidgets(){
        if(this.widgetsListComponent.isOneElementPresent()){
            this.widgetsListComponent.selectAll();
            this.widgetsListComponent.delete();
            return this;
        }else{
            throw new IllegalStateException("deleteAllWidgets: there must be at least one element in the list");
        }
    }

    //tested --> bug of the application: status does not change
    public WidgetsContainerPage unpublishAllWidgets(){
        if(this.widgetsListComponent.isOneElementPresent()){
            this.widgetsListComponent.selectAll();
            this.widgetsListComponent.unpublish();
            return this;
        }else{
            throw new IllegalStateException("unpublishAllWidgets: there must be at least one element in the list");
        }
    }

    //tested
    public WidgetsContainerPage copyAllWidgets(){
        if(this.widgetsListComponent.isOneElementPresent()){
            this.widgetsListComponent.selectAll();
            this.widgetsListComponent.copy();
            return this;
        }else{
            throw new IllegalStateException("copy: there must be at least one element in the list");
        }
    }

    //tested
    public WidgetsContainerPage goToMenu(WidgetMenus widgetMenu){
        if(this.widgetsSidebarComponent.isMenuPresent(widgetMenu)){
            this.widgetsSidebarComponent.clickOnLink(widgetMenu);
            return this;
        }else{
            throw new IllegalStateException("goToMenu: widgetMenu " + widgetMenu.value() + " not present");
        }
    }

    //tested
    public WidgetsContainerPage deleteWidget(Widget widget){
        if(this.widgetsListComponent.isWidgetPresent(widget)){
            this.widgetsListComponent.selectWidget(widget);
            this.widgetsListComponent.delete();
            return this;
        }else{
            throw new IllegalStateException("deleteWidget: widget " + widget.value() + " does not exist");
        }
    }

    //tested --> bug of the application status does not change
    public WidgetsContainerPage publishWidget(Widget widget){
        if(this.widgetsListComponent.isWidgetPresent(widget) && !this.widgetsListComponent.isStatusActive(widget)){
            this.widgetsListComponent.selectWidget(widget);
            this.widgetsListComponent.publish();
            return this;
        }else{
            throw new IllegalStateException("publishWidget: widget " + widget.value() + " does not exist or it is active");
        }
    }

    //tested --> bug of the application status does not change
    public WidgetsContainerPage unpublishWidget(Widget widget){
        if(this.widgetsListComponent.isWidgetPresent(widget) && this.widgetsListComponent.isStatusActive(widget)){
            this.widgetsListComponent.selectWidget(widget);
            this.widgetsListComponent.unpublish();
            return this;
        }else{
            throw new IllegalStateException("unpublishWidget: widget " + widget.value() + " does not exist or it is not active");
        }
    }

    //tested
    public WidgetsContainerPage copyWidget(Widget widget){
        if(this.widgetsListComponent.isWidgetPresent(widget)){
            this.widgetsListComponent.selectWidget(widget);
            this.widgetsListComponent.copy();
            return this;
        }else{
            throw new IllegalStateException("copy: widget " + widget.value() + " does not exist");
        }
    }

    //tested
    public AddEditMenuWidgetContainerPage editMenu(WidgetMenuTitles widgetMenuTitle){
        if(this.widgetsListComponent.isWidgetPresent(widgetMenuTitle)){
            this.widgetsListComponent.editWidget(widgetMenuTitle);
            return new AddEditMenuWidgetContainerPage(this.widgetsListComponent.getDriver());
        }else{
            throw new IllegalStateException("editMenu: widgetMenuTitle " + widgetMenuTitle.value() + " does not exist");
        }
    }

    //tested
    public AddEditTextWidgetContainerPage editText(WidgetTextTitles widgetTextTitle){
        if(this.widgetsListComponent.isWidgetPresent(widgetTextTitle)){
            this.widgetsListComponent.editWidget(widgetTextTitle);
            return new AddEditTextWidgetContainerPage(this.widgetsListComponent.getDriver());
        }else{
            throw new IllegalStateException("editText: widgetTextTitle " + widgetTextTitle.value() + " does not exist");
        }
    }

    //tested
    public AddEditLoginWidgetContainerPage editLogin(WidgetLoginTitles widgetLoginTitle){
        if(this.widgetsListComponent.isWidgetPresent(widgetLoginTitle)){
            this.widgetsListComponent.editWidget(widgetLoginTitle);
            return new AddEditLoginWidgetContainerPage(this.widgetsListComponent.getDriver());
        }else{
            throw new IllegalStateException("editLogin: widgetLoginTitle " + widgetLoginTitle.value() + " does not exist");
        }
    }

    //tested
    public AddEditMenuWidgetContainerPage addMenu(){
        this.widgetsListComponent.addWidget(SiteAddWidget.MENU);
        return new AddEditMenuWidgetContainerPage(this.widgetsListComponent.getDriver());
    }

    //tested
    public AddEditTextWidgetContainerPage addText(){
        this.widgetsListComponent.addWidget(SiteAddWidget.TEXT);
        return new AddEditTextWidgetContainerPage(this.widgetsListComponent.getDriver());
    }

    //tested
    public AddEditLoginWidgetContainerPage addLogin(){
        this.widgetsListComponent.addWidget(SiteAddWidget.LOGIN);
        return new AddEditLoginWidgetContainerPage(this.widgetsListComponent.getDriver());
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

    //tested
    @Override
    public boolean isPageLoaded() {
        boolean textExpected = false;
        String text = "0";
        if(this.widgetsListComponent.waitForElementThatChangesText(ConstantLocators.WIDGETS_SPAN.value(), textExpected, text)){
            return true;
        }
        WebElement unassignedMenuSpan = this.widgetsListComponent.findElement(ConstantLocators.WIDGETS_SPAN.value());
        if(unassignedMenuSpan != null){
            String textSpan = this.widgetsListComponent.getText(unassignedMenuSpan);
            if(textSpan.equals("0")) return true;
        }
        return false;
    }
}
