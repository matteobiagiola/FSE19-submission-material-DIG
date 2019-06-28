package po.shared.pages;

import custom_classes.AddEditNavbarActions;
import custom_classes.AddEditNavbarPoCallees;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.site.pages.AddEditLinkContainerPage;
import po.site.pages.AddEditLoginWidgetContainerPage;
import po.site.pages.AddEditMenuWidgetContainerPage;
import po.site.pages.AddEditPageContainerPage;
import po.site.pages.AddEditTextWidgetContainerPage;
import po.site.pages.PagesContainerPage;
import po.site.pages.WidgetsContainerPage;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObject;
import po_utils.ParametricPageComponent;

import java.util.List;

public class AddEditNavbarComponent extends BasePageObject implements PageComponent {

    public final String page = "AddEditPageContainerPage";
    public final String link = "AddEditLinkContainerPage";
    public final String menu = "AddEditMenuWidgetContainerPage";
    public final String text = "AddEditTextWidgetContainerPage";
    public final String login = "AddEditLoginWidgetContainerPage";

    public AddEditNavbarComponent(WebDriver driver) {
        super(driver);
    }

    public PageObject goTo(AddEditNavbarActions addEditNavbarAction){
        if(addEditNavbarAction.value().equals(AddEditNavbarActions.PAGE_CONTENT.value())){
            this.clickOnNavbar("Content");
            return new AddEditPageContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.PAGE_META.value())){
            this.clickOnNavbar("Meta");
            return new AddEditPageContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.LINK_SETTINGS.value())){
            this.clickOnNavbar("Settings");
            return new AddEditLinkContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.LINK_META.value())){
            this.clickOnNavbar("Meta");
            return new AddEditLinkContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.MENU_SETTINGS.value())){
            this.clickOnNavbar("Settings");
            return new AddEditMenuWidgetContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.MENU_VISIBILITY.value())){
            this.clickOnNavbar("Visibility");
            return new AddEditMenuWidgetContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.TEXT_SETTINGS.value())){
            this.clickOnNavbar("Settings");
            return new AddEditTextWidgetContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.TEXT_VISIBILITY.value())){
            this.clickOnNavbar("Visibility");
            return new AddEditTextWidgetContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.LOGIN_SETTINGS.value())){
            this.clickOnNavbar("Settings");
            return new AddEditLoginWidgetContainerPage(this.getDriver());
        }else if(addEditNavbarAction.value().equals(AddEditNavbarActions.LOGIN_VISIBILITY.value())){
            this.clickOnNavbar("Visibility");
            return new AddEditLoginWidgetContainerPage(this.getDriver());
        }
        else{
            throw new IllegalArgumentException("goToAddEditNavbarComponent: wrong state for " + addEditNavbarAction.value());
        }
    }

    public PageObject close(AddEditNavbarPoCallees addEditNavbarPoCallee){
        if(addEditNavbarPoCallee.value().equals(AddEditNavbarPoCallees.PAGE_LINK.value())){
            this.clickOn(By.xpath("//form[@id]//a[@class=\"uk-button uk-margin-small-right\"]"));
            return new PagesContainerPage(this.getDriver());
        }else if(addEditNavbarPoCallee.value().equals(AddEditNavbarPoCallees.MENU_TEXT_LOGIN.value())){
            this.clickOn(By.xpath("//form[@id]//a[@class=\"uk-button uk-margin-small-right\"]"));
            return new WidgetsContainerPage(this.getDriver());
        }else{
            throw new IllegalStateException("close: wrong state for " + addEditNavbarPoCallee.value());
        }
    }

    public boolean isNavbarItemActive(String navbarItemName){
        List<WebElement> links = this.getNavbarLinks();
        for(WebElement link: links){
            String linkText = this.getText(link);
            if(linkText.equals(navbarItemName)){
                String classAttribute = this.getAttribute(link, "class");
                if(classAttribute != null && classAttribute.equals("uk-active")){
                    return true;
                }
            }
        }
        return false;
    }

    public void clickOnNavbar(String navbarActionName){
        List<WebElement> links = this.getNavbarLinks();
        for(WebElement link: links){
            String linkText = this.getText(link);
            if(linkText.equals(navbarActionName)){
                WebElement linkElement = this.findElementJSByXPathStartingFrom(link, "./a[text()=" + "\"" + navbarActionName + "\"]");
                this.clickOn(linkElement);
            }
        }
    }


    public List<WebElement> getNavbarLinks(){
        List<WebElement> links = this.findElements(By.xpath("//form[@id]/ul/li"));
        links.remove(links.size() - 1); //remove last element -> it seems an hidden dropdown
        return links;
    }

    public String getPageTitle(){
        WebElement title = this.findElement(By.xpath("//form[@id]//h2"));
        return this.getText(title);
    }

    public void save(){
        this.clickOnSelenium(By.xpath("//form[@id]//button[@class=\"uk-button uk-button-primary\"]"));
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("save: page/link/menu/text/login notification not handled properly");
        }
    }

    public boolean isAddEditSite(){
        return this.isElementPresentOnPage(By.id("site-edit"));
    }

    public boolean isAddEditPage(){
        return this.isElementPresentOnPage(By.xpath("//form[@id=\"site-edit\"]//textarea[@id=\"textareahtml\"]"));
    }

    public boolean isAddEditLink(){
        return this.isElementPresentOnPage(By.xpath("//form[@id=\"site-edit\"]//select[@id=\"form-type\"]"));
    }

    public boolean isAddEditMenu(){
        return this.isElementPresentOnPage(By.xpath("//form[@id=\"widget-edit\"]//select[@id=\"form-level\"]"));
    }

    public boolean isAddEditText(){
        return this.isElementPresentOnPage(By.xpath("//form[@id=\"widget-edit\"]//textarea[@id=\"textareahtml\"]"));
    }

    public boolean isAddEditLogin(){
        return this.isElementPresentOnPage(By.xpath("//form[@id=\"widget-edit\"]//label[text()=\"Login Redirect\"]"));
    }
}
