package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class IndexComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Index (index)
	 */
	public IndexComponent(WebDriver driver) {
		super(driver);
	}

	public void goToWelcome() {
		this.clickOnOwners();
	}

	public void goToNewOwner() {
		this.clickOnOwners();
		this.clickOnRegister();
	}

	public void goToOwners() {
		this.clickOnOwners();
		this.clickAll();
	}

	public void goToVets() {
		this.clickOnVets();
	}

	/*------- added */

	public void clickOnOwners(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/A[1]"));
	}

	public void clickOnRegister(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/UL[1]/LI[2]/A[1]"));
	}

	public void clickAll(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/UL[1]/LI[1]/A[1]"));
	}

	public void clickOnVets(){
		this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[3]/A[1]"));
	}

}
