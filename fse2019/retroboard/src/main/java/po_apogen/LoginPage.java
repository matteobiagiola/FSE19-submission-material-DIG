package po_apogen;

import custom_classes.PeopleNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class LoginPage implements PageObject {

    public LoginComponent loginComponent;

    public LoginPage(WebDriver driver){
        this.loginComponent = new LoginComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("LoginPage not loaded properly");
        }
    }

    //tested
    public PageObject login(PeopleNames peopleName){
        this.loginComponent.typeName(peopleName.name());
        this.loginComponent.clickStart();
        long timeout = 100;
        if(this.loginComponent.waitForElementBeingPresentOnPage(By.xpath("//button[text()=\"Create a new session\"]"),timeout)){
            return new IndexPage(this.loginComponent.getDriver());
        }else{
            return new RetrospectivePage(this.loginComponent.getDriver());
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.loginComponent.waitForElementBeingPresentOnPage(ConstantLocators.LOGIN.value())){
            return true;
        }
        return false;
    }
}
