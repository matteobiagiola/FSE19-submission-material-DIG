package po.goals.components;

import custom_classes.Id;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class GoalsListComponent extends BasePageObject implements PageComponent {

    public GoalsListComponent(WebDriver driver){
        super(driver);
    }

    public void removeGoal(Id id){
        this.clickOn(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]//button[@class=\"btn btn-default btn-xs remove_plan_button\"]"));
    }

    public void viewGoalReport(Id id){
        this.clickOn(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]//a"));
    }

    public void editGoal(Id id){
        this.clickOn(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]//button[@class=\"btn btn-default btn-xs edit_plan_button\"]"));
    }

    public void createNew(){
        this.clickOn(By.id("button_create_new"));
    }

    public boolean goalExist(Id id){
        if(this.isElementPresentOnPage(By.xpath("//*[@id]/div/div[1]/div/div/div[2]/ul/li[" + id.value + "]"))){
            return true;
        }
        return false;
    }

}
