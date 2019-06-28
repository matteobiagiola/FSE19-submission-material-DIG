package po.goals.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class GoalDetailsComponent extends BasePageObject implements PageComponent {

    public GoalDetailsComponent(WebDriver driver) {
        super(driver);

    }

    public void refreshStats() {
        this.clickOn(By.id("reload_stats_button"));
    }


}
