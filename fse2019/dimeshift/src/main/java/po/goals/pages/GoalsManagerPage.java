package po.goals.pages;

import custom_classes.BreadcrumbActions;
import custom_classes.Id;
import custom_classes.NavbarActions;
import org.openqa.selenium.WebDriver;
import po_utils.PageObject;
import po.goals.components.GoalsListComponent;
import po.shared.components.BreadcrumbComponent;
import po.shared.components.NavbarComponent;
import po.shared.pages.modals.ConfirmationPage;
import po.wallets.pages.WalletsManagerPage;
import po_utils.ConstantLocators;

public class GoalsManagerPage implements PageObject {

    public GoalsListComponent goalsListComponent;
    public NavbarComponent navbarComponent;
    public BreadcrumbComponent breadcrumbComponent;

    public GoalsManagerPage(WebDriver driver){
        this.goalsListComponent = new GoalsListComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        this.breadcrumbComponent = new BreadcrumbComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + " page object not loaded properly");
        }
    }

    public ConfirmationPage removeGoal(Id id){
        if(this.goalsListComponent.goalExist(id)){
            this.goalsListComponent.removeGoal(id);
            return new ConfirmationPage(this.goalsListComponent.getDriver(),"GoalsManagerPage");
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": goal with id " + id.value + " doesn't exist");
        }
    }

    public GoalDetailsPage viewGoalReport(Id id){
        if(this.goalsListComponent.goalExist(id)){
            this.goalsListComponent.viewGoalReport(id);
            return new GoalDetailsPage(this.goalsListComponent.getDriver());
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": goal with id " + id.value + " doesn't exist");
        }
    }

    public CreateGoalFirstStepPage editGoal(Id id){
        if(this.goalsListComponent.goalExist(id)){
            this.goalsListComponent.editGoal(id);
            boolean editGoal = true;
            return new CreateGoalFirstStepPage(this.goalsListComponent.getDriver(), editGoal);
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + ": goal with id " + id.value + " doesn't exist");
        }
    }

    public CreateGoalFirstStepPage createNew(){
        this.goalsListComponent.createNew();
        boolean editGoal = false;
        return new CreateGoalFirstStepPage(this.goalsListComponent.getDriver(), editGoal);
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

    public boolean isPageLoaded() {
        if(this.goalsListComponent.waitForElementBeingPresentOnPage(ConstantLocators.GOALS_MANAGER.value())){
            return true;
        }
        return false;
    }
}
