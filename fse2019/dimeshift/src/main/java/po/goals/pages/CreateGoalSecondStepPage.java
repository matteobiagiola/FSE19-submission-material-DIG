package po.goals.pages;

import custom_classes.Amount;
import custom_classes.BreadcrumbActions;
import custom_classes.Date;
import custom_classes.NavbarActions;
import org.openqa.selenium.WebDriver;
import po_utils.PageObject;
import po.goals.components.GoalSettingsComponent;
import po.shared.components.BreadcrumbComponent;
import po.shared.components.NavbarComponent;
import po.wallets.pages.WalletsManagerPage;
import po_utils.ConstantLocators;

public class CreateGoalSecondStepPage implements PageObject {

    public GoalSettingsComponent goalSettingsComponent;
    public BreadcrumbComponent breadcrumbComponent;
    public NavbarComponent navbarComponent;
    public final boolean editGoal;

    public CreateGoalSecondStepPage(WebDriver driver, boolean editGoal){
        this.goalSettingsComponent = new GoalSettingsComponent(driver);
        this.breadcrumbComponent = new BreadcrumbComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        this.editGoal = editGoal;
        if(!isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
        }
    }

    public GoalDetailsPage addFurtherDetails(Amount toKeep, Date date){
        this.goalSettingsComponent.addFurtherDetails(toKeep,date);
        return new GoalDetailsPage(this.goalSettingsComponent.getDriver());
    }

    public CreateGoalFirstStepPage goBackToPreviousStep() {
        this.goalSettingsComponent.goBackToPreviousStep();
        return new CreateGoalFirstStepPage(this.goalSettingsComponent.getDriver(),this.editGoal);
    }

    /* Navbar */
    public WalletsManagerPage goToWalletsManagerPage(){
        return (WalletsManagerPage) this.navbarComponent.goTo(NavbarActions.WALLETS);
    }

    public GoalsManagerPage goToGoalsManagerPage(){
        return (GoalsManagerPage) this.navbarComponent.goTo(NavbarActions.GOALS);
    }

    /* Breadcrumb */
    public WalletsManagerPage goToHomeUserLoggedIn(){
        return (WalletsManagerPage) this.breadcrumbComponent.goTo(BreadcrumbActions.HOME);
    }


    @Override
    public boolean isPageLoaded(){
        if(this.goalSettingsComponent.waitForElementBeingPresentOnPage(ConstantLocators.CREATE_GOAL_SECOND_STEP.value())){
            return true;
        }
        return false;
    }
}
