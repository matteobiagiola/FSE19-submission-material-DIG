package po.users.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class RolesMainComponent extends BasePageObject implements PageComponent {

    public RolesMainComponent(WebDriver driver) {
        super(driver);
    }

    public void giveOrRemoveAllPermissionsToUserRole(){
        this.clickOn(By.xpath("//input[@value=\"system: manage packages\"]"));
        this.clickOn(By.xpath("//input[@value=\"system: software updates\"]"));
        this.clickOn(By.xpath("//input[@value=\"system: manage widgets\"]"));
        this.clickOn(By.xpath("//input[@value=\"system: manage storage\"]"));
        this.clickOn(By.xpath("//input[@value=\"system: manage storage read only\"]"));
        this.clickOn(By.xpath("//input[@value=\"system: access settings\"]"));
        this.clickOn(By.xpath("//input[@value=\"site: manage site\"]"));
        this.clickOn(By.xpath("//input[@value=\"site: maintenance access\"]"));
        this.clickOn(By.xpath("//input[@value=\"user: manage users\"]"));
        this.clickOn(By.xpath("//input[@value=\"user: manage user permissions\"]"));
        this.clickOn(By.xpath("//input[@value=\"system: access admin area\"]"));
    }
}
