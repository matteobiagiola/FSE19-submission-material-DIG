package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class MetaComponent extends BasePageObject implements PageComponent{

    public MetaComponent(WebDriver driver) {
        super(driver);
    }

    public void typeTitle(String title){
        this.type(By.id("form-meta-title"), title);
    }

    public void typeDescription(String description){
        this.type(By.id("form-meta-description"), description);
    }

    public void selectImage(){
        this.clickOn(By.xpath("//div[./label[@for=\"form-meta-image\"]]/div/a"));
    }
}
