package po.login.pages;

import custom_classes.Email;
import custom_classes.Passwords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.board.pages.BoardsContainerPage;
import po.login.components.LoginComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.concurrent.TimeUnit;

public class LoginContainerPage implements PageObject{

    public LoginComponent loginComponent;

    //try to see if crawljax handles it: there is an important functionality behind multi-user
    public LoginContainerPage(WebDriver driver){
        this.loginComponent = new LoginComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("LoginContainerPage not loaded properly");
        }
    }

    //tested
    // credentials for demo user are already in the page
    // email: john@phoenix-trello.com
    // password: 12345678
    public BoardsContainerPage loginAsJohn(){
        this.loginComponent.signIn();
        return new BoardsContainerPage(this.loginComponent.getDriver());
    }

    //tested
    public PageObject login(Email email, Passwords password){
        this.loginComponent.typeEmail(email.value());
        this.loginComponent.typePassword(password.value());
        this.loginComponent.signIn();
        long timeoutInMillis = 500;
        if(this.loginComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"error\"]"), timeoutInMillis, TimeUnit.MILLISECONDS)){
            //login failed
            this.loginComponent.refreshPage(); //refresh the page to get rid of the error notification
            return this;
        }else{
            //login succeeded
            return new BoardsContainerPage(this.loginComponent.getDriver());
        }
    }

    //tested
    public SignUpContainerPage goToSignUp(){
        this.loginComponent.clickOnCreateNewAccount();
        return new SignUpContainerPage(this.loginComponent.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.loginComponent.waitForElementBeingPresentOnPage(ConstantLocators.LOGIN.value())){
            return true;
        }
        return false;
    }
}
