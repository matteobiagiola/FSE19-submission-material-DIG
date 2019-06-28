package po_apogen;

import custom_classes.Email;
import custom_classes.Name;
import custom_classes.UserPassword;
import custom_classes.UserStatus;
import custom_classes.Username;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AddUserPage implements PageObject {

	public AddUserComponent addUserComponent;

	/**
	 * Page Object for AddUser -> AddEditUserContainerPage
	 */
	public AddUserPage(WebDriver driver) {
		this.addUserComponent = new AddUserComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AddUserPage not loaded properly");
		}
	}

	public AnonymousPage goToRoles() {
		this.addUserComponent.goToRoles();
		return new AnonymousPage(this.addUserComponent.getDriver());
	}

	public UsersPage goToUser() {
		this.addUserComponent.goToUser();
		return new UsersPage(this.addUserComponent.getDriver());
	}

	public PermissionsPage goToPermissions() {
		this.addUserComponent.goToPermissions();
		return new PermissionsPage(this.addUserComponent.getDriver());
	}

	public UsersPage goToUsers() {
		this.addUserComponent.goToUsers();
		return new UsersPage(this.addUserComponent.getDriver());
	}

	public AddUserPage addUser(Username username, Name name, Email email, UserPassword userPassword, UserStatus userStatus){
		if(!this.addUserComponent.isEditUser()){
			this.addUserComponent.addUser(username, name, email, userPassword, userStatus);
			if(this.addUserComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))){
				return this;
			}else{
				return this;
			}
		}else{
			throw new IllegalArgumentException("addUser: you are in edit user mode");
		}
	}


	@Override
	public boolean isPageLoaded() {
		if(this.addUserComponent.waitForElementBeingPresentOnPage(ConstantLocators.ADD_EDIT_USER.value())){
			return true;
		}
		return false;
	}
}
