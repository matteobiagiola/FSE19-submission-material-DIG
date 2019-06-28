package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class NavbarComponent extends BasePageObject implements PageComponent {

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    public void clickHomeLink(){
        this.clickOn(By.xpath("//a[text()=\"Retrospected \"]"));
    }

    public void clickOpenMenu(){
        this.clickOn(By.xpath("//button[@id=\"crawljax-menu\"]"));
    }

    public void clickOpenShare(){
        this.clickOn(By.xpath("//button[@title=\"Invite\"]"));
    }
}
