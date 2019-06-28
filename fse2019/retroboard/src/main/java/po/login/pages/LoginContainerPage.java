package po.login.pages;

import custom_classes.PeopleNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.login.components.LoginComponent;
import po.retrospective.pages.RetrospectiveContainerPage;
import po.shared.components.NavbarComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.concurrent.TimeUnit;

public class LoginContainerPage implements PageObject {

    public LoginComponent loginComponent;
    public NavbarComponent navbarComponent;

    public LoginContainerPage(WebDriver driver){
        this.loginComponent = new LoginComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("LoginContainerPage not loaded properly");
        }
    }

    //tested
    public PageObject login(PeopleNames peopleName){
        this.loginComponent.typeName(peopleName.name());
        this.loginComponent.clickStart();
        long timeout = 100;
        if(this.loginComponent.waitForElementBeingPresentOnPage(By.xpath("//button[text()=\"Create a new session\"]"),timeout, TimeUnit.MILLISECONDS)){
            return new HomeContainerPage(this.loginComponent.getDriver());
        }else{
            return new RetrospectiveContainerPage(this.loginComponent.getDriver());
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
