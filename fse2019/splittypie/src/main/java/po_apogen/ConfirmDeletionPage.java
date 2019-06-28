package po_apogen;

import custom_classes.TripNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageObject;

public class ConfirmDeletionPage extends BasePageObject implements PageObject {

	public TripNames tripName;
	/**
	 * Page Object for ConfirmDeletionPage (state178) --> ConfirmationPage
	 */
	public ConfirmDeletionPage(WebDriver driver, TripNames tripName) {
		super(driver);
		this.tripName = tripName;
		if(!this.isPageLoaded()){
			throw new IllegalStateException("ConfirmationDeletionPage not loaded properly");
		}
	}

	public IndexPage yes() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new IndexPage(this.getDriver());
	}

	// changed in EditEventPage; return type was ConfirmDeletionPage
	public EditEventPage no() {
		this.clickOn(By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"No\"]"));
		this.waitForTimeoutExpires(500); //wait for the modal disappears is not sufficient (anyway in headless mode never fails)
		return new EditEventPage(this.getDriver(), this.tripName);
	}

	@Override
	public boolean isPageLoaded() {
		if(this.isElementPresentOnPage(ConstantLocators.ERROR.value())){
			this.getDriver().get(MyProperties.getInstance().getProperty("appPort"));
		}
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.CONFIRMATION.value())){
			return true;
		}
		return false;
	}
}
