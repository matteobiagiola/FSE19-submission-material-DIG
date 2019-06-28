package po.site.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AddEditLinkFormComponent extends BasePageObject implements PageComponent {

    public AddEditLinkFormComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isUrlPresent(){
        WebElement urlParagraph = this.findElement(By.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"));
        String textUrl = this.getText(urlParagraph);
        //PageObjectLogging.logInfo(textUrl);
        return !textUrl.equals("false");
    }

    public void selectUrl(){
        this.clickOn(By.xpath("//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\" and text()=\"Select\"]"));
    }

    public void selectLinkType(String linkType){
        this.selectOptionInDropdown(By.id("form-type"), linkType);
    }

}
