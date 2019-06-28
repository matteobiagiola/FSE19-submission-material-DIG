package po.goals.pages;

import custom_classes.BreadcrumbActions;
import custom_classes.NavbarActions;
import org.openqa.selenium.WebDriver;
import po_utils.PageObject;
import po.goals.components.GoalDetailsComponent;
import po.shared.components.BreadcrumbComponent;
import po.shared.components.NavbarComponent;
import po.wallets.pages.WalletsManagerPage;
import po_utils.ConstantLocators;

public class GoalDetailsPage implements PageObject {

    public GoalDetailsComponent goalDetailsComponent;
    public BreadcrumbComponent breadcrumbComponent;
    public NavbarComponent navbarComponent;

    public GoalDetailsPage(WebDriver driver){
        this.goalDetailsComponent = new GoalDetailsComponent(driver);
        this.breadcrumbComponent = new BreadcrumbComponent(driver);
        this.navbarComponent = new NavbarComponent(driver);
        if(!isPageLoaded()){
            throw new IllegalStateException(this.getClass().getName() + ": page object not loaded properly");
        }
    }

    public GoalDetailsPage refreshStats(){
        this.goalDetailsComponent.refreshStats();
        return this;
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

    public GoalsManagerPage goToPlanYourExpenses(){
        return (GoalsManagerPage) this.breadcrumbComponent.goTo(BreadcrumbActions.GOALS);
    }

    @Override
    public boolean isPageLoaded() {
        if(this.goalDetailsComponent.waitForElementBeingPresentOnPage(ConstantLocators.GOAL_DETAILS.value())){
            return true;
        }
        return false;
    }
}
