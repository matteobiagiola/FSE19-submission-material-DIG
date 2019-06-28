package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class HomeComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for HomePage (state176) --> EventDetailsContainerPage
	 */
	public HomeComponent(WebDriver driver) {
		super(driver);
	}

	public void goToEditEvent() {
		this.clickOn(By.xpath("//div[@class=\"pull-right\"]/a[text()]"));
	}

	public void goToHomePage() {
		this.clickOn(By.xpath("//a[@title=\"Overview\"]"));
	}

	public void goToTransactions() {
		this.clickOn(By.xpath("//a[@title=\"Transactions\"]"));
	}

	/*----------- added */

	public void goToIndexPage(){
		this.clickOn(By.xpath("//div[@class=\"navbar-header\"]/a[@title=\"SplittyPie Home Page\"]"));
	}

	public boolean isTripPresent(String tripName){
		String currentTripName = this.getCurrentTrip();
		if(currentTripName.equals(tripName)) return true;
		this.clickOn(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
		List<WebElement> otherTripElements = this.findElements(By.xpath("//div[@class=\"pull-left\"]/div[contains(@class, \"dropdown event-dropdown\")]/ul/li/a[not(@href=\"/new\")]"));
		for(WebElement otherTripElement: otherTripElements){
			String otherTripName = this.getText(otherTripElement);
			if(otherTripName != null && !otherTripName.isEmpty()){
				if(otherTripName.equals(tripName)) return true;
			}else{
				throw new IllegalStateException("isTripPresent: other trip name must not be null nor empty");
			}
		}
		return false;
	}

	public String getCurrentTrip(){
		WebElement currentTripElement = this.findElement(By.xpath("//div[@class=\"pull-left\"]//button[@id=\"dropDownEvents\"]"));
		String currentTripName = this.getText(currentTripElement);
		if(currentTripName != null && !currentTripName.isEmpty()){
			return currentTripName;
		}else{
			throw new IllegalStateException("getCurrentTrip: current trip name must not be null nor empty");
		}
	}
}
