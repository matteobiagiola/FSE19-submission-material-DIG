package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.ConstantLocators;
import po_utils.PageObject;

import java.util.List;

public class DashboardPage implements PageObject {

	public DashboardComponent dashboardComponent;

	/**
	 * Page Object for Dashboard (state1) --> DashboardContainerPage
	 */
	public DashboardPage(WebDriver driver) {
		this.dashboardComponent = new DashboardComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("DashboardPage not loaded properly");
		}
	}

	public DashBoardMenuPage goToDashBoardMenu() {
		this.dashboardComponent.goToDashBoardMenu();
		return new DashBoardMenuPage(this.dashboardComponent.getDriver());
	}

	/* Location. */
	public EditLocationPage goToEditComponentLocation(Id id) {
		List<WebElement> widgetsOnPage = this.dashboardComponent.getWidgetsOnPage();
		if(id.value - 1 < widgetsOnPage.size()
				&& this.dashboardComponent.isLocationWidget(widgetsOnPage.get(id.value - 1))
				&& !this.dashboardComponent.isWidgetFormOpen(widgetsOnPage.get(id.value - 1))){
			this.dashboardComponent.goToEditComponentLocation(widgetsOnPage.get(id.value - 1));
			return new EditLocationPage(this.dashboardComponent.getDriver());
		}else{
			throw new IllegalArgumentException("WebElement with id " + id.value
					+ " is not a location widget or the widget form is open");
		}
	}

	public DeleteWidgetPage deletePagekit(Id id) {
		List<WebElement> widgetsOnPage = this.dashboardComponent.getWidgetsOnPage();
		if(id.value - 1 < widgetsOnPage.size()
				&& this.dashboardComponent.isFeedWidget(widgetsOnPage.get(id.value - 1))
				&& !this.dashboardComponent.isWidgetFormOpen(widgetsOnPage.get(id.value - 1))){
			this.dashboardComponent.deletePagekit(widgetsOnPage.get(id.value - 1));
			return new DeleteWidgetPage(this.dashboardComponent.getDriver());
		}else{
			throw new IllegalArgumentException("WebElement with id " + id.value + " is not a widget");
		}
	}

	public EditUserPage goToEditUser() {
		this.dashboardComponent.goToEditUser();
		return new EditUserPage(this.dashboardComponent.getDriver());
	}

	public EditRegisteredUserPage goToEditRegisteredUser(Id id) {
		List<WebElement> widgetsOnPage = this.dashboardComponent.getWidgetsOnPage();
		if(id.value - 1 < widgetsOnPage.size()
				&& this.dashboardComponent.isUserWidget(widgetsOnPage.get(id.value - 1))
				&& !this.dashboardComponent.isWidgetFormOpen(widgetsOnPage.get(id.value - 1))){
			this.dashboardComponent.goToEditRegisteredUser(widgetsOnPage.get(id.value - 1));
			return new EditRegisteredUserPage(this.dashboardComponent.getDriver());
		}else{
			throw new IllegalArgumentException("WebElement with id " + id.value + " is not a user widget or the widget form is open");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.dashboardComponent.waitForElementBeingPresentOnPage(ConstantLocators.DASHBOARD.value())){
			return true;
		}
		return false;
	}
}
