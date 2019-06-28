package po.event.pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.event.pages.EventDetailsContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class ShareEventPage extends BasePageObject implements PageObject {

    public ShareEventPage(WebDriver driver) {
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("ShareEventPage not loaded properly");
        }
    }

    //tested
    public EventDetailsContainerPage close(){
        this.clickOn(By.xpath("//div[@class=\"modal-header\"]/button[@class=\"close\"]"));
        this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
        return new EventDetailsContainerPage(this.getDriver());
    }

    public boolean isPageLoaded() {
        if(this.isElementPresentOnPage(ConstantLocators.ERROR.value())){
            this.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
        }
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.SHARE.value())){
            return true;
        }
        return false;
    }
}
