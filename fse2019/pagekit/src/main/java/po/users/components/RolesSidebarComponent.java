package po.users.components;

import custom_classes.UserRoles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class RolesSidebarComponent extends BasePageObject implements PageComponent {


    public RolesSidebarComponent(WebDriver driver) {
        super(driver);
    }

    public void addRole(){
        this.clickOn(By.xpath("//a[@class=\"uk-button\"]"));
    }

    public void clickOnRole(UserRoles userRoles){
        WebElement userLink = this.getUserRoleLink(userRoles);
        this.clickOn(userLink);
    }

    public void deleteRole(UserRoles userRoles){
        int userRoleIndex = this.getUserRoleIndex(this.getUserRoleNames(), userRoles);
        List<WebElement> userRoleLiElements = this.getUserRoleLiElements();
        WebElement userRoleLiElement = userRoleLiElements.get(userRoleIndex);
        WebElement deleteButtonElement = this.findElementJSByXPathStartingFrom(userRoleLiElement, ".//a[@class=\"pk-icon-delete pk-icon-hover\"]");
        this.clickOn(deleteButtonElement);
    }

    public void editRole(UserRoles userRoles){
        int userRoleIndex = this.getUserRoleIndex(this.getUserRoleNames(), userRoles);
        List<WebElement> userRoleLiElements = this.getUserRoleLiElements();
        WebElement userRoleLiElement = userRoleLiElements.get(userRoleIndex);
        WebElement editButtonElement = this.findElementJSByXPathStartingFrom(userRoleLiElement, ".//a[@class=\"pk-icon-edit pk-icon-hover\"]");
        this.clickOn(editButtonElement);
    }

    public boolean isRoleEditable(UserRoles userRoles){
        int userRoleIndex = this.getUserRoleIndex(this.getUserRoleNames(), userRoles);
        List<WebElement> userRoleLiElements = this.getUserRoleLiElements();
        if(userRoleIndex <= userRoleLiElements.size()){
            WebElement userRoleLiElement = userRoleLiElements.get(userRoleIndex);
            WebElement containerIconsUserRoleEditable = this.findElementJSByXPathStartingFrom(userRoleLiElement, ".//ul");
            if(containerIconsUserRoleEditable != null) return true;
            return false;
        }else{
            throw new IllegalStateException("isRoleEditable: could not established if user role " + userRoles.value() + " is editable or not");
        }
    }

    public boolean isRolePresent(UserRoles userRoles){
        return this.getUserRoleNames().contains(userRoles.value());
    }

    public boolean isRoleAdmin(UserRoles userRoles){
        if(userRoles.value().equals("Administrator")) return true;
        return false;
    }

    public List<WebElement> getUserRoleLiElements(){
        List<WebElement> userLiElements = this.findElements(By.xpath("//ul[@class=\"uk-sortable uk-nav uk-nav-side\"]/li"));
        return userLiElements;
    }


    public WebElement getUserRoleLink(UserRoles userRoles){
        List<WebElement> links = this.getUserRoleLinks();
        for(WebElement link: links){
            String userRoleName = this.getText(link);
            if(userRoleName.equals(userRoles.value())) return link;
        }
        throw new IllegalStateException("getUserRoleLink: user link not found for user role " + userRoles.value());
    }

    public List<WebElement> getUserRoleLinks(){
        List<WebElement> userLiElements = this.findElements(By.xpath("//ul[@class=\"uk-sortable uk-nav uk-nav-side\"]/li"));
        List<WebElement> links = new ArrayList<WebElement>();
        for(WebElement userLiElement: userLiElements){
            WebElement link = this.findElementJSByXPathStartingFrom(userLiElement, ".//a");
            links.add(link);
        }
        return links;
    }

    public List<String> getUserRoleNames(){
        List<String> userRoleNames = new ArrayList<String>();
        List<WebElement> userLiElements = this.findElements(By.xpath("//ul[@class=\"uk-sortable uk-nav uk-nav-side\"]/li"));
        for(WebElement userLiElement: userLiElements){
            WebElement webElementWithText = this.findElementJSByXPathStartingFrom(userLiElement, ".//a[not(@data-uk-tooltip)]");
            String textUserLiElement = this.getText(webElementWithText);
            userRoleNames.add(textUserLiElement);
        }
        return userRoleNames;
    }

    public int getUserRoleIndex(List<String> userRoleNames, UserRoles userRoles){
        for (int i = 0; i < userRoleNames.size(); i++) {
            if(userRoleNames.get(i).equals(userRoles.value())) return i;
        }
        throw new IllegalStateException("getUserRoleIndex: could not find index for user role: " + userRoles.value());
    }
}
