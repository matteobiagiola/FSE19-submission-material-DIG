package po.site.components;

import custom_classes.SiteMenus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class PagesSidebarComponent extends BasePageObject implements PageComponent {


    public PagesSidebarComponent(WebDriver driver) {
        super(driver);
    }

    public List<String> getSidebarLinkTexts(){
        List<WebElement> links = this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li/a"));
        List<String> linkNames = new ArrayList<String>();
        for(WebElement link: links){
            String linkText = this.getText(link);
            if(linkText != null && !linkText.isEmpty()){
                linkNames.add(linkText);
            }else{
                throw new IllegalStateException("getSidebarLinkTexts: linkText cannot be null or empty -> " + linkText);
            }
        }
        return linkNames;
    }

    public void clickOnLink(SiteMenus siteMenu){
        for(WebElement link: this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li/a"))){
            String linkText = this.getText(link);
            if(linkText.equals(siteMenu.value())){
                this.clickOn(link);
                break;
            }
        }
    }

    public void addMenu(){
        this.clickOn(By.xpath("//a[@class=\"uk-button\" and text()=\"Add Menu\"]"));
    }

    public boolean isMenuPresent(SiteMenus siteMenu){
        return this.getSidebarLinkTexts().contains(siteMenu.value());
    }

    public boolean isSpecialMenu(SiteMenus siteMenu){
        if(siteMenu.value().equals(SiteMenus.NOT_LINKED.value()) || siteMenu.value().equals(SiteMenus.TRASH.value())){
            return true;
        }
        return false;
    }

    public boolean isTrashMenu(SiteMenus siteMenu){
        if(siteMenu.value().equals(SiteMenus.TRASH.value())){
            return true;
        }
        return false;
    }

    public boolean isMenuEditable(SiteMenus siteMenu){
        List<WebElement> liElementsWithLinks = this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li[a]"));
        for (int i = 0; i < liElementsWithLinks.size(); i++) {
            WebElement link = this.findElementJSByXPathStartingFrom(liElementsWithLinks.get(i), ".//a");
            String linkText = this.getText(link);
            if(linkText.equals(siteMenu.value())){
                WebElement ulEditContainerIcons = this.findElementJSByXPathStartingFrom(liElementsWithLinks.get(i), ".//ul");
                if(ulEditContainerIcons != null) return true;
                return false;
            }
        }
        return false;
    }

    public void editMenu(SiteMenus siteMenu){
        List<WebElement> liElementsWithLinks = this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li[a]"));
        for (int i = 0; i < liElementsWithLinks.size(); i++) {
            WebElement link = this.findElementJSByXPathStartingFrom(liElementsWithLinks.get(i), "./a");
            String linkText = this.getText(link);
            if(linkText.equals(siteMenu.value())){
                WebElement editIcon = this.findElementJSByXPathStartingFrom(liElementsWithLinks.get(i), "./ul//a[@class=\"pk-icon-edit pk-icon-hover\"]");
                this.clickOn(editIcon);
            }
        }
    }

    public void deleteMenu(SiteMenus siteMenu){
        List<WebElement> liElementsWithLinks = this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li[a]"));
        for (int i = 0; i < liElementsWithLinks.size(); i++) {
            WebElement link = this.findElementJSByXPathStartingFrom(liElementsWithLinks.get(i), "./a");
            String linkText = this.getText(link);
            if(linkText.equals(siteMenu.value())){
                WebElement editIcon = this.findElementJSByXPathStartingFrom(liElementsWithLinks.get(i), "./ul//a[@class=\"pk-icon-delete pk-icon-hover\"]");
                this.clickOn(editIcon);
            }
        }
    }

    public boolean isTrashMenuActive(){
        List<WebElement> menus = this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li[contains(@class, \"uk-visible-hover\") and not(contains(@class, \"uk-nav-divider\"))]"));
        for(WebElement menuListItem: menus){
            WebElement aTag = this.findElementStartingFrom(menuListItem, By.xpath("./a"));
            String menuName = this.getText(aTag);
            if(menuName.equals("Trash")){
                String classAttribute = this.getAttribute(menuListItem, "class");
                if(classAttribute.contains("uk-active")){
                    return true;
                }
            }
        }
        return false;
    }
}
