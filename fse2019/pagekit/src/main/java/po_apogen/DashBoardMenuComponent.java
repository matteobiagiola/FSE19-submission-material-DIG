package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class DashBoardMenuComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for DashBoardMenu (state223) --> DashboardContainerPage
	 */
	public DashBoardMenuComponent(WebDriver driver) {
		super(driver);
	}

	public void goToDashboard() {
		this.clickOn(By.xpath("//img[@alt=\"Dashboard\"]"));
	}

	public void goToUsers() {
		this.clickOn(By.xpath("//img[@alt=\"Users\"]"));
	}

}
