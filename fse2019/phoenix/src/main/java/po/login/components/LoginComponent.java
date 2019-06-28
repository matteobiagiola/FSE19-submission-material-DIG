package po.login.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class LoginComponent extends BasePageObject implements PageComponent {

    //try to see if crawljax handles it: there is an important functionality behind multi-user
    public LoginComponent(WebDriver driver) {
        super(driver);
    }

    public void typeEmail(String email){
        this.type(By.id("user_email"), email);
    }

    public void typePassword(String password){
        this.type(By.id("user_password"), password);
    }

    public void signIn(){
        this.clickOn(By.xpath("//button[text()=\"Sign in\"]"));
    }

    public void clickOnCreateNewAccount(){
        this.clickOn(By.xpath("//a[@href=\"/sign_up\"]"));
    }

    public void refreshPage(){
        this.getDriver().navigate().refresh();
    }

}
