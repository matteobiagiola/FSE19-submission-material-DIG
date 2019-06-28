package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AdvancedComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Advanced (state30) --> HomeContainerPage
	 */
	public AdvancedComponent(WebDriver driver) {
		super(driver);
	}

	// no correspondence in manual PO
	public void goToIndex() {
		this.clickCreate();
	}

	//	goToPreviousView
	public void goToPrevious() {
		this.clickPrevious();
	}

	// no correspondence in manual PO
	public void goToAdvanced() {
		this.clickAdvanced();
	}

	// added
	public void goToLogin(){
		this.clickLogout();
	}

	/*--------- added */

	public void clickLogout(){
		this.clickOn(By.xpath("//button[text()=\"Logout\"]"));
	}

	public void clickCreateSession(){
		this.clickOn(By.xpath("//button[text()=\"Create a new session\"]"));
	}

	public void clickAdvanced(){
		this.clickOn(By.xpath("//label[text()=\"Advanced\"]"));
	}

	public void clickPrevious(){
		this.clickOn(By.xpath("//label[text()=\"Previous\"]"));
	}

	public boolean isPreviousTabPresent(){
		return this.isElementPresentOnPage(By.xpath("//label[text()=\"Previous\"]"));
	}

	public boolean isPreviousTabActive(){
		WebElement activeTab = this.findElement(By.xpath("//nav/label[contains(@class,\"theme__active___2SLiK\")]"));
		String text = this.getText(activeTab);
		if(text.equalsIgnoreCase("Previous")){
			return true;
		}
		return false;
	}

	public void clickCreate(){
		this.clickOn(By.xpath("//label[text()=\"Create\"]"));
	}

}
