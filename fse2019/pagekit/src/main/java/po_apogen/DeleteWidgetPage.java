package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class DeleteWidgetPage extends BasePageObject implements PageObject {

	/**
	 * Page Object for DeleteWidget (state212) --> DeleteItemPage
	 */
	public DeleteWidgetPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("DeleteWidgetPage not loaded properly");
		}
	}

	public DashboardPage ok() {
		this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
		return new DashboardPage(this.getDriver());
	}

	// strange
//	public DeleteWidgetPage cancel() {
//		this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
//		return this;
//	}

	public DashboardPage cancel() {
		this.clickOn(By.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
		return new DashboardPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.DELETE_ITEM.value())){
			return true;
		}
		return false;
	}
}
