package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class DashBoardMenuPage implements PageObject {

	public DashBoardMenuComponent dashBoardMenuComponent;

	/**
	 * Page Object for DashBoardMenu (state223) --> DashboardContainerPage
	 */
	public DashBoardMenuPage(WebDriver driver) {
		this.dashBoardMenuComponent = new DashBoardMenuComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("DashboardMenuPage not loaded properly");
		}
	}

	public DashboardPage goToDashboard() {
		this.dashBoardMenuComponent.goToDashboard();
		return new DashboardPage(this.dashBoardMenuComponent.getDriver());
	}

	public UsersPage goToUsers() {
		this.dashBoardMenuComponent.goToUsers();
		return new UsersPage(this.dashBoardMenuComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.dashBoardMenuComponent.waitForElementBeingPresentOnPage(ConstantLocators.DASHBOARD.value())){
			return true;
		}
		return false;
	}
}
