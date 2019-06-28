package po.goals.components;

import custom_classes.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GoalBasicSettingsComponent extends BasePageObject implements PageComponent {

    private Map<String,Integer> mapMonthToNumber = new LinkedHashMap<String, Integer>();
    private final boolean editGoal;

    public GoalBasicSettingsComponent(WebDriver driver, boolean editGoal){
        super(driver);
        this.editGoal = editGoal;
    }

    public void addGoalToWallet(Goals goal, WalletNames walletName){
        this.typeJS(By.id("input_name"), goal.value());
        int walletPosition = this.isWalletPresent(walletName);
        this.clickOn(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a[" + walletPosition + "]"));
        this.clickOn(By.id("button_step1_next"));
    }

    public void editGoal(Goals goal){
        this.typeJS(By.id("input_name"), goal.value());
        this.clickOn(By.id("button_step1_next"));
    }


    public void goBack(){
        this.clickOn(By.id("button_step1_back"));
    }

    public int isWalletPresent(WalletNames walletName){
        if(this.isElementPresentOnPage(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a"))){
            List<WebElement> elements = this.findElements(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a"));
            for(int i = 0 ; i < elements.size(); i++){
                if(elements.get(i).getText().trim().equals(walletName.value())){
                    return i + 1;
                }
            }
            return -1;
        }
        return -1;
    }

    public boolean isEdit(){
        return this.editGoal;
    }
}
