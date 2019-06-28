package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class WelcomeComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Welcome (state3)
	 */
	public WelcomeComponent(WebDriver driver) {
		super(driver);
	}

	public void goToIndex() {
		this.clickOnOwners();
	}

	public void goToOwners() {
		this.clickAll();
	}

	public void goToNewOwner() {
		this.clickOnRegister();
	}

	/*----- added */

	public void clickOnOwners(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/A[1]"));
	}

	public void clickOnRegister(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/UL[1]/LI[2]/A[1]"));
	}

	public void clickAll(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/UL[1]/LI[1]/A[1]"));
	}
}
