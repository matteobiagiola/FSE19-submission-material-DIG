package po.retrospective.pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.login.pages.LoginContainerPage;
import po.retrospective.pages.RetrospectiveContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class MenuPage extends BasePageObject implements PageObject {

    public MenuPage(WebDriver driver) {
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("MenuPage not loaded properly");
        }
    }

    //tested
    public LoginContainerPage logout(){
        this.clickOn(By.xpath("((//body/div)[2]/div//button)[2]"));
        this.clickOutsideTheModal();
        return new LoginContainerPage(this.getDriver());
    }

    //tested
    public RetrospectiveContainerPage toggleSummaryMode(){
        if(!this.isSummaryModeOn()){
            this.clickOn(By.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
            this.clickOutsideTheModal();
            return new RetrospectiveContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("toggleSummaryMode: summary mode is already on");
        }
    }

    //tested
    public RetrospectiveContainerPage untoggleSummaryMode(){
        if(this.isSummaryModeOn()){
            this.clickOn(By.xpath("(//body/div)[2]/div//input[@label=\"Summary Mode\"]"));
            this.clickOutsideTheModal();
            return new RetrospectiveContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("toggleSummaryMode: summary mode is off");
        }
    }

    //tested
    public HomeContainerPage leaveSession(){
        this.clickOn(By.xpath("((//body/div)[2]/div//button)[1]"));
        this.clickOutsideTheModal();
        return new HomeContainerPage(this.getDriver());
    }

    public void clickOutsideTheModal(){
        this.clickOn(By.xpath("((//body/div)[2]/div/div)[1]")); //click outside the modal to close it
    }

    public boolean isSummaryModeOn(){
        return this.isElementPresentOnPage(By.xpath("(//body/div)[2]/div//span[@class=\"theme__on___3ocqI\"]"));
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.MENU.value())){
            return true;
        }
        return false;
    }
}
