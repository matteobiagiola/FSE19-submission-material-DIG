package po_apogen;

import custom_classes.Id;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class CreatePlanComponent extends BasePageObject implements PageComponent{

	public CreatePlanComponent(WebDriver driver) {
		super(driver);
	}

	public void goToPlanBasicSettings() {
		this.clickOn(By.id("button_create_new"));
	}

	public void viewPlanReport(Id id){
		this.clickOn(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]//a"));
	}

	public void editPlan(Id id){
		this.clickOn(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]//button[@class=\"btn btn-default btn-xs edit_plan_button\"]"));
	}

	/* method preconditions */

	public boolean planExists(Id id){
		if(this.isElementPresentOnPage(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]"))){
			return true;
		}
		return false;
	}

}
