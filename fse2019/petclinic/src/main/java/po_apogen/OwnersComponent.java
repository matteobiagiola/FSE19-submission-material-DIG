package po_apogen;

import custom_classes.FirstNames;
import custom_classes.LastNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageComponent;

import java.util.List;

public class OwnersComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Owners (state5)
	 */
	public OwnersComponent(WebDriver driver) {
		super(driver);
	}

	public void goToOwnerInformation(FirstNames firstName, LastNames lastName) {
		this.clickOnOwner(firstName, lastName);
	}

	public void searchFilter(FirstNames firstName) {
		this.type(By.xpath("//div[@class=\"form-group\"]/input[@placeholder=\"Search Filter\"]"), firstName.value());
		if(!this.waitForElementBeingPresentOnPage(ConstantLocators.OWNERS_PAGE_TABLE.value(), 1000)){
			// at most it waits for 1 second that the first element in the table appears
			throw new IllegalStateException("searchFilter: first name " + firstName.value() + " not loaded properly after filtering");
		}
	}

	/*-------------added */

	public List<WebElement> getOwnerList() {
		return this.findElements(By.xpath("html/body/div[1]/div/div/ui-view/owner-list/table/tbody//a"));
	}

	public boolean isListEmpty() {
		return this.getOwnerList().isEmpty();
	}

	public boolean isOwnerPresent(FirstNames firstName, LastNames lastName) {
		if (isListEmpty())
			return false;
		String ownerName = firstName.value() + " " + lastName.value();
		for (WebElement owner : this.getOwnerList()) {
			String candidateOwner = this.getText(owner);
			if (candidateOwner.equals(ownerName)) {
				return true;
			}
		}
		return false;
	}

	public void clickOnOwner(FirstNames firstName, LastNames lastName){
		String ownerName = firstName.value() + " " + lastName.value();
		for (WebElement owner : this.getOwnerList()) {
			String candidateOwner = this.getText(owner);
			if (candidateOwner.equals(ownerName)) {
				this.clickOn(owner);
				return;
			}
		}
		throw new IllegalStateException("clickOnOwner: owner " + firstName.value() + " " + lastName.value() + " not found");
	}



}
