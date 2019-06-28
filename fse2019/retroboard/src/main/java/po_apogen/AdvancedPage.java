package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class AdvancedPage implements PageObject {

	public AdvancedComponent advancedComponent;

	/**
	 * Page Object for Advanced (state30) --> HomeContainerPage
	 */
	public AdvancedPage(WebDriver driver) {
		this.advancedComponent = new AdvancedComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("AdvancedPage not loaded properly");
		}
	}

	// no correspondence in manual PO
	public IndexPage goToIndex() {
		this.advancedComponent.goToIndex();
		return new IndexPage(this.advancedComponent.getDriver());
	}

	// goToPreviousView
	public PreviousPage goToPrevious() {
		if(this.advancedComponent.isPreviousTabPresent()){
			this.advancedComponent.goToPrevious();
			return new PreviousPage(this.advancedComponent.getDriver());
		}else{
			throw new IllegalStateException("goToPreviousView: previous tab is not present");
		}
	}

	// no correspondence in manual PO
	public AdvancedPage goToAdvanced() {
		this.advancedComponent.goToAdvanced();
		return new AdvancedPage(this.advancedComponent.getDriver());
	}

	// added
	public LoginPage goToLogin(){
		this.advancedComponent.goToLogin();
		return new LoginPage(this.advancedComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.advancedComponent.waitForElementBeingPresentOnPage(ConstantLocators.HOME.value())){
			return true;
		}
		return false;
	}
}
