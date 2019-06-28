package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class PermissionsComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Permissions (state9) --> PermissionsContainerPage
	 */
	public PermissionsComponent(WebDriver driver) {
		super(driver);
	}

	public void goToUsers() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user\"]"));
	}

	public void goToPermissions() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/permissions\"]"));
	}

	public void goToAnonymous() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/roles\"]"));
	}

}
