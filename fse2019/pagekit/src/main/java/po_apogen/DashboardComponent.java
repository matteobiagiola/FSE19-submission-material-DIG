package po_apogen;

import custom_classes.WidgetFeedNumberOfPosts;
import custom_classes.WidgetFeedPostContent;
import custom_classes.WidgetFeedTitle;
import custom_classes.WidgetFeedUrl;
import custom_classes.WidgetLocation;
import custom_classes.WidgetNumberOfUsers;
import custom_classes.WidgetTotalUser;
import custom_classes.WidgetUnit;
import custom_classes.WidgetUserDisplay;
import custom_classes.WidgetUserType;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class DashboardComponent extends BasePageObject implements PageComponent {

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[2]/UL[1]/LI[1]/DIV[1]/COMPONENT[1]/DIV[1]/UL[1]/LI[2]")
	private WebElement li_Li_Ul_Div_Component_Location;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[2]/UL[1]/LI[1]")
	private WebElement li_Ul_Location;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[1]")
	private WebElement li_Ul_Div_Div_1registereduser;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[2]/UL[1]/LI[1]/DIV[1]/COMPONENT[1]/DIV[1]/UL[1]/LI[1]/A[1]")
	private WebElement a_editComponentLocation;

	@FindBy(css = "#dashboard > div.uk-grid.uk-grid-medium.uk-grid-match > div.uk-width-medium-1-3:nth-child(2) > ul.uk-sortable.pk-sortable > li > div.uk-panel.uk-panel-box.uk-visible-hover-inline > component > form.pk-panel-teaser.uk-form.uk-form-stacked > div.uk-form-row:nth-child(2) > div.uk-form-controls.uk-form-controls-text > p.uk-form-controls-condensed:nth-child(1) > label > input")
	private WebElement input_metric;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[4]/A[1]")
	private WebElement a_Admin;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[1]/A[1]")
	private WebElement a_editregistereduser;

	@FindBy(css = "#form-city")
	private WebElement input_form_city;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[1]/DIV[2]/DIV[1]/A[1]")
	private WebElement a_Addwidget;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[2]")
	private WebElement li_Li_Ul_Div_Div_1registereduser;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[3]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[2]")
	private WebElement li_Li_Ul_Div_Div_Pagekitnews;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[3]/UL[1]/LI[1]")
	private WebElement li_Ul_Pagekitnews;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[3]")
	private WebElement li_Li_Li_Ul_Admin;

	@FindBy(css = "#dashboard > div.uk-grid.uk-grid-medium.uk-grid-match > div.uk-width-medium-1-3:nth-child(2) > ul.uk-sortable.pk-sortable > li > div.uk-panel.uk-panel-box.uk-visible-hover-inline > component > form.pk-panel-teaser.uk-form.uk-form-stacked > div.uk-form-row:nth-child(2) > div.uk-form-controls.uk-form-controls-text > p.uk-form-controls-condensed:nth-child(2) > label > input")
	private WebElement input_imperial;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[2]/A[1]")
	private WebElement a_Li_Li_Ul_Admin;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[1]/DIV[1]/DIV[1]/A[1]")
	private WebElement a_Updatenow;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]")
	private WebElement li_Ul_1registereduser;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[3]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[1]")
	private WebElement li_Ul_Div_Div_Pagekitnews;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[3]/A[1]")
	private WebElement a_logout;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[3]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[3]/A[1]")
	private WebElement a_deletePagekitnews;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]/DIV[1]/COMPONENT[1]/UL[1]/LI[1]")
	private WebElement li_Ul_H3_Registereduser;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[2]")
	private WebElement li_Li_Ul_Admin;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[4]")
	private WebElement li_Li_Li_Li_Ul_Admin;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[1]/DIV[2]/DIV[1]/DIV[1]/UL[1]/LI[1]/A[1]")
	private WebElement a_User;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[2]/A[1]")
	private WebElement a_Li_Li_Ul_Div_Div_1registereduser;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[1]/UL[1]/LI[1]/DIV[1]/COMPONENT[1]/UL[1]/LI[1]/A[1]")
	private WebElement a_Li_Ul_H3_Registereduser;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[2]/UL[1]/LI[1]")
	private WebElement li_Ul_Admin;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/DIV[1]/DIV[1]/DIV[1]/I[1]")
	private WebElement i_Div_Dashboard;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[2]/UL[1]/LI[1]/DIV[1]/COMPONENT[1]/DIV[1]/UL[1]/LI[2]/A[1]")
	private WebElement a_Li_Li_Ul_Div_Component_Location;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[3]/UL[1]/LI[1]/DIV[1]/DIV[1]/UL[1]/LI[1]/A[1]")
	private WebElement a_Li_Ul_Div_Div_Pagekitnews;

	@FindBy(xpath = "//DIV[@id = 'dashboard']/DIV[2]/DIV[2]/UL[1]/LI[1]/DIV[1]/COMPONENT[1]/DIV[1]/UL[1]/LI[1]")
	private WebElement li_Ul_Div_Component_Location;

	private WebDriver driver;

	/**
	 * Page Object for Dashboard (state1) --> DashboardContainerPage
	 */
	public DashboardComponent(WebDriver driver) {
		super(driver);
	}

	public void goToDashBoardMenu() {
		this.clickOn(By.xpath("//i[@class=\"tm-icon-menu\"]"));
	}

	/* Location. */
	public void goToEditComponentLocation(WebElement widget) {
		this.clickOnEditWidget(widget);
	}

	public void deletePagekit(WebElement widget) {
		this.clickOnEditWidget(widget);
		this.deleteWidget(widget);
	}

	public void goToEditUser() {
		this.clickOn(By.xpath("//a[@title=\"Profile\"]"));
	}

	public void goToEditRegisteredUser(WebElement widget) {
		this.clickOnEditWidget(widget);
	}

	public List<WebElement> getWidgetsOnPage(){
		List<WebElement> widgets = this.findElements(By.xpath("//ul[@data-column]/li"));
		return widgets;
	}

	/*--------------------------------------------------------------------------------------------------------*/

	public void clickOnEditWidget(WebElement widget) {
		WebElement editButton = this.findElementJSByXPathStartingFrom(widget, ".//a[@class]");
		this.clickOn(editButton);
	}

	public boolean isUserWidget(WebElement widget) {
		try {
			WebElement element = this.findElementJSByXPathStartingFrom(widget, ".//a[@href]");
			if(element != null){
				//PageObjectLogging.logInfo("Widget " + this.getAttribute(widget, "data-id") + " is a user widget");
				return true;
			}
			return false;
		} catch (Exception ex) {
			//PageObjectLogging.logInfo("Widget " + this.getAttribute(widget, "data-id") + " is NOT a user widget");
			return false;
		}
	}

	public boolean isLocationWidget(WebElement widget){
		try {
			WebElement element = this.findElementJSByXPathStartingFrom(widget, ".//div[@class=\"pk-panel-background uk-contrast\"]");
			if(element != null){
				//PageObjectLogging.logInfo("Widget " + this.getAttribute(widget, "data-id") + " is a location widget");
				return true;
			}
			return false;
		} catch (Exception ex) {
			//PageObjectLogging.logInfo("Widget " + this.getAttribute(widget, "data-id") + " is NOT a location widget");
			return false;
		}
	}

	public boolean isFeedWidget(WebElement widget){
		WebElement containerDivFeedLoader;
		try{
			containerDivFeedLoader = this.findElementStartingFrom(widget, By.xpath("//component/div[@class=\"uk-text-center\"]"));
		}catch (NoSuchElementException ex){
			// if it does not exists it means that it is not a feed widget
			return false;
		}
		String attributeName = "style";
		String expectedValueAttribute = "display: none;";
		if(!this.waitForElementThatChangesProperty(containerDivFeedLoader, attributeName, expectedValueAttribute)){
			throw new IllegalStateException("Loader feed widget disappeared after timeout");
		}
		WebElement element = this.findElementJSByXPathStartingFrom(widget, ".//component/div/h3[@class=\"uk-panel-title\"]");
		if(element != null){
			//PageObjectLogging.logInfo("Widget " + this.getAttribute(widget, "data-id") + " is a feed widget");
			return true;
		}
		return false;
	}

	public void deleteWidget(WebElement widget){
		WebElement deleteButton = this.findElementJSByXPathStartingFrom(widget, ".//a[@class=\"pk-icon-delete pk-icon-hover\"]");
		this.clickOn(deleteButton);
	}

	//canceling delete operation of a widget leaves the form widget open: in that state it is not possible to click the edit button
	public boolean isWidgetFormOpen(WebElement widget){
		WebElement deleteIconForm = this.findElementJSByXPathStartingFrom(widget, ".//ul[@class=\"uk-subnav pk-subnav-icon\"]/li[3]");
		String styleAttributeValue = this.getAttribute(deleteIconForm, "style");
		if(styleAttributeValue.equals("display: none;")) return false;
		else return true;
	}

}
