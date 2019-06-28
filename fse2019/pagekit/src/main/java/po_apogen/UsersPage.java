package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.List;

public class UsersPage implements PageObject {

	public UsersComponent usersComponent;

	/**
	 * Page Object for Users (state5) --> UsersListContainerPage
	 */
	public UsersPage(WebDriver driver) {
		this.usersComponent = new UsersComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("UsersPage not loaded properly");
		}
	}

	public EditUserPage goToEdit(Id id) {
		List<WebElement> users = this.usersComponent.getUsersInList();
		if(id.value - 1 < users.size()){
			this.usersComponent.goToEdit(id);
			return new EditUserPage(this.usersComponent.getDriver());
		}else{
			throw new IllegalArgumentException("User with id " + id.value + " does not exist");
		}
	}

	public AddUserPage add_user() {
		this.usersComponent.add_user();
		return new AddUserPage(this.usersComponent.getDriver());
	}
	
	public PermissionsPage goToPermissions() {
		this.usersComponent.goToPermissions();
		return new PermissionsPage(this.usersComponent.getDriver());
	}
	
	public AnonymousPage goToAnonymous() {
		this.usersComponent.goToAnonymous();
		return new AnonymousPage(this.usersComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.usersComponent.waitForElementBeingPresentOnPage(ConstantLocators.USER_LIST.value())){
			return true;
		}
		return false;
	}
}
