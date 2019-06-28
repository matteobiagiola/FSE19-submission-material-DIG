package po.users.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class UserListComponent extends BasePageObject implements PageComponent {

    public UserListComponent(WebDriver driver) {
        super(driver);
    }

    public void addUser(){
        this.clickOn(By.xpath("//a[@class=\"uk-button uk-button-primary\"]"));
    }

    /*public void selectAllUsers(){
        this.clickOn(By.xpath("//th[@class=\"pk-table-width-minimum\"]/input"));
    }*/

    /*public void activateAllUsers(){
        this.selectAllUsers();
        this.clickOn(By.xpath("//a[@class=\"pk-icon-check pk-icon-hover\"]"));
    }*/

    public void activateUser(WebElement user){
        WebElement checkboxUser = this.findElementJSByXPathStartingFrom(user, ".//td/input");
        this.clickOn(checkboxUser);
        this.clickOn(By.xpath("//a[@class=\"pk-icon-check pk-icon-hover\"]"));
    }

    /*public void blockAllUsers(){
        this.selectAllUsers();
        this.clickOn(By.xpath("//a[@class=\"pk-icon-block pk-icon-hover\"]"));
    }*/

    public void blockUser(WebElement user){
        WebElement checkboxUser = this.findElementJSByXPathStartingFrom(user, ".//td/input");
        this.clickOn(checkboxUser);
        this.clickOn(By.xpath("//a[@class=\"pk-icon-block pk-icon-hover\"]"));
    }

    /*public void deleteAllUsers(){
        this.selectAllUsers();
        this.clickOn(By.xpath("//a[@class=\"pk-icon-delete pk-icon-hover\"]"));
    }*/

    public void deleteUser(WebElement user){
        WebElement checkboxUser = this.findElementJSByXPathStartingFrom(user, ".//td/input");
        this.clickOn(checkboxUser);
        this.clickOn(By.xpath("//a[@class=\"pk-icon-delete pk-icon-hover\"]"));
    }

    public List<WebElement> getUsersInList(){
        List<WebElement> users = this.findElements(By.xpath("//table/tbody/tr[@class=\"check-item\" or @class=\"check-item uk-active\"]"));
        return users;
    }

    public boolean isAdminUser(WebElement user){
        WebElement usernameElement = this.findElementJSByXPathStartingFrom(user, ".//td[3]/div");
        String usernameText = this.getText(usernameElement);
        if(usernameText.equals("admin")) return true;
        return false;
    }

    public boolean isUserSelected(WebElement user){
        String classAttribute = this.getAttribute(user, "class");
        if(classAttribute.contains("uk-active")) return true;
        return false;
    }

}
