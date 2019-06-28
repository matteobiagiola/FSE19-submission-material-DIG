package po.shared.pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.dashboard.pages.DashboardContainerPage;
import po.site.pages.PagesContainerPage;
import po.site.pages.WidgetsContainerPage;
import po.users.pages.RolesContainerPage;
import po.users.pages.UserListContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

public class DeleteItemPage extends BasePageObject implements PageObject {

    public final String dashboard = "DashboardContainerPage";
    public final String userList = "UserListContainerPage";
    public final String roles = "RolesContainerPage";
    public final String pages = "PagesContainerPage";
    public String poCallee;
    public String expectingFailure = "default";

    public DeleteItemPage(WebDriver driver, String poCallee) {
        super(driver);
        this.poCallee = poCallee;
        if(!this.isPageLoaded()){
            throw new IllegalStateException("DeleteItemPage not loaded properly");
        }
    }

    public DeleteItemPage(WebDriver driver, String poCallee, String expectingFailure) {
        this(driver, poCallee);
        this.expectingFailure = expectingFailure;
    }

    public PageObject cancelOperation(){
        if(this.poCallee.equals(this.dashboard)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new DashboardContainerPage(this.getDriver());
        }else if (this.poCallee.equals(this.userList)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new UserListContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.roles)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new RolesContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.pages)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new PagesContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("Unknown PO callee name: " + this.poCallee);
        }
    }

    public PageObject confirmOperation(){
        if(this.poCallee.equals(this.dashboard)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
            this.waitForTimeoutExpires(500); //wait that element is removed from the DOM: better solution would be to pass the specific element to the constructor but I don't know if the element is always available in the container pages
            return new DashboardContainerPage(this.getDriver());
        }else if (this.poCallee.equals(this.userList) && this.expectingFailure.equals("default")){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
            this.waitForTimeoutExpires(500);
            return new UserListContainerPage(this.getDriver());
        }else if (this.poCallee.equals(this.userList) && this.expectingFailure.equals("Delete admin user")){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
            if(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))){
                //PageObjectLogging.logInfo("Unable to delete yourself message");
                return new UserListContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("ConfirmOperation: unable to delete yourself message not loaded properly");
            }
        }else if(this.poCallee.equals(this.roles)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
            this.waitForTimeoutExpires(500);
            return new RolesContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.pages)){
            this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
            this.waitForTimeoutExpires(500);
            return new PagesContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("Unknown PO callee name: " + this.poCallee);
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.DELETE_ITEM.value())){
            return true;
        }
        return false;
    }
}
