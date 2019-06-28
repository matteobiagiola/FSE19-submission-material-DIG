package po.site.components;

import custom_classes.SiteAddWidget;
import custom_classes.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class WidgetsListComponent extends BasePageObject implements PageComponent {

    public WidgetsListComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isOneElementPresent(){
        return this.getLinksPages().size() > 0;
    }

    public List<WebElement> getLinksPages(){
        return this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"check-item\"]"));
    }

    public List<String> getWidgetTexts(){
        List<WebElement> linksOrPages = this.getLinksToWidgets();
        List<String> linkTexts = new ArrayList<String>();
        for(WebElement linkOrPage: linksOrPages){
            String linkText = this.getText(linkOrPage);
            if(linkText != null && !linkText.isEmpty()){
                linkTexts.add(linkText);
            }else{
                throw new IllegalStateException("getWidgetTexts: linkText cannot be null or empty -> " + linkText);
            }
        }
        return linkTexts;
    }

    public List<WebElement> getLinksToWidgets(){
        return this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"check-item\"]/div/div[2]/a"));
    }

    public WebElement getLinkByName(Widget widget){
        List<WebElement> links = this.getLinksToWidgets();
        for(WebElement link: links){
            String linkText = this.getText(link);
            if(linkText.equals(widget.value())){
                return link;
            }
        }
        throw new IllegalStateException("Could not find any link associated with " + widget.value());
    }

    public boolean isWidgetPresent(Widget widget){
        return this.getWidgetTexts().contains(widget.value());
    }

    public void selectAll(){
        this.clickOn(By.xpath("//div[@class=\"pk-table-width-minimum\"]/input[@type=\"checkbox\" and @group]"));
    }

    public int getIndexByName(List<String> widgetTexts, Widget widget){
        for (int i = 0; i < widgetTexts.size(); i++) {
            if(widgetTexts.get(i).equals(widget.value())){
                return i;
            }
        }
        throw new IllegalStateException("getIndexByName: could not find widgetText given " + widget.value());
    }

    public void editWidget(Widget widget){
        WebElement link = this.getLinkByName(widget);
        this.clickOn(link);
    }

    public void selectWidget(Widget widget){
        this.typeInSearchBar(widget);
        List<WebElement> inputCheckboxes = this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"check-item\"]/div/div/input"));
        int index = this.getIndexByName(this.getWidgetTexts(), widget);
        WebElement inputCheckbox = inputCheckboxes.get(index);
        this.clickOn(inputCheckbox);
    }

    public void publish(){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-check pk-icon-hover\"]"));
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("publish notification not handled properly");
        }
    }

    public void unpublish(){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-block pk-icon-hover\"]"));
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("unpublish notification not handled properly");
        }
    }

    public void copy(){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-copy pk-icon-hover\"]"));
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("unpublish notification not handled properly");
        }
    }

    //Dropdown of move does not work
    /*public void move(WidgetMenus widgetMenu){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-move pk-icon-hover\"]"));
        this.selectOptionInDropdown(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]//ul[@class=\"uk-nav uk-nav-dropdown\"]"), widgetMenu.value(), "./li/a");
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("move notification not handled properly");
        }
    }*/

    public void delete(){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-delete pk-icon-hover\"]"));
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("delete notification not handled properly");
        }
    }

    public void typeInSearchBar(Widget widget){
        this.type(By.xpath("//div[@class=\"uk-search\"]/input"), widget.value());
    }

    public boolean isStatusActive(Widget widget){
        List<WebElement> linkOrPageStatus = this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[\"check-item\"]/div/div[3]/a"));
        int index = this.getIndexByName(this.getWidgetTexts(), widget);
        WebElement status = linkOrPageStatus.get(index);
        String classAttributeValue = this.getAttribute(status, "class");
        if(classAttributeValue.equals("pk-icon-circle-success")) return true;
        else if(classAttributeValue.equals("pk-icon-circle-danger")) return false;
        else throw new IllegalStateException("isStatusActive: unknown class attribute value " + classAttributeValue);
    }

    public void addWidget(SiteAddWidget siteAddWidget){
        this.clickOnSelenium(By.xpath("//button[@class=\"uk-button uk-button-primary\" and text()=\"Add Widget\"]"));
        this.selectOptionInDropdown(By.xpath("//div[@data-uk-dropdown]/div[@class=\"uk-dropdown uk-dropdown-small uk-dropdown-flip uk-dropdown-bottom\"]/ul"), siteAddWidget.value(), "./li/a");
    }
}
