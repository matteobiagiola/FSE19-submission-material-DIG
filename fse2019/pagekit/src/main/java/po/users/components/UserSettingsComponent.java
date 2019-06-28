package po.users.components;

import custom_classes.RegistrationUserSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class UserSettingsComponent extends BasePageObject implements PageComponent {

    public UserSettingsComponent(WebDriver driver) {
        super(driver);
    }

    public void changeSettings(RegistrationUserSettings registrationUserSettings){
        this.clickOn(By.xpath("//input[@type=\"radio\" and @value=" + "\"" + registrationUserSettings.value() + "\"" + "]"));
        this.clickOn(By.id("form-user-verification")); //tick and untick the checkbox
        this.clickOn(By.xpath("//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\"]"));
    }


}
