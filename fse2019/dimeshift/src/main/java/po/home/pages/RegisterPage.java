
package po.home.pages;

import custom_classes.Email;
import custom_classes.Password;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;

public class RegisterPage extends BasePageObject {

    public RegisterPage(WebDriver driver){
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public void register(Username username, Email email, Password password){
        this.typeJS(By.id("input_login"), username.value());
        this.typeJS(By.id("input_email"), email.value());
        this.typeJS(By.id("input_password"), password.value());
        this.clickOn(By.id("registration_modal_form_submit"));
    }

    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.REGISTER.value())){
            return true;
        }
        return false;
    }
}
