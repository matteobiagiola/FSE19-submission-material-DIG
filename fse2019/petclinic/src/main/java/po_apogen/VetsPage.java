package po_apogen;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class VetsPage implements PageObject {

	public VetsComponent vetsComponent;

	/**
	 * Page Object for Vets (state12)
	 */
	public VetsPage(WebDriver driver) {
		this.vetsComponent = new VetsComponent(driver);
		if (!this.isPageLoaded()){
			throw new IllegalStateException("VetsPage not loaded properly");
		}
	}

	/*------ added */
	public IndexPage goToIndex(){
		this.vetsComponent.goToIndex();
		return new IndexPage(this.vetsComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.vetsComponent
				.waitForElementBeingPresentOnPage(ConstantLocators.VETS_PAGE.value())){
			return true;
		}
		return false;
	}
}
