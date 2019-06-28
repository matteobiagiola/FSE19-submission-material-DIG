package po.shared.pages;

import custom_classes.MenuActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.dashboard.pages.DashboardContainerPage;
import po.site.pages.PagesContainerPage;
import po.users.pages.AddEditUserContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.ParametricPageComponent;

public class MenuComponent extends BasePageObject implements PageComponent {

    public MenuComponent(WebDriver driver) {
        super(driver);
    }

    public PageObject goTo(MenuActions menuActions){
        if(menuActions.value().equals(MenuActions.DASHBOARD.value())){
            this.clickOn(By.xpath("//i[@class=\"tm-icon-menu\"]"));
            this.clickOn(By.xpath("//img[@alt=\"Dashboard\"]"));
            return new DashboardContainerPage(this.getDriver());
        }else if(menuActions.value().equals(MenuActions.SITE.value())){
            this.clickOn(By.xpath("//i[@class=\"tm-icon-menu\"]"));
            this.clickOn(By.xpath("//img[@alt=\"Site\"]"));
            return new PagesContainerPage(this.getDriver());
        }else if(menuActions.value().equals(MenuActions.USERS.value())){
            this.clickOn(By.xpath("//i[@class=\"tm-icon-menu\"]"));
            this.clickOn(By.xpath("//img[@alt=\"Users\"]"));
            return new UserListContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": wrong action");
        }
    }

    public void goToEditCurrentUser(){
        this.clickOn(By.xpath("//a[@title=\"Profile\"]"));
    }
}
