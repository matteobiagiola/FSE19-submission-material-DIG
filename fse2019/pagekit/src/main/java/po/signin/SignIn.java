package po.signin;

import custom_classes.UserPassword;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;

public class SignIn extends BasePageObject {

    public SignIn(WebDriver driver) {
        super(driver);
    }

    public void singIn(Username username, UserPassword userPassword){
        this.type(By.xpath("//input[@name=\"credentials[username]\"]"), username.value());
        this.type(By.xpath("//input[@name=\"credentials[password]\"]"), userPassword.value());
        this.clickOn(By.xpath("(//button[1])[1]")); //the second button is reset password in another screen
    }
}
