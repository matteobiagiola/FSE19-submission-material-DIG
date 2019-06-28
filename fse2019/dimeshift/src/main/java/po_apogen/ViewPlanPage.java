package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to GoalDetailsPage

public class ViewPlanPage extends BasePageObject implements PageObject{

	/**
	 * Page Object for ViewPlanPage (state19)
	 */
	public ViewPlanPage(WebDriver driver) {
		super(driver);
		if(!isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
		}
	}

	// implement method to go to home (WalletPage)
	public WalletPage goToWalletPage(){
		this.clickOn(By.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[2]/a[1]"));
		return new WalletPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.GOAL_DETAILS.value())){
			return true;
		}
		return false;
	}
}
