package po.shared.pages.modals;

import custom_classes.Extension;
import custom_classes.FileNames;
import custom_classes.SitePages;
import custom_classes.View;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import po.site.pages.AddEditLinkContainerPage;
import po.site.pages.AddEditLoginWidgetContainerPage;
import po.site.pages.AddEditPageContainerPage;
import po.users.pages.UserSettingsContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

public class SelectLinkPage extends BasePageObject implements PageObject {

    public String poCallee;
    public By locatorElementToWaitFor;
    public final String prefix = "//div[@class=\"uk-modal-footer uk-text-right\"]";
    public final String userSettings = "UserSettingsContainerPage";
    public final String addEditLink = "AddEditLinkContainerPage";
    public final String addEditLogin = "AddEditLoginWidgetContainerPage";

    public SelectLinkPage(WebDriver webDriver, String poCallee){
        super(webDriver);
        this.poCallee = poCallee;
        if(!this.isPageLoaded()){
            throw new IllegalStateException("SelectLinkPage not loaded properly");
        }
    }

    public SelectLinkPage(WebDriver webDriver, String poCallee, By locatorElementToWaitFor){
        this(webDriver, poCallee);
        this.locatorElementToWaitFor = locatorElementToWaitFor;
    }

    public PageObject selectLinkPage(Extension extension, SitePages sitePage){
        if(extension.value().equals("Page") && this.isOptionPagePresent() && this.isOptionPresentInDropdown(By.id("form-link-page"), sitePage.value())){
            this.selectOptionInDropdown(By.id("form-link-page"), sitePage.value());
            this.clickOn(By.xpath(this.prefix + "/button[@type=\"submit\"]"));
            if(this.poCallee.equals(this.userSettings)){
                return new UserSettingsContainerPage(this.getDriver());
            }else if(poCallee.equals(this.addEditLink)){
                boolean textExpected = false;
                String text = "false";
                if(this.waitForElementThatChangesText(By.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"), textExpected, text)){
                    return new AddEditLinkContainerPage(this.getDriver());
                }else{
                    throw new IllegalStateException("update: text not handled properly");
                }
            }else if(poCallee.equals(this.addEditLogin)){
                boolean textExpected = false;
                String text = "false";
                if(this.waitForElementThatChangesText(this.locatorElementToWaitFor, textExpected, text)){
                    return new AddEditLoginWidgetContainerPage(this.getDriver());
                }else{
                    throw new IllegalStateException("update: text not handled properly");
                }
            }else{
                throw new IllegalStateException("selectLinkPage: unknown poCallee " + this.poCallee);
            }
        }else{
            throw new IllegalArgumentException("selectLinkPage: extension " + extension.value() + " is not Page or SitePage " + sitePage.value() + " is not among the options or there are no options");
        }
    }

    public SelectImagePage selectLinkStorage(Extension extension){
        if(extension.value().equals("Storage")){
            this.selectOptionInDropdown(By.id("form-style"), extension.value());
            this.clickOn(By.xpath("//div[@class=\"uk-modal-dialog\"]//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\"]"));
            String currentPoCallee = "SelectLinkPage";
            return new SelectImagePage(this.getDriver(), currentPoCallee, this.poCallee);
        }else{
            throw new IllegalArgumentException("selectLinkStorage: extension " + extension.value() + " is not Storage");
        }
    }

    public PageObject selectLinkUser(Extension extension, View view){
        if(extension.value().equals("User")){
            this.selectOptionInDropdown(By.id("form-style"), extension.value());
            this.selectOptionInDropdown(By.id("form-link-user"), view.value());
            this.clickOn(By.xpath(this.prefix + "/button[@type=\"submit\"]"));
            if(this.poCallee.equals(this.userSettings)){
                return new UserSettingsContainerPage(this.getDriver());
            }else if(poCallee.equals(this.addEditLink)){
                boolean textExpected = false;
                String text = "false";
                if(this.waitForElementThatChangesText(By.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"), textExpected, text)){
                    return new AddEditLinkContainerPage(this.getDriver());
                }else{
                    throw new IllegalStateException("update: text not handled properly");
                }
            }else if(poCallee.equals(this.addEditLogin)){
                boolean textExpected = false;
                String text = "false";
                if(this.waitForElementThatChangesText(this.locatorElementToWaitFor, textExpected, text)){
                    return new AddEditLoginWidgetContainerPage(this.getDriver());
                }else{
                    throw new IllegalStateException("update: text not handled properly");
                }
            }else{
                throw new IllegalStateException("selectLinkUser: unknown poCallee " + this.poCallee);
            }
        }else{
            throw new IllegalArgumentException("selectLinkUser: extension " + extension.value() + " is not User");
        }
    }

    public PageObject update(){
        if(this.poCallee.equals(this.userSettings)){
            this.clickOn(By.xpath(this.prefix + "/button[@type=\"submit\"]"));
            return new UserSettingsContainerPage(this.getDriver());
        }else if(poCallee.equals(this.addEditLink)){
            this.clickOn(By.xpath(this.prefix + "/button[@type=\"submit\"]"));
            boolean textExpected = false;
            String text = "false";
            if(this.waitForElementThatChangesText(By.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"), textExpected, text)){
                return new AddEditLinkContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("update: text not handled properly");
            }
        }else if(poCallee.equals(this.addEditLogin)){
            this.clickOn(By.xpath(this.prefix + "/button[@type=\"submit\"]"));
            boolean textExpected = false;
            String text = "false";
            if(this.waitForElementThatChangesText(this.locatorElementToWaitFor, textExpected, text)){
                return new AddEditLoginWidgetContainerPage(this.getDriver());
            }else{
                throw new IllegalStateException("update: text not handled properly");
            }
        }else{
            throw new IllegalStateException("update: unknown poCallee " + this.poCallee);
        }
    }

    public PageObject cancelOperation(){
        if(poCallee.equals(this.userSettings)){
            this.clickOn(By.xpath(this.prefix + "/button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new UserSettingsContainerPage(this.getDriver());
        }else if(poCallee.equals(this.addEditLink)){
            this.clickOn(By.xpath(this.prefix + "/button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new AddEditLinkContainerPage(this.getDriver());
        }else if(poCallee.equals(this.addEditLogin)){
            this.clickOn(By.xpath(this.prefix + "/button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
            return new AddEditLoginWidgetContainerPage(this.getDriver());
        }
        else{
            throw new IllegalStateException("Unknown poCallee " + this.poCallee);
        }
    }

    public boolean isOptionPagePresent(){
        if(this.waitForElementBeingPresentOnPage(By.xpath("//select[@id=\"form-link-page\"]/option"))){
            return true;
        }
        return false;
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.SELECT_LINK.value())
                && this.waitForElementBeingClickable(By.xpath(this.prefix + "/button[@type=\"submit\"]"))){
            return true;
        }
        return false;
    }
}
