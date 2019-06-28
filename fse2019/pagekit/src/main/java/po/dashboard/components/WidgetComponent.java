package po.dashboard.components;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

import java.util.List;

public class WidgetComponent extends BasePageObject implements PageComponent {


    public WidgetComponent(WebDriver driver) {
        super(driver);
    }

    //it assumes that the form of the user widget is open and visible
    public void addEditUserWidget(WidgetUserType widgetUserType, WidgetUserDisplay widgetUserDisplay,
                                  WidgetTotalUser widgetTotalUser, WidgetNumberOfUsers widgetNumberOfUsers) {
        this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetUserType.value() + "\"" + "]"));
        this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetUserDisplay.value() + "\"" + "]"));
        this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetTotalUser.value() + "\"" + "]"));
        this.selectOptionInDropdown(By.id("form-user-number"), widgetNumberOfUsers.value());
        this.clickOnSaveEditWidget();
    }

    public void clickOnEditWidget(WebElement widget) {
        WebElement editButton = this.findElementJSByXPathStartingFrom(widget, ".//a[@class]");
        this.clickOn(editButton);
    }

    public void clickOnEditUserLink(WebElement userWidget){
        WebElement linkEditUser = this.findElementJSByXPathStartingFrom(userWidget, ".//a[@href]");
        this.clickOn(linkEditUser);
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
        WebElement containerDivFeedLoader = this.findElementStartingFrom(widget, By.xpath("//component/div[@class=\"uk-text-center\"]"));
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

    //it assumes that the form of the location widget is open and visible
    public void addEditLocationWidget(WidgetLocation widgetLocation, WidgetUnit widgetUnit) {
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

    private boolean triggerDropdownMenu(WidgetLocation widgetLocation, int timeout) throws IllegalStateException{
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

    //it assumes that the form of the feed widget is open and visible
    public void addEditFeedWidget(WidgetFeedTitle widgetFeedTitle, WidgetFeedUrl widgetFeedUrl, WidgetFeedNumberOfPosts widgetFeedNumberOfPosts, WidgetFeedPostContent widgetFeedPostContent) {
        this.type(By.id("form-feed-title"), widgetFeedTitle.value());
        this.type(By.id("form-feed-url"), widgetFeedUrl.value());
        this.selectOptionInDropdown(By.id("form-feed-count"), String.valueOf(widgetFeedNumberOfPosts));
        this.clickOn(By.xpath("//form//input[@value=" + "\"" + widgetFeedPostContent.value() + "\"" + "]"));
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
