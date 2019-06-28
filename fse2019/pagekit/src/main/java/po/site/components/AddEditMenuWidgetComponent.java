package po.site.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.CheckCondition;
import po_utils.PageComponent;

import java.util.List;

public class AddEditMenuWidgetComponent extends BasePageObject implements PageComponent {

    public AddEditMenuWidgetComponent(WebDriver driver) {
        super(driver);
    }

    public void typeTitle(String menuTitle){
        this.type(By.id("form-title"), menuTitle);
    }

    public void selectMenu(String siteMenuName){
        this.selectOptionInDropdown(By.id("form-menu"), siteMenuName);
    }

    public void selectLevel(int level){
        this.selectOptionInDropdown(By.id("form-level"), String.valueOf(level));
    }

    public void selectDepth(String depth){
        this.selectOptionInDropdown(By.id("form-depth"), depth);
    }

    public void selectSubItems(String subItem){
        this.clickOnSubItem(subItem);
    }

    public void clickOnSubItem(String subItem){
        List<WebElement> subItemLabels = this.findElements(By.xpath("//div[@class=\"pk-width-content uk-form-horizontal uk-row-first\"]//div[@class=\"uk-form-controls uk-form-controls-text\"]/p/label"));
        for(WebElement subItemLabel: subItemLabels){
            String subItemLabelText = this.getText(subItemLabel);
            CheckCondition.checkState(subItemLabelText != null && !subItemLabelText.isEmpty(), "clickOnSubItem: subItemLabelText must not be null or empty -> " + subItemLabelText);
            if(subItemLabelText.equals(subItem)){
                WebElement inputRadioButton = this.findElementJSByXPathStartingFrom(subItemLabel, "./input");
                this.clickOn(inputRadioButton);
            }
        }
    }

    public boolean isMenuPresent(String menuName){
        return this.isOptionPresentInDropdown(By.id("form-menu"), menuName);
    }
}
