package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class HTMLFormComponent extends BasePageObject implements PageComponent {


    public HTMLFormComponent(WebDriver driver) {
        super(driver);
    }

    public void enterTitle(String title){
        this.type(By.xpath("//input[@class=\"uk-width-1-1 uk-form-large\"]"), title);
    }

    public void writeIntoTextarea(String input){
        this.type(By.id("textareahtml"), input);
    }
}
