package po.shared.pages;

import custom_classes.NavbarActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.site.pages.PagesContainerPage;
import po.site.pages.WidgetsContainerPage;
import po.users.pages.PermissionsContainerPage;
import po.users.pages.RolesContainerPage;
import po.users.pages.UserSettingsContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.ParametricPageComponent;

public class NavbarComponent extends BasePageObject implements PageComponent {

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    public PageObject goTo(NavbarActions navbarActions){
        if(NavbarActions.LIST.value().equals(navbarActions.value())){
            this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user\"]"));
            return new UserListContainerPage(this.getDriver());
        }else if(NavbarActions.PERMISSIONS.value().equals(navbarActions.value())){
            this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/permissions\"]"));
            return new PermissionsContainerPage(this.getDriver());
        }else if(NavbarActions.ROLES.value().equals(navbarActions.value())){
            this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/roles\"]"));
            return new RolesContainerPage(this.getDriver());
        }else if(NavbarActions.USER_SETTINGS.value().equals(navbarActions.value())){
            this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/settings\"]"));
            return new UserSettingsContainerPage(this.getDriver());
        }else if(NavbarActions.PAGES.value().equals(navbarActions.value())){
            this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/site/page\"]"));
            return new PagesContainerPage(this.getDriver());
        }else if(NavbarActions.WIDGETS.value().equals(navbarActions.value())){
            this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/site/widget\"]"));
            return new WidgetsContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("goTo NavbarComponent wrong navbar action: " + navbarActions.value());
        }
    }

    public boolean isUserTab(){
        return this.isElementPresentOnPage(By.xpath("//header[@id=\"header\"]//h1[text()=\"Users\"]"));
    }

    public boolean isSiteTab(){
        return this.isElementPresentOnPage(By.xpath("//header[@id=\"header\"]//h1[text()=\"Site\"]"));
    }
}
