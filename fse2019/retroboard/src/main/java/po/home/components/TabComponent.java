package po.home.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class TabComponent extends BasePageObject implements PageComponent {

    public TabComponent(WebDriver driver) {
        super(driver);
    }

    public void clickAdvanced(){
        this.clickOn(By.xpath("//label[text()=\"Advanced\"]"));
    }

    public void clickPrevious(){
        this.clickOn(By.xpath("//label[text()=\"Previous\"]"));
    }

    public boolean isPreviousTabPresent(){
        return this.isElementPresentOnPage(By.xpath("//label[text()=\"Previous\"]"));
    }

    public boolean isPreviousTabActive(){
        WebElement activeTab = this.findElement(By.xpath("//nav/label[contains(@class,\"theme__active___2SLiK\")]"));
        String text = this.getText(activeTab);
        if(text.equalsIgnoreCase("Previous")){
            return true;
        }
        return false;
    }

    public void clickCreate(){
        this.clickOn(By.xpath("//label[text()=\"Create\"]"));
    }
}
