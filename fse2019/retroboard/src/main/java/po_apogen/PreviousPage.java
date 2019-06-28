package po_apogen;

import custom_classes.BoardNames;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class PreviousPage implements PageObject {

	public PreviousComponent previousComponent;

	/**
	 * Page Object for Previous (state31) --> HomeContainerPage
	 */
	public PreviousPage(WebDriver driver) {
		this.previousComponent = new PreviousComponent(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("PreviousPage not loaded properly");
		}
	}

	// goToBoard
	public RetrospectivePage goToRetrospective(BoardNames boardName) {
		if(this.previousComponent.isBoardPresent(boardName.value())){
			this.previousComponent.goToRetrospective(boardName);
			return new RetrospectivePage(this.previousComponent.getDriver());
		}else{
			throw new IllegalStateException("goToRetrospective: board " + boardName.value() + " is not present");
		}
	}

	// no correspondence with manual PO
	public IndexPage goToIndex() {
		this.previousComponent.goToIndex();
		return new IndexPage(this.previousComponent.getDriver());
	}

	// goToPreviousView
	public PreviousPage goToPrevious(){
		this.previousComponent.goToPrevious();
		return new PreviousPage(this.previousComponent.getDriver());
	}

	// no correspondence with manual PO
	public AdvancedPage goToAdvanced() {
		this.previousComponent.goToAdvanced();
		return new AdvancedPage(this.previousComponent.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.previousComponent.waitForElementBeingPresentOnPage(ConstantLocators.HOME.value())){
			return true;
		}
		return false;
	}
}
