package po.wallets.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class FilterComponent extends BasePageObject implements PageComponent {

    public FilterComponent(WebDriver driver){
        super(driver);
    }

    public String getActiveFilter(){
        WebElement element = this.findElement(By.xpath("//*[@id]/div/div[1]" +
                "/div[2]/div[1]/div[2]/ul/li[@class=\"active\"]"));
        return this.getText(element);
    }

    public void clickOnFilter(String filter){
        this.clickOn(By.linkText(filter));
    }
}
