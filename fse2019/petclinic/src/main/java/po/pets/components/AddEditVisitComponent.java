package po.pets.components;

import custom_classes.Dates;
import custom_classes.VisitDescriptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AddEditVisitComponent extends BasePageObject implements PageComponent {

    public AddEditVisitComponent(WebDriver driver) {
        super(driver);
    }

    public void addVisit(Dates date, VisitDescriptions visitDescription) {
        this.typeWithoutClear(By.xpath("//input[@type=\"date\"]"), date.value());
        this.type(By.xpath("//div[@class=\"form-group\"]/textarea"), visitDescription.value());
//		this.clickOn(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"));
        this.bruteForceClick(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"), By.xpath("//div[@class=\"form-group\"]/textarea"),
                20, "form: failed to click submit button");
    }
}
