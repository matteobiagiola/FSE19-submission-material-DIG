package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

// Maps to GoalsManagerPage

public class CreatePlanPage implements PageObject {

    public CreatePlanComponent createPlanComponent;

    /**
     * Page Object for CreatePlanComponent (state21)
     */
    public CreatePlanPage(WebDriver driver){
        this.createPlanComponent = new CreatePlanComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public CreatePlanBasicSettingsPage goToCreateNewPlanPage() {
        this.createPlanComponent.goToPlanBasicSettings();
        return new CreatePlanBasicSettingsPage(this.createPlanComponent.getDriver());
    }

    public ViewPlanPage viewPlanPage(Id id){
        if(this.createPlanComponent.planExists(id)){
            this.createPlanComponent.viewPlanReport(id);
            return new ViewPlanPage(this.createPlanComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": plan with id " + id.value + " doesn't exist");
        }
    }

    public CreatePlanBasicSettingsPage editGoal(Id id){
        if(this.createPlanComponent.planExists(id)){
            this.createPlanComponent.editPlan(id);
            return new CreatePlanBasicSettingsPage(this.createPlanComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": plan with id " + id.value + " doesn't exist");
        }
    }

    public boolean isPageLoaded() {
        if(this.createPlanComponent.waitForElementBeingPresentOnPage(ConstantLocators.GOALS_MANAGER.value())){
            return true;
        }
        return false;
    }
}
