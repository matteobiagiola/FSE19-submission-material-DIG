package po.login.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class LoginComponent extends BasePageObject implements PageComponent {

    public LoginComponent(WebDriver driver) {
        super(driver);
    }

    public void clickStart(){
        this.clickOn(By.xpath("//button[text()=\"Let's start\"]"));
    }

    public void typeName(String name){
        this.type(By.xpath("//input[@type=\"input\"]"),name);
    }
}
