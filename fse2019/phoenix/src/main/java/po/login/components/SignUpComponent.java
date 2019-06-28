package po.login.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class SignUpComponent extends BasePageObject implements PageComponent {

    //try to see if crawljax handles it: there is an important functionality behind multi-user
    public SignUpComponent(WebDriver driver) {
        super(driver);
    }

    public void typeFirstName(String firstName){
        this.type(By.id("user_first_name"), firstName);
    }

    public void typeLastName(String lastName){
        this.type(By.id("user_last_name"), lastName);
    }

    public void typeEmail(String email){
        this.type(By.id("user_email"), email);
    }

    public void typePassword(String password){
        this.type(By.id("user_password"), password);
    }

    public void typePasswordConfirmation(String passwordConfirmation){
        this.type(By.id("user_password_confirmation"), passwordConfirmation);
    }

    public void clickOnSignUp(){
        this.clickOn(By.xpath("//button[text()=\"Sign up\"]"));
    }

    public void clickOnSignIn(){
        this.clickOn(By.xpath("//a[@href=\"/sign_in\"]"));
    }

    public void refreshPage(){
        this.getDriver().navigate().refresh();
    }
}
