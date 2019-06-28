package po_apogen;

import custom_classes.WidgetLocation;
import custom_classes.WidgetUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class EditLocationComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for EditComponentLocation (state197) --> DashboardContainerPage
	 */
	public EditLocationComponent(WebDriver driver) {
		super(driver);
	}

	public void form(WidgetLocation widgetLocation, WidgetUnit widgetUnit) {
		int maxAttempts = 0;
		int timeout = 1000;
		boolean dropdownTriggered = this.triggerDropdownMenu(widgetLocation, timeout);
		while(!dropdownTriggered && maxAttempts < 3){
			timeout = timeout * 2;
			dropdownTriggered = this.triggerDropdownMenu(widgetLocation, timeout);
			maxAttempts++;
		}
		if(maxAttempts == 3) throw new IllegalStateException("Dropdown menu in location widget not loaded properly");
		//always select the first result of the dropdown since the input value is already correct
		this.clickOnSelenium(By.xpath("(//form[@class=\"pk-panel-teaser uk-form uk-form-stacked\" and not(@style=\"display: none;\")]//div[@class=\"uk-dropdown\"]/ul/li)[1]/a"));
		this.clickOn(By.xpath("//form[@class=\"pk-panel-teaser uk-form uk-form-stacked\" and not(@style=\"display: none;\")]//input[@value=" + "\"" + widgetUnit.value() + "\"" + "]"));
		this.clickOnSaveEditWidget();
	}

	public boolean triggerDropdownMenu(WidgetLocation widgetLocation, int timeout) throws IllegalStateException{
		this.type(By.xpath("//form[@class=\"pk-panel-teaser uk-form uk-form-stacked\" and " +
				"not(@style=\"display: none;\")]//input[@id=\"form-city\"]"), widgetLocation.value());
		if(timeout > 1000) {
			this.typeWithoutClearing(By.xpath("//form[@class=\"pk-panel-teaser uk-form uk-form-stacked\" and " +
					"not(@style=\"display: none;\")]//input[@id=\"form-city\"]"), " ");
		}
		String attributeName = "class";
		String expectedValueAttribute = "uk-autocomplete uk-width-1-1 uk-open";
		return this.waitForElementThatChangesProperty(
				By.xpath("//form[@class=\"pk-panel-teaser uk-form uk-form-stacked\" and not(@style=\"display: none;\")]" +
						"//div[contains(@class, \"uk-autocomplete uk-width-1-1\")]"),
				attributeName, expectedValueAttribute, timeout);
	}

	public void clickOnSaveEditWidget(){
		this.clickOnSelenium(By.xpath("//li[not(@style=\"display: none;\")]/a[@class=\"pk-icon-check pk-icon-hover\"]"));
		int maxAttempts = 0;
		boolean result = this.isElementPresentOnPage(By.xpath("//li[not(@style=\"display: none;\")]/a[@class=\"pk-icon-check pk-icon-hover\"]"));
		while(result && maxAttempts < 3){
			this.clickOnSelenium(By.xpath("//li[not(@style=\"display: none;\")]/a[@class=\"pk-icon-check pk-icon-hover\"]"));
			result = this.isElementPresentOnPage(By.xpath("//li[not(@style=\"display: none;\")]/a[@class=\"pk-icon-check pk-icon-hover\"]"));
			maxAttempts++;
		}
		if(maxAttempts == 3){
			throw new IllegalStateException("Failed to click save widget");
		}
	}

}
