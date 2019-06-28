package po.site.components;

import custom_classes.SiteAddPage;
import custom_classes.SiteLinkOrPage;
import custom_classes.SiteMenus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class PagesListComponent extends BasePageObject implements PageComponent {

    public PagesListComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isOneElementPresent(){
        return this.getLinksPages().size() > 0;
    }

    public List<WebElement> getLinksPages(){
        return this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"uk-nestable-item check-item\"]"));
    }

    public List<String> getLinkOrPageTexts(){
        List<WebElement> linksOrPages = this.getLinksToLinksAndPages();
        List<String> linkTexts = new ArrayList<String>();
        for(WebElement linkOrPage: linksOrPages){
            String linkText = this.getText(linkOrPage);
            if(linkText != null && !linkText.isEmpty()){
                linkTexts.add(linkText);
            }else{
                throw new IllegalStateException("getLinkOrPageTexts: linkText cannot be null or empty -> " + linkText);
            }
        }
        return linkTexts;
    }

    public List<WebElement> getLinksToLinksAndPages(){
        return this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"uk-nestable-item check-item\"]/div/div[3]/a"));
    }

    public WebElement getLinkByName(SiteLinkOrPage siteLinkOrPage){
        List<WebElement> links = this.getLinksToLinksAndPages();
        for(WebElement link: links){
            String linkText = this.getText(link);
            if(linkText.equals(siteLinkOrPage.value())){
                return link;
            }
        }
        throw new IllegalStateException("Could not find any link associated with " + siteLinkOrPage);
    }

    public boolean isLinkOrPagePresent(SiteLinkOrPage siteLinkOrPage){
        return this.getLinkOrPageTexts().contains(siteLinkOrPage.value());
    }

    public void selectAll(){
        this.clickOn(By.xpath("//div[@class=\"pk-table-width-minimum pk-table-fake-nestable-padding\"]/input[@type=\"checkbox\"]"));
    }

    public int getIndexByName(List<String> linkOrPageTexts, SiteLinkOrPage siteLinkOrPage){
        for (int i = 0; i < linkOrPageTexts.size(); i++) {
            if(linkOrPageTexts.get(i).equals(siteLinkOrPage.value())){
                return i;
            }
        }
        throw new IllegalStateException("getIndexByName: could not find linkOrPageText given " + siteLinkOrPage.value());
    }

    public void editLinkOrPage(SiteLinkOrPage siteLinkOrPage){
        WebElement link = this.getLinkByName(siteLinkOrPage);
        this.clickOn(link);
    }

    public void selectLinkOrPage(SiteLinkOrPage siteLinkOrPage){
        List<WebElement> inputCheckboxes = this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"uk-nestable-item check-item\"]/div/div[2]/input"));
        int index = this.getIndexByName(this.getLinkOrPageTexts(), siteLinkOrPage);
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

    public void move(SiteMenus siteMenu){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-move pk-icon-hover\"]"));
        this.selectOptionInDropdown(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]//ul[@class=\"uk-nav uk-nav-dropdown\"]"), siteMenu.value(), "./li/a");
        if(!(this.waitForElementBeingPresentOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]"))
                && this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))){
            throw new IllegalStateException("move notification not handled properly");
        }
    }

    public void delete(){
        this.clickOn(By.xpath("//div[@class=\"pk-width-content\"]//ul[@class=\"uk-subnav pk-subnav-icon\"]/li/a[@class=\"pk-icon-delete pk-icon-hover\"]"));
    }

    public boolean isStatusActive(SiteLinkOrPage siteLinkOrPage){
        List<WebElement> linkOrPageStatus = this.findElements(By.xpath("//div[@class=\"pk-width-content\"]//li[@class=\"uk-nestable-item check-item\"]/div/div[5]/a"));
        int index = this.getIndexByName(this.getLinkOrPageTexts(), siteLinkOrPage);
        WebElement status = linkOrPageStatus.get(index);
        String classAttributeValue = this.getAttribute(status, "class");
        if(classAttributeValue.equals("pk-icon-circle-success")) return true;
        else if(classAttributeValue.equals("pk-icon-circle-danger")) return false;
        else throw new IllegalStateException("isStatusActive: unknown class attribute value " + classAttributeValue);
    }

    public void addLinkOrPage(SiteAddPage siteAddPage){
        this.clickOnSelenium(By.xpath("//a[@class=\"uk-button uk-button-primary\" and text()=\"Add Page\"]"));
        this.selectOptionInDropdown(By.xpath("//div[@data-uk-dropdown]/div[@class=\"uk-dropdown uk-dropdown-small uk-dropdown-flip uk-dropdown-bottom\"]/ul"), siteAddPage.value(), "./li/a");
    }
}
