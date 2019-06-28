package po_apogen;

import custom_classes.WidgetNumberOfUsers;
import custom_classes.WidgetTotalUser;
import custom_classes.WidgetUserDisplay;
import custom_classes.WidgetUserType;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class EditRegisteredUserPage implements PageObject {

	public EditRegisteredUserComponent editRegisteredUserComponent;

	/**
	 * Page Object for EditRegisteredUser (state114) --> DashboardContainerPage
	 */
	public EditRegisteredUserPage(WebDriver driver) {
		this.editRegisteredUserComponent = new EditRegisteredUserComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditRegisteredUserPage not loaded properly");
		}
	}

	public DashboardPage form(WidgetUserType widgetUserType, WidgetUserDisplay widgetUserDisplay,
							  WidgetTotalUser widgetTotalUser, WidgetNumberOfUsers widgetNumberOfUsers) {
		this.editRegisteredUserComponent.form(widgetUserType, widgetUserDisplay, widgetTotalUser, widgetNumberOfUsers);
		return new DashboardPage(this.editRegisteredUserComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.editRegisteredUserComponent.waitForElementBeingPresentOnPage(ConstantLocators.DASHBOARD.value())){
			return true;
		}
		return false;
	}
}
