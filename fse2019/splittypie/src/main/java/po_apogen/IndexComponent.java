package po_apogen;

import custom_classes.TripNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.MyProperties;
import po_utils.PageComponent;

import java.util.List;

public class IndexComponent extends BasePageObject implements PageComponent {

	@FindBy(xpath = "html/body/div[2]/div[3]/main/div/div[2]/section[2]/div/div/a")
	private WebElement a_trip;

	@FindBy(xpath = "html/body/div[1]/div[3]/nav/div/div[2]/ul/li[4]/a")
	private WebElement a_Newevent;

	@FindBy(xpath = "html/body/div[2]/div[3]/main/div/div[2]/section[1]/div/div[1]/p[2]/a")
	private WebElement a_Newevent_1;

	@FindBy(xpath = "//BUTTON[@title='Remove reference from this computer. The event will still be reachable by url.']")
	private WebElement button_close;

	/**
	 * Page Object for Index (index) --> HomePageContainerPage
	 */
	public IndexComponent(WebDriver driver) {
		super(driver);
	}

	// changed: it was goToNew
	public void goToNewEventNavbar() {
		this.clickOn(By.xpath("//div[@id=\"navbar-right\"]//a[@title=\"Create New Event\"]"));
	}

	// changed: it was goToNew_1
	public void goToNewEventHome() {
		this.clickOn(By.xpath("//section[@id=\"about\"]//a[text()=\"Create New Event\"]"));
	}

	public void goToHomePage(TripNames tripName) {
		this.clickOnTrip(tripName.value());
		int max = 100;
		while(this.isElementPresentOnPage(ConstantLocators.ERROR.value()) && max > 0){
			this.getDriver().navigate().refresh();
			this.waitForTimeoutExpires(500);
			this.clickOnTrip(tripName.value());
			max--;
		}
		if(max == 0){
			throw new IllegalStateException("Failed to deal with error page when clicking on a just created event in IndexPage");
		}

	}
	
	public void goToConfirmation(TripNames tripName) {
		this.deleteTrip(tripName.value());
	}

	/*-------------- added */

	public boolean isEventPresent(String tripName){
		List<WebElement> events = this.getEvents();
		for(WebElement event: events){
			WebElement strongElement = this.findElementJSByXPathStartingFrom(event, "./a/strong");
			String strongText = this.getText(strongElement);
			if(strongText.equals(tripName)){
				return true;
			}
		}
		return false;
	}

	public List<WebElement> getEvents(){
		return this.findElements(By.xpath("//section[@id=\"events\"]//div[@class=\"previous-events\"]/div"));
	}

	public void clickOnTrip(String tripName){
		List<WebElement> events = this.getEvents();
		for(WebElement event: events){
			WebElement strongElement = this.findElementJSByXPathStartingFrom(event, "./a/strong");
			String strongText = this.getText(strongElement);
			if(strongText.equals(tripName)){
				WebElement linkElement = this.findElementJSByXPathStartingFrom(event, "./a");
				this.clickOn(linkElement);
				break;
			}
		}
	}

	public void deleteTrip(String tripName){
		List<WebElement> events = this.getEvents();
		for(WebElement event: events){
			WebElement strongElement = this.findElementJSByXPathStartingFrom(event, "./a/strong");
			String strongText = this.getText(strongElement);
			if(strongText.equals(tripName)){
				WebElement button = this.findElementJSByXPathStartingFrom(event, "./button");
				this.clickOn(button);
			}
		}
	}

}
