package po.users.components;

import custom_classes.Email;
import custom_classes.Name;
import custom_classes.UserPassword;
import custom_classes.UserStatus;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AddEditUserComponent extends BasePageObject implements PageComponent {

    public AddEditUserComponent(WebDriver driver) {
        super(driver);
    }

    public void editUser(Username username, Name name, Email email, UserPassword userPassword){
        this.typeJS(By.id("form-username"), username.value());
        this.typeJS(By.id("form-name"), name.value());
        this.typeJS(By.id("form-email"), email.value());
        this.clickOn(By.xpath("//a[text()=\"Change password\"]"));
        //this.clickOn(By.xpath("//a[text()=\"Show\"]"));
        this.typeJS(By.id("form-password"), userPassword.value());
        this.clickOn(By.xpath("//button[@type=\"submit\"]"));
    }

    public void addUser(Username username, Name name, Email email, UserPassword userPassword, UserStatus userStatus){
        this.type(By.id("form-username"), username.value());
        this.type(By.id("form-name"), name.value());
        this.type(By.id("form-email"), email.value());
        this.clickOn(By.xpath("//a[text()=\"Change password\"]"));
        //this.clickOn(By.xpath("//a[text()=\"Show\"]"));
        this.type(By.id("form-password"), userPassword.value());
        this.clickOn(By.xpath("//form[@id=\"user-edit\"]//input[@type=\"radio\" and @value=" + "\"" + userStatus.value() + "\"" + "]"));
        //I do not model role at the moment
        this.clickOn(By.xpath("//form[@id=\"user-edit\"]//input[@type=\"checkbox\" and @value=3]"));
        this.clickOnSelenium(By.xpath("//button[@type=\"submit\"]"));
    }

    public void closeOperation(){
        this.clickOn(By.xpath("//a[@class=\"uk-button uk-margin-small-right\"]"));
    }

    public boolean isEditUser(){
        String textTitleForm = this.getText(this.findElement(By.xpath("//form[@id=\"user-edit\"]//h2")));
        if(textTitleForm.equals("Edit User")) return true;
        return false;
    }
}
