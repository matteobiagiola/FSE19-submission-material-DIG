package po.wallets.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AccessComponent extends BasePageObject implements PageComponent {

    public AccessComponent(WebDriver driver){
        super(driver);
    }

    public String getActiveAccessFilter(){
        WebElement element = this.findElement(By.xpath("//*[@id]/div/div[1]" +
                "/div[2]/div[2]/div[2]/ul/li[@class=\"active\"]"));
        return this.getText(element);
    }

    public void clickOnAccess(String access){
        this.clickOn(By.linkText(access));
    }
}
