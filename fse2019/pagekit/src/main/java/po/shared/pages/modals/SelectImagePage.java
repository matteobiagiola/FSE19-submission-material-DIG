package po.shared.pages.modals;

import custom_classes.FileNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.site.pages.AddEditPageContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelectImagePage extends BasePageObject implements PageObject {

    public String poCallee;
    public String previousPoCallee = "default";
    public final String linkPage = "SelectLinkPage";
    public final String addEditPage = "AddEditPageContainerPage";

    public SelectImagePage(WebDriver driver, String poCallee) {
        super(driver);
        this.poCallee = poCallee;
        if(!this.isPageLoaded()){
            throw new IllegalStateException("SelectImagePage not loaded properly");
        }
    }

    public SelectImagePage(WebDriver driver, String poCallee, String previousPoCallee) {
        this(driver, poCallee);
        this.previousPoCallee = previousPoCallee;
    }

    public PageObject selectFile(FileNames fileName){
        if(this.poCallee.equals(this.linkPage) && !this.previousPoCallee.equals("default")){
            List<WebElement> filesInTable = this.findElements(By.xpath("//div[@class=\"uk-modal uk-open\"]//div[@class=\"uk-form\"]//table/tbody/tr"));
            WebElement inputCheckbox = this.getIndexMatchingName(filesInTable, fileName);
            this.clickOn(inputCheckbox);
            this.clickOn(By.xpath("//div[@class=\"uk-modal uk-open\"]//button[@class=\"uk-button uk-button-primary\" and @type=\"button\"]"));
            return new SelectLinkPage(this.getDriver(), this.previousPoCallee);
        }else if(this.poCallee.equals(this.addEditPage)){
            List<WebElement> filesInTable = this.findElements(By.xpath("//div[@class=\"uk-modal uk-open\"]//div[@class=\"uk-form\"]//table/tbody/tr"));
            WebElement inputCheckbox = this.getIndexMatchingName(filesInTable, fileName);
            this.clickOn(inputCheckbox);
            this.clickOn(By.xpath("//div[@class=\"uk-modal uk-open\"]//button[@class=\"uk-button uk-button-primary\" and @type=\"button\"]"));
            return new AddEditPageContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("selectFile: unknown poCallee " + this.poCallee + " or previousCallee is default " + this.previousPoCallee);
        }
    }

    public PageObject cancelOperation(){
        if(this.poCallee.equals(this.linkPage) && !this.previousPoCallee.equals("default")){
            this.clickOn(By.xpath("//div[@class=\"uk-modal-dialog uk-modal-dialog-large\"]//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new SelectLinkPage(this.getDriver(), this.previousPoCallee);
        }else if(this.poCallee.equals(this.addEditPage)){
            this.clickOn(By.xpath("//div[@class=\"uk-modal-dialog uk-modal-dialog-large\"]//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new AddEditPageContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("selectFile: unknown poCallee " + this.poCallee + " or previousCallee is default " + this.previousPoCallee);
        }
    }

    public WebElement getIndexMatchingName(List<WebElement> filesInTable, FileNames fileName){
        for (int i = 0; i < filesInTable.size(); i++) {
            WebElement inputCheckbox = this.findElementJSByXPathStartingFrom(filesInTable.get(i), "./td/input");
            String fileNameInTable = this.getAttribute(inputCheckbox, "value");
            if(fileName.value().equals(fileNameInTable)){
                return inputCheckbox;
            }
        }
        throw new IllegalStateException("getIndexMatchingName: could not find an input checkbox for file name " + fileName.value());
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.SELECT_IMAGE.value())){
            return true;
        }
        return false;
    }
}
