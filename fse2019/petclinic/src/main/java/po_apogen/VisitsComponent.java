package po_apogen;

import custom_classes.Dates;
import custom_classes.VisitDescriptions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class VisitsComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Visits (state40)
	 */
	public VisitsComponent(WebDriver driver) {
		super(driver);
	}

	public void form(Dates date, VisitDescriptions visitDescription) {
		this.typeWithoutClear(By.xpath("//input[@type=\"date\"]"), date.value());
		this.type(By.xpath("//div[@class=\"form-group\"]/textarea"), visitDescription.value());
//		this.clickOn(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"));
		this.bruteForceClick(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"), By.xpath("//div[@class=\"form-group\"]/textarea"),
				20, "form: failed to click submit button");
	}

}
