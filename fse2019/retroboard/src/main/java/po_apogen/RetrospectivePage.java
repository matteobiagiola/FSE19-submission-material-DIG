package po_apogen;

import custom_classes.BoardNames;
import custom_classes.Id;
import custom_classes.IdeasPosts;
import custom_classes.NotWentWellPosts;
import custom_classes.WentWellPosts;
import org.openqa.selenium.WebDriver;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class RetrospectivePage implements PageObject {

	public RetrospectiveComponent retrospectiveComponent;

	/**
	 * Page Object for NewRetrospective (state7) --> RetrospectiveContainerPage
	 */
	public RetrospectivePage(WebDriver driver) {
		this.retrospectiveComponent = new RetrospectiveComponent(driver);
	}

	// divided in 4 methods instead of 1
//	public RetrospectivePage retrospective(String args0, String args1, String args2, String args3) {
//		span_1.sendKeys(args3);
//		input_1.sendKeys(args0);
//		input_2.sendKeys(args1);
//		input_3.sendKeys(args2);
//		return this;
//	}

	public RetrospectivePage renameBoard(BoardNames newBoardName){
		this.retrospectiveComponent.renameBoard(newBoardName.value());
		return new RetrospectivePage(this.retrospectiveComponent.getDriver());
	}

	public RetrospectivePage createWentWellPost(WentWellPosts wentWellPost){
		if(!this.retrospectiveComponent.isSummaryModeOn()){
			this.retrospectiveComponent.typeWentWellPost(wentWellPost.value());
			return new RetrospectivePage(this.retrospectiveComponent.getDriver());
		}else{
			throw new IllegalStateException("createWentWellPost: summary mode is on");
		}
	}

	public RetrospectivePage createNotWentWellPost(NotWentWellPosts notWentWellPost){
		if(!this.retrospectiveComponent.isSummaryModeOn()){
			this.retrospectiveComponent.typeNotWentWellPost(notWentWellPost.value());
			return new RetrospectivePage(this.retrospectiveComponent.getDriver());
		}else{
			throw new IllegalStateException("createNotWentWellPost: summary mode is on");
		}
	}

	public RetrospectivePage createIdeasPost(IdeasPosts ideasPost){
		if(!this.retrospectiveComponent.isSummaryModeOn()){
			this.retrospectiveComponent.typeIdeasPost(ideasPost.value());
			return new RetrospectivePage(this.retrospectiveComponent.getDriver());
		}else{
			throw new IllegalStateException("createIdeasPost: summary mode is on");
		}
	}

	// openMenu
	public MenuPage goToMenu() {
		this.retrospectiveComponent.goToMenu();
		return new MenuPage(this.retrospectiveComponent.getDriver());
	}

	public RetrospectivePage deleteWentWellPost(Id id){
		if(!this.retrospectiveComponent.isSummaryModeOn()
				&& this.retrospectiveComponent.isWentWellPostPresent(id.value)
				&& !this.retrospectiveComponent.isWentWellPostShared(id.value)){
			this.retrospectiveComponent.deleteWentWellPost(id.value);
			return new RetrospectivePage(this.retrospectiveComponent.getDriver());
		}else{
			throw new IllegalStateException("deleteWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is shared");
		}
	}

	public RetrospectivePage deleteNotWentWellPost(Id id){
		if(!this.retrospectiveComponent.isSummaryModeOn()
				&& this.retrospectiveComponent.isNotWentWellPostPresent(id.value)
				&& !this.retrospectiveComponent.isNotWentWellPostShared(id.value)){
			this.retrospectiveComponent.deleteNotWentWellPost(id.value);
			return new RetrospectivePage(this.retrospectiveComponent.getDriver());
		}else{
			throw new IllegalStateException("deleteNotWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is shared");
		}
	}

	public RetrospectivePage deleteIdeasPost(Id id){
		if(!this.retrospectiveComponent.isSummaryModeOn()
				&& this.retrospectiveComponent.isIdeasPostPresent(id.value)
				&& !this.retrospectiveComponent.isIdeasPostShared(id.value)){
			this.retrospectiveComponent.deleteIdeasPost(id.value);
			return new RetrospectivePage(this.retrospectiveComponent.getDriver());
		}else{
			throw new IllegalStateException("deleteIdeasPost: summary mode is on or post with id " + id.value + " is not present or it is shared");
		}
	}

	@Override
	public boolean isPageLoaded() {
		if(this.retrospectiveComponent.waitForElementBeingPresentOnPage(ConstantLocators.RETROSPECTIVE.value())){
			return true;
		}
		return false;
	}
}
