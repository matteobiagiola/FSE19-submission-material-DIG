package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class UsersComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Users (state5) --> UsersListContainerPage
	 */
	public UsersComponent(WebDriver driver) {
		super(driver);
	}

	public void goToEdit(Id id) {
		this.clickOn(By.xpath("//DIV[@id=\"users\"]/DIV[2]/TABLE[1]/TBODY[1]/TR/TD/A[@href=\"/pagekit/index.php/admin/user/edit?id=" + id.value + "\"]"));
	}

	public void add_user() {
		this.clickOn(By.xpath("//a[@class=\"uk-button uk-button-primary\"]"));
	}
	
	public void goToPermissions() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/permissions\"]"));
	}
	
	public void goToAnonymous() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/roles\"]"));
	}


	/*-----------------------------------------------------------*/

	public List<WebElement> getUsersInList(){
		List<WebElement> users = this.findElements(By.xpath("//table/tbody/tr[@class=\"check-item\" or @class=\"check-item uk-active\"]"));
		return users;
	}

	public boolean isAdminUser(WebElement user){
		WebElement usernameElement = this.findElementJSByXPathStartingFrom(user, ".//td[3]/div");
		String usernameText = this.getText(usernameElement);
		if(usernameText.equals("admin")) return true;
		return false;
	}

}
