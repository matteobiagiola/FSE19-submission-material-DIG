package po_apogen;

import custom_classes.Email;
import custom_classes.Passwords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class IndexComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Index (index) --> LoginContainerPage
	 */
	public IndexComponent(WebDriver driver) {
		super(driver);
	}

	public void sign_in_form(Email email, Passwords password) {
		this.typeEmail(email.value());
		this.typePassword(password.value());
		this.signIn();
	}

	public void goToSignUp() {
		this.clickOnCreateNewAccount();
	}

	/* logs in automatically because of the default data. */
	public void goToHomePage() {
		this.signIn();
	}

	/*--------- added */

	public void typeEmail(String email){
		this.type(By.id("user_email"), email);
	}

	public void typePassword(String password){
		this.type(By.id("user_password"), password);
	}

	public void signIn(){
		this.clickOn(By.xpath("//button[text()=\"Sign in\"]"));
	}

	public void clickOnCreateNewAccount(){
		this.clickOn(By.xpath("//a[@href=\"/sign_up\"]"));
	}

	public void refreshPage(){
		this.getDriver().navigate().refresh();
	}

}
