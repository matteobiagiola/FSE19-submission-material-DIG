package po_apogen;

import custom_classes.Goals;
import custom_classes.WalletNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to CreateGoalFirstStepPage

public class CreatePlanBasicSettingsPage implements PageObject {

	public CreatePlanBasicSettingsComponent createPlanBasicSettingsComponent;

	/**
	 * Page Object for CreatePlanBasicSettingsComponent (state22)
	 */
	public CreatePlanBasicSettingsPage(WebDriver driver) {
		this.createPlanBasicSettingsComponent = new CreatePlanBasicSettingsComponent(driver);
	}

	public CreatePlanPage goToPlanPage() {
		this.createPlanBasicSettingsComponent.goToPlanPage();
		return new CreatePlanPage(this.createPlanBasicSettingsComponent.getDriver());
	}

	public CreatePlanAdvancedSettingsPage goToPlanAdvancedSettings(Goals goal, WalletNames walletName) {
		if(this.createPlanBasicSettingsComponent.isWalletPresent(walletName) != -1){
			this.createPlanBasicSettingsComponent.goToPlanAdvancedSettings(goal, walletName);
			return new CreatePlanAdvancedSettingsPage(this.createPlanBasicSettingsComponent.getDriver());
		}else{
			throw new IllegalStateException(this.getClass().getName() + ": cannot add goal to wallet");
		}
	}

	@Override
	public boolean isPageLoaded(){
		if(this.createPlanBasicSettingsComponent.waitForElementBeingPresentOnPage(ConstantLocators.CREATE_GOAL_FIRST_STEP.value())){
			return true;
		}
		return false;
	}
}
