package po.site.components;

import custom_classes.WidgetMenus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class WidgetsSidebarComponent extends BasePageObject implements PageComponent {

    public WidgetsSidebarComponent(WebDriver driver) {
        super(driver);
    }

    //Unassigned is always present but with display none when the number of widgets in it is 0
    public List<String> getSidebarLinkTexts(){
        List<WebElement> links = this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li[not(@style=\"display: none;\")]/a"));
        List<String> linkNames = new ArrayList<String>();
        for(WebElement link: links){
            String linkText = this.getText(link);
            if(linkText != null && !linkText.isEmpty()){
                int indexOfSpace = linkText.indexOf(' ');
                if(indexOfSpace != -1) {
                    linkText = linkText.substring(0, indexOfSpace);
                }
                linkNames.add(linkText);
            }else{
                throw new IllegalStateException("getSidebarLinkTexts: linkText cannot be null or empty -> " + linkText);
            }
        }
        return linkNames;
    }

    //Unassigned is always present but with display none when the number of widgets in it is 0
    public void clickOnLink(WidgetMenus widgetMenu){
        for(WebElement link: this.findElements(By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li[not(@style=\"display: none;\")]/a"))){
            String linkText = this.getText(link);
            if(linkText != null && !linkText.isEmpty()){
                int indexOfSpace = linkText.indexOf(' ');
                if(indexOfSpace != -1) {
                    linkText = linkText.substring(0, indexOfSpace);
                }
                if(linkText.equals(widgetMenu.value())){
                    this.clickOn(link);
                    break;
                }
            }else{
                throw new IllegalStateException("getSidebarLinkTexts: linkText cannot be null or empty -> " + linkText);
            }
        }
    }

    public boolean isMenuPresent(WidgetMenus widgetMenu){
        return this.getSidebarLinkTexts().contains(widgetMenu.value());
    }
}
