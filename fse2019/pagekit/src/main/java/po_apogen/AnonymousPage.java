package po_apogen;

import custom_classes.UserRoles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AnonymousPage implements PageObject {

	public AnonymousComponent anonymousComponent;

	/**
	 * Page Object for Anonymous (state108) --> RolesContainerPage
	 */
	public AnonymousPage(WebDriver driver) {
		this.anonymousComponent = new AnonymousComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AnonymousPage not loaded properly");
		}
	}

	public AnonymousPage(WebDriver driver, String wait) {
		this.anonymousComponent = new AnonymousComponent(driver);
		this.anonymousComponent.waitForTimeoutExpires(1000);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AnonymousPage not loaded properly");
		}
	}

	public AnonymousPage(WebDriver driver, boolean roleAdded) {
		this.anonymousComponent = new AnonymousComponent(driver);
		if(this.anonymousComponent.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
				&& this.anonymousComponent.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))){
		}else{
			throw new IllegalStateException("new role notifications not handled properly");
		}
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AnonymousPage not loaded properly");
		}
	}

	public AdministratorPage goToAdministrator() {
		this.anonymousComponent.goToAdministrator();
		return new AdministratorPage(this.anonymousComponent.getDriver());
	}

	public AddRolePage goToAddRole(UserRoles userRole) {
		if(!this.anonymousComponent.isRolePresent(userRole)){
			this.anonymousComponent.goToAddRole();
			return new AddRolePage(this.anonymousComponent.getDriver(), userRole);
		}else{
			throw new IllegalArgumentException("goToAddRole: user role " + userRole.value() + " already exists");
		}
	}

	public EditRolePage goToEditRole(UserRoles userRoleToEdit, UserRoles newUserRole){
		if(this.anonymousComponent.isRolePresent(userRoleToEdit)
				&& this.anonymousComponent.isRoleEditable(userRoleToEdit)
				&& !this.anonymousComponent.isRolePresent(newUserRole)){
			this.anonymousComponent.goToEditRole(userRoleToEdit);
			return new EditRolePage(this.anonymousComponent.getDriver(), newUserRole);
		}else{
			throw new IllegalArgumentException("goToEditRole: user role " + userRoleToEdit.value() + " is not editable, it does not exist or new user role " + newUserRole.value() + " already exists.");
		}
	}


	public DeleteRolePage deleteUserRole(UserRoles userRoleToDelete){
		if(this.anonymousComponent.isRolePresent(userRoleToDelete) && this.anonymousComponent.isRoleEditable(userRoleToDelete)){
			this.anonymousComponent.goToDeleteRole(userRoleToDelete);
			return new DeleteRolePage(this.anonymousComponent.getDriver());
		}else{
			throw new IllegalArgumentException("deleteUserRole: user role " + userRoleToDelete.value() + " is not present or not editable");
		}
	}

	// added
	public DashboardPage goToHome(){
		this.anonymousComponent.clickOn(By.xpath("//i[@class=\"tm-icon-menu\"]"));
		this.anonymousComponent.clickOn(By.xpath("//img[@alt=\"Dashboard\"]"));
		return new DashboardPage(this.anonymousComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.anonymousComponent.waitForElementBeingPresentOnPage(ConstantLocators.ROLES.value())){
			return true;
		}
		return false;
	}
}
