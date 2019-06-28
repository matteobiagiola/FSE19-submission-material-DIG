package po.site.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.CheckCondition;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

public class AddEditLoginWidgetComponent extends BasePageObject implements PageComponent {

    public AddEditLoginWidgetComponent(WebDriver driver) {
        super(driver);
    }

    public void typeTitle(String loginTitle){
        this.type(By.id("form-title"), loginTitle);
    }

    public boolean isUrlLoginPresent(){
        return this.isUrlPresent(1);
    }

    public boolean isUrlLogoutPresent(){
        return this.isUrlPresent(2);
    }

    public boolean isUrlPresent(int index){
        CheckCondition.checkArgument(index >= 1 && index <= 2, "Index must be [1,2]: found " + index);
        WebElement urlParagraph = this.findElement(By.xpath("(//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"])[" + index + "]"));
        String textUrl = this.getText(urlParagraph);
        PageObjectLogging.logInfo(textUrl);
        return !textUrl.equals("false");
    }

    public void selectLoginRedirect(){
        this.clickOn(By.xpath("(//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\"])[1]"));
    }

    public void selectLogoutRedirect(){
        this.clickOn(By.xpath("(//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\"])[2]"));
    }
}
