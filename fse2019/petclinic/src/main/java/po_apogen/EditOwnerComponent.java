package po_apogen;

import custom_classes.Addresses;
import custom_classes.Cities;
import custom_classes.FirstNames;
import custom_classes.LastNames;
import custom_classes.Telephones;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageComponent;

public class EditOwnerComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for EditOwner (state7)
	 */
	public EditOwnerComponent(WebDriver driver) {
		super(driver);
	}

	public void editOwner(FirstNames firstName, LastNames lastName, Addresses address, Cities city, Telephones telephone){
		this.type(By.name("firstName"), firstName.value());
		this.type(By.name("lastName"), lastName.value());
		this.type(By.name("address"), address.value());
		this.type(By.name("city"), city.value());
		this.type(By.name("telephone"), telephone.value());
//		this.clickOn(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"));
		this.bruteForceClick(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"),
				By.xpath("//owner-form/h2[text()=\"Owner\"]"), 20 , "editOwner: failed to click submit button");
	}

}
