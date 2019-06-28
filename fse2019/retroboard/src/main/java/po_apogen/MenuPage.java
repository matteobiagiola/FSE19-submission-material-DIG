package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class MenuPage extends BasePageObject implements PageObject {

	/**
	 * Page Object for Menu (state8 is wrong) --> MenuPage
	 */
	public MenuPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("MenuPage not loaded properly");
		}
	}

	public IndexPage goToIndex() {
		this.clickOn(By.xpath("((//body/div)[2]/div//button)[1]"));
		this.clickOutsideTheModal();
		return new IndexPage(this.getDriver());
	}

	public LoginPage logout(){
		this.clickOn(By.xpath("((//body/div)[2]/div//button)[2]"));
		this.clickOutsideTheModal();
		return new LoginPage(this.getDriver());
	}

	public RetrospectivePage toggleSummaryMode(){
		if(!this.isSummaryModeOn()){
			this.clickOn(By.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
			this.clickOutsideTheModal();
			return new RetrospectivePage(this.getDriver());
		}else{
			throw new IllegalStateException("toggleSummaryMode: summary mode is already on");
		}
	}

	/*------ added */

	public RetrospectivePage untoggleSummaryMode(){
		if(this.isSummaryModeOn()){
			this.clickOn(By.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
			this.clickOutsideTheModal();
			return new RetrospectivePage(this.getDriver());
		}else{
			throw new IllegalStateException("toggleSummaryMode: summary mode is off");
		}
	}

	public void clickOutsideTheModal(){
		this.clickOn(By.xpath("((//body/div)[2]/div/div)[1]")); //click outside the modal to close it
	}

	public boolean isSummaryModeOn(){
		return this.isElementPresentOnPage(By.xpath("(//body/div)[2]/div//span[@class=\"theme__on___3ocqI\"]"));
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.MENU.value())){
			return true;
		}
		return false;
	}
}
