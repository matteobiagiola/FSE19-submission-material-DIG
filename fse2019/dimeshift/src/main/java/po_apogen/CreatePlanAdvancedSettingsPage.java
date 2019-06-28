package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to CreateGoalSecondStepPage

public class CreatePlanAdvancedSettingsPage implements PageObject {

	public CreatePlanAdvancedSettingsComponent createPlanAdvancedSettingsComponent;

	/**
	 * Page Object for CreatePlanAdvancedSettings (no state correspondence in screenshot)
	 */
	public CreatePlanAdvancedSettingsPage(WebDriver driver) {
		this.createPlanAdvancedSettingsComponent = new CreatePlanAdvancedSettingsComponent(driver);
		if(!isPageLoaded()){
			throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
		}
	}

	public CreatePlanBasicSettingsPage goToPlanBasicSettingsPage() {
		this.createPlanAdvancedSettingsComponent.goToPlanBasicSettingsPage();
		return new CreatePlanBasicSettingsPage(this.createPlanAdvancedSettingsComponent.getDriver());
	}

	public ViewPlanPage confirmAndSave() {
		this.createPlanAdvancedSettingsComponent.confirmAndSave();
		return new ViewPlanPage(this.createPlanAdvancedSettingsComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded(){
		if(this.createPlanAdvancedSettingsComponent.waitForElementBeingPresentOnPage(ConstantLocators.CREATE_GOAL_SECOND_STEP.value())){
			return true;
		}
		return false;
	}
}
