package po.shared.components;

import custom_classes.BreadcrumbActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.MyProperties;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.ParametricPageComponent;
import po.goals.pages.GoalsManagerPage;
import po.wallets.pages.WalletsManagerPage;

public class BreadcrumbComponent extends BasePageObject implements PageComponent, ParametricPageComponent {

    public BreadcrumbComponent(WebDriver driver){
        super(driver);
    }

    public PageObject goTo(BreadcrumbActions breadcrumbAction){
        if(breadcrumbAction.value().equals(BreadcrumbActions.HOME.value()) && this.isUserLogged()){
            this.clickOn(By.xpath("//*[@id]/div/ul/li[1]/a[@href=\"http://localhost:" + MyProperties.getInstance().getProperty("appPort") + "\"]"));
            return new WalletsManagerPage(this.getDriver());
            /*if(this.waitForElementBeingPresentOnPage(ConstantLocators.WALLETS.value(),true)){
                return new WalletsManagerPage(this.getDriver());
            }else{
                throw new IllegalStateException(this.getClass().getName() + ": timeout expired to display wallets page");
            }*/
        }else if(breadcrumbAction.value().equals(BreadcrumbActions.WALLETS.value())
                && this.isElementPresent(breadcrumbAction) && this.isUserLogged()){
            this.clickOn(By.xpath("//*[@id]/div/ul/li[2]/a[@href=\"http://localhost:" + MyProperties.getInstance().getProperty("appPort") + "/wallets\"]"));
            return new WalletsManagerPage(this.getDriver());
            /*if(this.waitForElementBeingPresentOnPage(ConstantLocators.WALLETS.value(), true)){
                return new WalletsManagerPage(this.getDriver());
            }else{
                throw new IllegalStateException(this.getClass().getName() + ": timeout expired to display wallets page");
            }*/
        }else if(breadcrumbAction.value().equals(BreadcrumbActions.GOALS.value())
                && this.isElementPresent(breadcrumbAction) && this.isUserLogged()) {
            this.clickOn(By.xpath("//*[@id]/div/ul/li[2]/a[@href=\"http://localhost:" + MyProperties.getInstance().getProperty("appPort") + "/plans\"]"));
            return new GoalsManagerPage(this.getDriver());
            /*if (this.waitForElementBeingPresentOnPage(ConstantLocators.GOALS.value(), true)) {
                return new GoalsManagerPage(this.getDriver());
            } else {
                throw new IllegalStateException(this.getClass().getName() + ": timeout expired to display goals page");
            }*/
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": wrong state");
        }
    }

    public boolean isElementPresent(BreadcrumbActions breadcrumbAction){
        if(breadcrumbAction.value().equals(BreadcrumbActions.WALLETS.value())){
            return this.isElementPresentOnPage(By.xpath("//*[@id]/div/ul/li[2]/a[@href=\"http://localhost:" + MyProperties.getInstance().getProperty("appPort") + "/wallets\"]"));
        }else if(breadcrumbAction.value().equals(BreadcrumbActions.GOALS.value())){
            return this.isElementPresentOnPage(By.xpath("//*[@id]/div/ul/li[2]/a[@href=\"http://localhost:" + MyProperties.getInstance().getProperty("appPort") + "/plans\"]"));
        }
        return false;
    }

    public boolean isUserLogged(){
        return this.isElementPresentOnPage(By.linkText("Log Out"));
    }

}