package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.CheckCondition;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class VisibilityComponent extends BasePageObject implements PageComponent {

    public VisibilityComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isLinkOrPagePresent(String linkOrPageName){
        return this.getLabelPageTexts().contains(linkOrPageName);
    }

    public void clickOnPageInput(String pageName){
        List<WebElement> labelPages = this.getLabelPages();
        for(WebElement labelPage: labelPages){
            String labelText = this.getText(labelPage);
            if(labelText != null && !labelText.isEmpty()){
                if(labelText.equals(pageName)){
                    WebElement inputCheckbox = this.findElementJSByXPathStartingFrom(labelPage, "./input");
                    this.clickOn(inputCheckbox);
                    break;
                }
            }else{
                throw new IllegalStateException("clickOnPageInput: label text must not be null nor empty -> " + labelText);
            }
        }
    }

    public List<WebElement> getLabelPages(){
        return this.findElements(By.xpath("//div[@class=\"uk-form-horizontal\"]//label"));
    }

    public List<String> getLabelPageTexts(){
        List<WebElement> labelPages = this.getLabelPages();
        List<String> labelPageTexts = new ArrayList<String>();
        for(WebElement labelPage: labelPages){
            String labelText = this.getText(labelPage);
            CheckCondition.checkState(labelText != null && !labelText.isEmpty(), "getLabelPageTexts: label text must not be null or empty -> " + labelText);
            labelPageTexts.add(labelText);
        }
        //PageObjectLogging.logInfo("Label page texts: " + labelPageTexts);
        return labelPageTexts;
    }
}
