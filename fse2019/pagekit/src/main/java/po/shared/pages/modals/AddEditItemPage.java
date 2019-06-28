package po.shared.pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po.site.pages.PagesContainerPage;
import po.users.pages.RolesContainerPage;
import po_utils.*;

public class AddEditItemPage extends BasePageObject implements PageObject {

    public final String roles = "RolesContainerPage";
    public final String pages = "PagesContainerPage";
    public final String xpathPrefix = "//div[@class=\"uk-modal-dialog\"]";
    public String poCallee;
    public FormValue formValueInput;
    public String expectingFailure = "default";

    public AddEditItemPage(WebDriver driver, String poCallee, FormValue formValueInput) {
        super(driver);
        this.poCallee = poCallee;
        this.formValueInput = formValueInput;
        if(!this.isPageLoaded()){
            throw new IllegalStateException("AddItemPage not loaded properly");
        }
    }

    public AddEditItemPage(WebDriver driver, String poCallee, FormValue formValueInput, String expectingFailure) {
        this(driver, poCallee, formValueInput);
        this.expectingFailure = expectingFailure;
    }

    public PageObject addItem(){
        if(this.poCallee.equals(this.roles) && this.expectingFailure.equals("Role already exists")){
            this.type(By.xpath(this.xpathPrefix + "//input"), this.formValueInput.value());
            this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]"));
            if(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))){
                return new RolesContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("addItem: unable to add role message not loaded properly");
            }
        }else if(this.poCallee.equals(this.roles) && this.expectingFailure.equals("default")){
            this.type(By.xpath(this.xpathPrefix + "//input"), this.formValueInput.value());
            this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]"));
            if(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                    && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))){
                return new RolesContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("addItem: roles notification not handled properly");
            }
        }else if(this.poCallee.equals(this.pages) && this.expectingFailure.equals("Menu already exists")) {
            this.type(By.xpath(this.xpathPrefix + "//input"), this.formValueInput.value());
            this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]"));
            if(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))){
                return new PagesContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("addItem: page notification not handled properly");
            }
        }else if(this.poCallee.equals(this.pages) && this.expectingFailure.equals("default")) {
            this.type(By.xpath(this.xpathPrefix + "//input"), this.formValueInput.value());
            this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]"));
            if(this.waitForElementBeingPresentOnPage(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li/a[text()=\"" + this.formValueInput.value() + "\"]"))){
                return new PagesContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("addItem: page notification not handled properly");
            }
        }else{
            throw new IllegalStateException("Unknown PO callee name: " + this.poCallee);
        }
    }

    public PageObject cancelOperation(){
        if(this.poCallee.equals(this.roles)){
            this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new RolesContainerPage(this.getDriver());
        }else if(this.poCallee.equals(this.pages)){
            this.clickOn(By.xpath(this.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new PagesContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("Unknown PO callee name: " + this.poCallee);
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.ADD_ITEM.value())){
            return true;
        }
        return false;
    }
}
