package po_apogen;

import custom_classes.Email;
import custom_classes.Passwords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class IndexPage implements PageObject {

	public IndexComponent indexComponent;

	/**
	 * Page Object for Index (index) --> LoginContainerPage
	 */
	public IndexPage(WebDriver driver) {
		this.indexComponent = new IndexComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("IndexPage not loaded properly");
		}
	}

	public PageObject sign_in_form(Email email, Passwords password) {
		this.indexComponent.sign_in_form(email, password);
		long timeoutInMillis = 500;
		if(this.indexComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"error\"]"), timeoutInMillis)){
			//login failed
			this.indexComponent.refreshPage(); //refresh the page to get rid of the error notification
			return new IndexPage(this.indexComponent.getDriver());
		}else{
			//login succeeded
			return new HomePage(this.indexComponent.getDriver());
		}
	}

	public SignUpPage goToSignUp() {
		this.indexComponent.goToSignUp();
		return new SignUpPage(this.indexComponent.getDriver());
	}

	/* logs in automatically because of the default data. */
	public HomePage goToHomePage() {
		this.indexComponent.goToHomePage();
		return new HomePage(this.indexComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.indexComponent.waitForElementBeingPresentOnPage(ConstantLocators.LOGIN.value())){
			return true;
		}
		return false;
	}
}
