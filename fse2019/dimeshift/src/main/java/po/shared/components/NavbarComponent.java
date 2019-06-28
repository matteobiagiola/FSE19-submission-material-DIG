package po.shared.components;

import custom_classes.NavbarActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.ParametricPageComponent;
import po.goals.pages.GoalsManagerPage;
import po.wallets.pages.WalletsManagerPage;

/*
* SettingsPage disabled for the moment. There are two functionalities: change password and remove account.
* Change password doesn't work as expected; it says password successfully changed but it is impossible to login with the new password.
* The remove account functionalities is not interesting to test since a code to remove the account is sent to the email of the account itself.
* */
public class NavbarComponent extends BasePageObject implements PageComponent, ParametricPageComponent {

    public NavbarComponent(WebDriver driver){
        super(driver);
    }

    public PageObject goTo(NavbarActions navbarPageObject){
        if(navbarPageObject.value().equals(NavbarActions.WALLETS.value()) && this.isUserLogged()){
            this.clickOn(By.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[2]/a[1]"));
            return new WalletsManagerPage(this.getDriver());
            /*if(this.waitForElementBeingPresentOnPage(ConstantLocators.WALLETS.value(), true)){
                return new WalletsManagerPage(this.getDriver());
            }else{
                throw new IllegalStateException(this.getClass().getName() + ": timeout expired to display wallets page");
            }*/
        }else if(navbarPageObject.value().equals(NavbarActions.GOALS.value()) && this.isUserLogged()){
            this.clickOn(By.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[3]/a[1]"));
            return new GoalsManagerPage(this.getDriver());
            /*if(this.waitForElementBeingPresentOnPage(ConstantLocators.GOALS.value(), true)){
                return new GoalsManagerPage(this.getDriver());
            }else{
                throw new IllegalStateException(this.getClass().getName() + ": timeout expired to display goals page");
            }*/
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": wrong state");
        }
    }

    public void goToRegisterPage(){
        this.clickOn(By.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[6]/a"));
    }

    public boolean isUserLogged(){
        return this.isElementPresentOnPage(By.linkText("Log Out"));
    }
}