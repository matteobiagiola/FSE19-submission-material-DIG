package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class PageLinkDetailsComponent extends BasePageObject implements PageComponent {

    public PageLinkDetailsComponent(WebDriver driver) {
        super(driver);
    }

    public void typeTitle(String title){
        this.type(By.id("form-menu-title"), title);
    }

    public void typeSlug(String slug){
        this.type(By.id("form-slug"), slug);
    }

    public void selectStatus(String status){
        this.selectOptionInDropdown(By.id("form-status"), status);
    }

    public boolean isRolePresent(String userRole){
        return this.getRoleNames().contains(userRole);
    }

    public List<String> getRoleNames(){
        List<WebElement> roles = this.getRoles();
        List<String> roleNames = new ArrayList<String>();
        for(WebElement role: roles){
            String roleText = this.getText(role);
            if(roleText != null && !roleText.isEmpty()){
                roleNames.add(roleText);
            }else{
                throw new IllegalStateException("getRoleNames: roleText cannot be null or empty -> " + roleText);
            }
        }
        return roleNames;
    }

    public List<WebElement> getRoles(){
        return this.findElements(By.xpath("//div[@class=\"uk-form-controls uk-form-controls-text\"]/p/label"));
    }

    //it doesn't matter if the role is found or not: this method will ignore a user role if it doesn't exist
    public void clickOnRole(String userRole){
        if(userRole == null || userRole.isEmpty()){
            return;
        }
        List<WebElement> roles = this.getRoles();
        for(WebElement role: roles){
            String roleText = this.getText(role);
            if(roleText.equals(userRole)){
                WebElement inputCheckbox = this.findElementJSByXPathStartingFrom(role, "./input");
                this.clickOn(inputCheckbox);
            }
        }
    }

    public void hideOnMenu(String hide){
        if(hide.equals("Yes")) this.clickOn(By.xpath("//div[@class=\"uk-form-controls uk-form-controls-text\"]/label[text()=\"Hide in menu\"]/input"));
    }
}
