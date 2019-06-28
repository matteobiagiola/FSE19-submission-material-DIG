package po_apogen;

import custom_classes.WidgetNumberOfUsers;
import custom_classes.WidgetTotalUser;
import custom_classes.WidgetUserDisplay;
import custom_classes.WidgetUserType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class EditRegisteredUserComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for EditRegisteredUser (state114) --> DashboardContainerPage
	 */
	public EditRegisteredUserComponent(WebDriver driver) {
		super(driver);
	}

	public void form(WidgetUserType widgetUserType, WidgetUserDisplay widgetUserDisplay,
								  WidgetTotalUser widgetTotalUser, WidgetNumberOfUsers widgetNumberOfUsers) {
		this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetUserType.value() + "\"" + "]"));
		this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetUserDisplay.value() + "\"" + "]"));
		this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetTotalUser.value() + "\"" + "]"));
		this.selectOptionInDropdown(By.id("form-user-number"), widgetNumberOfUsers.value());
		this.clickOnSaveEditWidget();
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
