package po_apogen;

import custom_classes.Dates;
import custom_classes.VisitDescriptions;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class VisitsPage implements PageObject {

	public VisitsComponent visitsComponent;

	/**
	 * Page Object for Visits (state40)
	 */
	public VisitsPage(WebDriver driver) {
		this.visitsComponent = new VisitsComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("VisitsPage not loaded properly");
		}
	}

	public OwnerInformationPage form(Dates date, VisitDescriptions visitDescription) {
		this.visitsComponent.form(date, visitDescription);
		return new OwnerInformationPage(this.visitsComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.visitsComponent
				.waitForElementBeingPresentOnPage(ConstantLocators.VISITS_PAGE.value())){
			return true;
		}
		return false;
	}
}
