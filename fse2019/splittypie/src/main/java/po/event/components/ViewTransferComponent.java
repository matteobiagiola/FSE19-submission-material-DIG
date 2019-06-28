package po.event.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class ViewTransferComponent extends BasePageObject implements PageComponent {

    public ViewTransferComponent(WebDriver driver) {
        super(driver);
    }

    public void cancel(){
        this.clickOn(By.xpath("//div[@class=\"form-group hidden-xs\"]/button"));
    }
}
