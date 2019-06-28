package po_apogen;

import custom_classes.Email;
import custom_classes.Name;
import custom_classes.UserPassword;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class EditUserPage implements PageObject {

	public EditUserComponent editUserComponent;

	/**
	 * Page Object for EditUser (state4) --> AddEditUserContainerPage
	 */
	public EditUserPage(WebDriver driver) {
		this.editUserComponent = new EditUserComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditUserPage not loaded properly");
		}
	}

	public EditUserPage(WebDriver driver, boolean edited) {
		this.editUserComponent = new EditUserComponent(driver);
		if(this.editUserComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
				&& this.editUserComponent.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))){
		}else{
			throw new IllegalStateException("edit user notifications not handled properly");
		}
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditUserPage not loaded properly");
		}
	}

	public AnonymousPage goToRoles() {
		this.editUserComponent.goToRoles();
		return new AnonymousPage(this.editUserComponent.getDriver());
	}

	public UsersPage goToUser() {
		this.editUserComponent.goToUser();
		return new UsersPage(this.editUserComponent.getDriver());
	}

	public PermissionsPage goToPermissions() {
		this.editUserComponent.goToPermissions();
		return new PermissionsPage(this.editUserComponent.getDriver());
	}

	public UsersPage goToUsers() {
		this.editUserComponent.goToUsers();
		return new UsersPage(this.editUserComponent.getDriver());
	}

	public EditUserPage user_edit(Username username, Name name, Email email, UserPassword userPassword){
		if(this.editUserComponent.isEditUser()){
			this.editUserComponent.user_edit(username, name, email, userPassword);
			return new EditUserPage(this.editUserComponent.getDriver(), true);
		}else{
			throw new IllegalArgumentException("user_edit: you are not in edit user mode");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.editUserComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_USER.value())){
			return true;
		}
		return false;
	}
}
