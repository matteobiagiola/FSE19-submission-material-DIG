package po_apogen;

import custom_classes.Email;
import custom_classes.Passwords;
import custom_classes.PeopleNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class SignUpPage implements PageObject {

	public SignUpComponent signUpComponent;

	/**
	 * Page Object for SignUp (state1) --> SignUpContainerPage
	 */
	public SignUpPage(WebDriver driver) {
		this.signUpComponent = new SignUpComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("SignUpPage not loaded properly");
		}
	}

	public IndexPage goToIndex() {
		this.signUpComponent.goToIndex();
		return new IndexPage(this.signUpComponent.getDriver());
	}

	public PageObject sign_up_form(PeopleNames firstName, PeopleNames lastName, Email email,
								 Passwords password, Passwords passwordConfirmation) {

		if(password.value().equals(passwordConfirmation.value())){
			this.signUpComponent.sign_up_form(firstName, lastName, email, password, passwordConfirmation);
			long timeoutInMillis = 500;
			if(this.signUpComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"error\"]"), timeoutInMillis)){
				//sign up failure: email already taken
				this.signUpComponent.refreshPage(); // refresh page to get rid of error notification
				return new SignUpPage(this.signUpComponent.getDriver());
			}else{
				//sign up succeeded
				return new HomePage(this.signUpComponent.getDriver());
			}
		}else{
			throw new IllegalArgumentException("signUp: the two passwords do not match.");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.signUpComponent.waitForElementBeingPresentOnPage(ConstantLocators.SIGNUP.value())){
			return true;
		}
		return false;
	}
}
