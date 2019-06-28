package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class CreatePlanAdvancedSettingsComponent extends BasePageObject implements PageComponent{

	public CreatePlanAdvancedSettingsComponent(WebDriver driver) {
		super(driver);
	}

	public void goToPlanBasicSettingsPage() {
		this.clickOn(By.cssSelector("#button_step2_back"));
	}

	public void confirmAndSave() {
		this.clickOn(By.cssSelector("#button_step2_save"));
	}
}
