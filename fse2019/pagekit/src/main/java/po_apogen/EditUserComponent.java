package po_apogen;

import custom_classes.Email;
import custom_classes.Name;
import custom_classes.UserPassword;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class EditUserComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for EditUser (state4) --> AddEditUserContainerPage
	 */
	public EditUserComponent(WebDriver driver) {
		super(driver);
	}

	public void goToRoles() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/roles\"]"));
	}

	public void goToUser() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user\"]"));
	}

	public void goToPermissions() {
		this.clickOn(By.xpath("//ul[@class=\"uk-navbar-nav\"]//a[@href=\"/pagekit/index.php/admin/user/permissions\"]"));
	}

	public void goToUsers() {
		this.clickOn(By.xpath("//a[@class=\"uk-button uk-margin-small-right\"]"));
	}

	public void user_edit(Username username, Name name, Email email, UserPassword userPassword){
		this.typeJS(By.id("form-username"), username.value());
		this.typeJS(By.id("form-name"), name.value());
		this.typeJS(By.id("form-email"), email.value());
		this.clickOn(By.xpath("//a[text()=\"Change password\"]"));
		//this.clickOn(By.xpath("//a[text()=\"Show\"]"));
		this.typeJS(By.id("form-password"), userPassword.value());
		this.clickOn(By.xpath("//button[@type=\"submit\"]"));
	}

	/*-----------------------------------------------------------------------------*/

	public boolean isEditUser(){
		String textTitleForm = this.getText(this.findElement(By.xpath("//form[@id=\"user-edit\"]//h2")));
		if(textTitleForm.equals("Edit User")) return true;
		return false;
	}

}
