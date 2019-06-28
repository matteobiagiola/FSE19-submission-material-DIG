package po.goals.pages;

import custom_classes.BreadcrumbActions;
import custom_classes.Goals;
import custom_classes.NavbarActions;
import custom_classes.WalletNames;
import org.openqa.selenium.WebDriver;
import po_utils.PageObject;
import po.goals.components.GoalBasicSettingsComponent;
import po.shared.components.BreadcrumbComponent;
import po.shared.components.NavbarComponent;
import po.wallets.pages.WalletsManagerPage;
import po_utils.ConstantLocators;

public class CreateGoalFirstStepPage implements PageObject {

    public GoalBasicSettingsComponent goalBasicSettingsComponent;
    public BreadcrumbComponent breadcrumbComponent;
    public NavbarComponent navbarComponent;
    public final boolean editGoal;

    public CreateGoalFirstStepPage(WebDriver driver, boolean editGoal){
        this.goalBasicSettingsComponent = new GoalBasicSettingsComponent(driver,editGoal);
        this.breadcrumbComponent = new BreadcrumbComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        this.editGoal = editGoal;
        if(!isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
        }
    }

    public CreateGoalSecondStepPage addGoalToWallet(Goals goal, WalletNames walletName){
        if(!this.goalBasicSettingsComponent.isEdit() && this.goalBasicSettingsComponent.isWalletPresent(walletName) != -1){
            this.goalBasicSettingsComponent.addGoalToWallet(goal,walletName);
            return new CreateGoalSecondStepPage(this.goalBasicSettingsComponent.getDriver(),this.editGoal);
        }else{
            /*PageObjectLogging.logInfoPreconditionFailure("addGoalToWallet",
                    Arrays.asList("Goal", "WalletName"), Arrays.asList(goal.value(), walletName.value()),
                    Arrays.asList("isEdit", "isWalletPresent"), Arrays.asList(!this.goalBasicSettingsComponent.isEdit(), this.goalBasicSettingsComponent.isWalletPresent(walletName) != -1));*/
            throw new IllegalStateException(this.getClass().getName() + ": cannot add goal to wallet");
        }
    }

    public CreateGoalSecondStepPage editGoal(Goals goal){
        if(this.goalBasicSettingsComponent.isEdit()){
            this.goalBasicSettingsComponent.editGoal(goal);
            return new CreateGoalSecondStepPage(this.goalBasicSettingsComponent.getDriver(),this.editGoal);
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": cannot add goal to wallet");
        }
    }

    public GoalsManagerPage goBack(){
        this.goalBasicSettingsComponent.goBack();
        return new GoalsManagerPage(this.goalBasicSettingsComponent.getDriver());
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
        if(this.goalBasicSettingsComponent.waitForElementBeingPresentOnPage(ConstantLocators.CREATE_GOAL_FIRST_STEP.value())){
            return true;
        }
        return false;
    }
}
