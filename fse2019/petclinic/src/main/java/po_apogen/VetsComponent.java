package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class VetsComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Vets (state12)
	 */
	public VetsComponent(WebDriver driver) {
		super(driver);
	}

	/*------ added */
	public void goToIndex(){
		this.clickOn(By.xpath("//a[@title=\"home page\"]"));
	}

}
