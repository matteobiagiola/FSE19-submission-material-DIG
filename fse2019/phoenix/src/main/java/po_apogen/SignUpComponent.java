package po_apogen;

import custom_classes.Email;
import custom_classes.Passwords;
import custom_classes.PeopleNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class SignUpComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for SignUp (state1) --> SignUpContainerPage
	 */
	public SignUpComponent(WebDriver driver) {
		super(driver);
	}

	public void goToIndex() {
		this.clickOnSignIn();
	}

	public void sign_up_form(PeopleNames firstName, PeopleNames lastName, Email email,
								 Passwords password, Passwords passwordConfirmation) {
		this.typeFirstName(firstName.value());
		this.typeLastName(lastName.value());
		this.typeEmail(email.value());
		this.typePassword(password.value());
		this.typePasswordConfirmation(passwordConfirmation.value());
		this.clickOnSignUp();
	}

	/*------- added */

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
