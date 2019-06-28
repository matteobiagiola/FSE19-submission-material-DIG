package po_apogen;

import custom_classes.WidgetLocation;
import custom_classes.WidgetUnit;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class EditLocationPage implements PageObject {

	public EditLocationComponent editLocationComponent;

	/**
	 * Page Object for EditComponentLocation (state197) --> DashboardContainerPage
	 */
	public EditLocationPage(WebDriver driver) {
		this.editLocationComponent = new EditLocationComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("EditComponentLocationPage not loaded properly");
		}
	}

	public DashboardPage form(WidgetLocation widgetLocation, WidgetUnit widgetUnit) {
		this.editLocationComponent.form(widgetLocation, widgetUnit);
		return new DashboardPage(this.editLocationComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.editLocationComponent.waitForElementBeingPresentOnPage(ConstantLocators.DASHBOARD.value())){
			return true;
		}
		return false;
	}
}
