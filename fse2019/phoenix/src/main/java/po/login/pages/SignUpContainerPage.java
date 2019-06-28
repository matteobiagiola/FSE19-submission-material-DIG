package po.login.pages;

import custom_classes.Email;
import custom_classes.PeopleNames;
import custom_classes.Passwords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.board.pages.BoardsContainerPage;
import po.login.components.SignUpComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.concurrent.TimeUnit;

public class SignUpContainerPage implements PageObject {

    public SignUpComponent signUpComponent;

    //try to see if crawljax handles it: there is an important functionality behind multi-user
    public SignUpContainerPage(WebDriver driver){
        this.signUpComponent = new SignUpComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("SignUpContainerPage not loaded properly");
        }
    }

    //tested
    public SignUpContainerPage signUpFailurePassword(PeopleNames firstName, PeopleNames lastName, Email email, Passwords password, Passwords passwordConfirmation){
        if(!password.value().equals(passwordConfirmation.value())){
            this.signUpComponent.typeFirstName(firstName.value());
            this.signUpComponent.typeLastName(lastName.value());
            this.signUpComponent.typeEmail(email.value());
            this.signUpComponent.typePassword(password.value());
            this.signUpComponent.typePasswordConfirmation(passwordConfirmation.value());
            this.signUpComponent.clickOnSignUp();
            long timeoutInMillis = 500;
            if(this.signUpComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"error\"]"), timeoutInMillis, TimeUnit.MILLISECONDS)){
                this.signUpComponent.refreshPage(); // refresh page to get rid of error notification
                return this;
            }else{
                throw new IllegalStateException("signUpFailurePassword: password does not match notification not handled properly");
            }
        }else{
            throw new IllegalArgumentException("signUpFailurePassword: the two passwords are equal.");
        }
    }

    //tested
    public PageObject signUp(PeopleNames firstName, PeopleNames lastName, Email email, Passwords password, Passwords passwordConfirmation){
        if(password.value().equals(passwordConfirmation.value())){
            this.signUpComponent.typeFirstName(firstName.value());
            this.signUpComponent.typeLastName(lastName.value());
            this.signUpComponent.typeEmail(email.value());
            this.signUpComponent.typePassword(password.value());
            this.signUpComponent.typePasswordConfirmation(passwordConfirmation.value());
            this.signUpComponent.clickOnSignUp();
            long timeoutInMillis = 500;
            if(this.signUpComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"error\"]"), timeoutInMillis, TimeUnit.MILLISECONDS)){
                //sign up failure: email already taken
                this.signUpComponent.refreshPage(); // refresh page to get rid of error notification
                return this;
            }else{
                //sign up succeeded
                return new BoardsContainerPage(this.signUpComponent.getDriver());
            }
        }else{
            throw new IllegalArgumentException("signUp: the two passwords do not match.");
        }
    }

    //tested
    public LoginContainerPage goToSignInPage(){
        this.signUpComponent.clickOnSignIn();
        return new LoginContainerPage(this.signUpComponent.getDriver());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.signUpComponent.waitForElementBeingPresentOnPage(ConstantLocators.SIGNUP.value())){
            return true;
        }
        return false;
    }
}
