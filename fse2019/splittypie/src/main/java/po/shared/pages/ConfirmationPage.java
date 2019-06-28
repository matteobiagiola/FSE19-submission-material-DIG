package po.shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.event.pages.AddEditEventContainerPage;
import po.event.pages.AddEditTransactionContainerPage;
import po.event.pages.EventDetailsContainerPage;
import po.home.pages.HomePageContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class ConfirmationPage extends BasePageObject implements PageObject {

    public String poCallee;
    public String previousPoCallee;
    public String tripName;
    public final String addEditEvent = "AddEditEventContainerPage";
    public final String eventDetails = "EventDetailsContainerPage";
    public final String home = "HomePageContainerPage";
    public final String addEditTransaction = "AddEditTransactionContainerPage";

    public ConfirmationPage(WebDriver driver, String poCallee) {
        super(driver);
        this.poCallee = poCallee;
        if(!this.isPageLoaded()){
            throw new IllegalStateException("SettleUpPage not loaded properly");
        }
    }

    public ConfirmationPage(WebDriver driver, String poCallee, String previousPoCallee, String tripName){
        this(driver, poCallee);
        this.previousPoCallee = previousPoCallee;
        this.tripName = tripName;
    }


    //tested
    public PageObject confirm(){
        if(this.poCallee.equals(this.addEditEvent)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new HomePageContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.eventDetails)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new EventDetailsContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.home)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new HomePageContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.addEditTransaction)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new EventDetailsContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("confirm: unknown po callee " + this.poCallee);
        }
    }

    //tested
    public PageObject cancel(){
        if(this.poCallee.equals(this.addEditEvent)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new AddEditEventContainerPage(this.getDriver(), this.previousPoCallee, this.tripName);
        }else if(this.poCallee.equals(this.eventDetails)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new EventDetailsContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.home)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new HomePageContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.addEditTransaction)){
            this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
            this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
            return new AddEditTransactionContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("confirm: unknown po callee " + this.poCallee);
        }
    }


    public boolean isPageLoaded() {
        if(this.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.CONFIRMATION.value())){
            return true;
        }
        return false;
    }
}
