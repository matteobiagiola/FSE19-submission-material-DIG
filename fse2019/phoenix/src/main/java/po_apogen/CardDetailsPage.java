package po_apogen;

import custom_classes.CardComment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class CardDetailsPage extends BasePageObject implements PageObject {

	/**
	 * Page Object for CardDetailsPage (state13) --> CardDetails
	 */
	public CardDetailsPage(WebDriver driver) {
		super(driver);
		if(!this.isPageLoaded()){
			throw new IllegalStateException("CardDetailsPage not loaded properly");
		}
	}

	public BoardPage goToBoardPage() {
		this.clickOn(By.xpath("//div[@class=\"md-modal\"]//a[@class=\"close\"]"));
		return new BoardPage(this.getDriver());
	}

	// changed: it was goToBoardPage1
	public BoardPage deleteCard() {
		this.clickOn(By.xpath("//div[@class=\"md-modal\"]//a[@class=\"delete\"]"));
		return new BoardPage(this.getDriver());
	}

	public CardDetailsPage save_comment(CardComment cardComment) {
		this.type(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//textarea[@placeholder=\"Write a comment...\"]"), cardComment.value());
		this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//button[text()=\"Save comment\"]"));
		return new CardDetailsPage(this.getDriver());
	}

	@Override
	public boolean isPageLoaded() {
		if(this.waitForElementBeingPresentOnPage(ConstantLocators.CARD_DETAILS.value())){
			return true;
		}
		return false;
	}
}
