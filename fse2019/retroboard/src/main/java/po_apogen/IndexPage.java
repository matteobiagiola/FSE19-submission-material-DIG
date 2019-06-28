package po_apogen;

import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class IndexPage implements PageObject {

	public IndexComponent indexComponent;

	/**
	 * Page Object for Index (index) --> HomeContainerPage
	 */
	public IndexPage(WebDriver driver) {
		this.indexComponent = new IndexComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("IndexPage not loaded properly");
		}
	}

	// createSession
	public RetrospectivePage goToRetrospective() {
		this.indexComponent.goToRetrospective();
		return new RetrospectivePage(this.indexComponent.getDriver());
	}

	// no correspondence in manual PO
	public IndexPage goToIndex() {
		this.indexComponent.goToIndex();
		return new IndexPage(this.indexComponent.getDriver());
	}

	// goToPreviousView
	public PreviousPage goToPrevious() {
		if(this.indexComponent.isPreviousTabPresent()){
			this.indexComponent.goToPrevious();
			return new PreviousPage(this.indexComponent.getDriver());
		}else{
			throw new IllegalStateException("goToPreviousView: previous tab is not present");
		}
	}

	// no correspondence in manual PO
	public AdvancedPage goToAdvanced() {
		this.indexComponent.goToAdvanced();
		return new AdvancedPage(this.indexComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.indexComponent.waitForElementBeingPresentOnPage(ConstantLocators.HOME.value())){
			return true;
		}
		return false;
	}
}
