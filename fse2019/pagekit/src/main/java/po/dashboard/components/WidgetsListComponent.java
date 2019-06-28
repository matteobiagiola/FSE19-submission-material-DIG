package po.dashboard.components;

import custom_classes.Widgets;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

import java.util.List;

public class WidgetsListComponent extends BasePageObject implements PageComponent {


    public WidgetsListComponent(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getWidgetsOnPage(){
        List<WebElement> widgets = this.findElements(By.xpath("//ul[@data-column]/li"));
        //PageObjectLogging.logInfo("Number of widgets: " + widgets.size());
        return widgets;
    }

    public void addWidget(Widgets widgets){
        this.clickOn(By.xpath("//a[text()=\"Add Widget\"]"));
        this.clickOn(By.xpath("//a[text()=" + "\"" + widgets.value() + "\"" + "]"));
        try {
            Thread.sleep(500); // I didn't have a more clever solution
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*if(!this.waitUntilAnElementIsAddedToWidgetList(2000, 50)){
            throw new IllegalStateException("Add widget not loaded properly");
        }*/
        //it fails when there is more than one widget of a particular type
        /*if(widgets.value().equals(Widgets.USER.value())){
            if(!this.waitForElementBeingPresentOnPage(By.xpath("//form/div/span[text()=\"User Type\"]"))){
                throw new IllegalStateException("Add widget user not loaded properly");
            }
        }
        else if(widgets.value().equals(Widgets.LOCATION.value())){
            String attributeName = "style";
            String defaultAttributeValue = "display: none;";
            if(!this.waitForElementWhosePropertyDisappears(By.xpath("//form[@class=\"pk-panel-teaser uk-form uk-form-stacked\"]"), attributeName, defaultAttributeValue)){
                throw new IllegalStateException("Add widget location not loaded properly");
            }
        }else if(widgets.value().equals(Widgets.FEED.value())){
            if(!this.waitForElementBeingPresentOnPage(By.xpath("//form/div/label[text()=\"Title\"]"))){
                throw new IllegalStateException("Add widget feed not loaded properly");
            }
        }*/
    }

    public boolean waitUntilAnElementIsAddedToWidgetList(long timeoutMilliseconds, long pollingIntervalMilliseconds){
        List<WebElement> oldWidgets = this.getWidgetsOnPage();
        List<WebElement> newWidgets = this.getWidgetsOnPage();
        PageObjectLogging.logInfo("Old: " + oldWidgets.size() + ". New: " + newWidgets.size() + ". Timeout: " + timeoutMilliseconds);
        long start = 0;
        while(newWidgets.size() == oldWidgets.size() && timeoutMilliseconds > 0){
            try {
                start = System.currentTimeMillis();
                Thread.sleep(pollingIntervalMilliseconds);
                newWidgets = this.getWidgetsOnPage();
                timeoutMilliseconds -= (System.currentTimeMillis() - start);
                PageObjectLogging.logInfo("Old: " + oldWidgets.size() + ". New: " + newWidgets.size() + ". Timeout: " + timeoutMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(newWidgets.size() > oldWidgets.size()) return true;
        return false;
    }
}
