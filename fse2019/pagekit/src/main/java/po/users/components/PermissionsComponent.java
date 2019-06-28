package po.users.components;

import custom_classes.UserRoles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

import java.util.ArrayList;
import java.util.List;

public class PermissionsComponent extends BasePageObject implements PageComponent {


    public PermissionsComponent(WebDriver driver) {
        super(driver);
    }

    public void giveOrRemoveAllPermissionsToUserRole(UserRoles userRoles){
        List<String> userRoleNames = this.getUserRoles();
        int indexPropertiesToModify = this.getIndexUserRoleNames(userRoles, userRoleNames);
        this.clickOn(By.xpath("(//input[@value=\"system: manage packages\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"system: software updates\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"system: manage widgets\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"system: manage storage\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"system: manage storage read only\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"system: access settings\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"site: manage site\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"site: maintenance access\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"user: manage users\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"user: manage user permissions\"])[" + (indexPropertiesToModify + 1) + "]"));
        this.clickOn(By.xpath("(//input[@value=\"system: access admin area\"])[" + (indexPropertiesToModify + 1) + "]"));

    }

    public List<String> getUserRoles(){
        List<WebElement> userRoleThElements = this.findElements(By.xpath("//div[@id=\"installer\"]//thead/tr/th[@class=\"pk-table-width-minimum pk-table-max-width-100 uk-text-truncate uk-text-center\"]"));
        List<String> userRoleNames = new ArrayList<String>();
        for(WebElement userRoleThElement: userRoleThElements){
            String name = this.getText(userRoleThElement);
            userRoleNames.add(this.getText(userRoleThElement));
        }
        return userRoleNames;
    }

    public boolean isRoleAdministrator(UserRoles userRoles){
        if(userRoles.value().equals("Administrator")) return true;
        return false;
    }

    public int getIndexUserRoleNames(UserRoles userRoles, List<String> userRoleNames){
        for (int i = 0; i < userRoleNames.size(); i++) {
            if(userRoles.value().equals(userRoleNames.get(i))){
                return i;
            }
        }
        throw new IllegalStateException("getIndexUserRoleNames: not able to extract a valid index for " + userRoles.value() + " in " + userRoleNames);
    }
}
