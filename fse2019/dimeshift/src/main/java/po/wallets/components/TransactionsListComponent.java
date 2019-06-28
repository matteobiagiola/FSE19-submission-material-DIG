package po.wallets.components;

import custom_classes.Id;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class TransactionsListComponent extends BasePageObject implements PageComponent {

    public TransactionsListComponent(WebDriver driver){
        super(driver);
    }

    public void selectTransaction(Id id){
        this.clickOn(By.xpath("(//div[@id=\"transactions_container\"]//div[@class=\"list-group-item item\"])[" + id.value + "]"));
    }

    public void goToTransactionsNextMonth(){
        this.clickOn(By.id("goto_next"));
    }

    public void goToTransactionsCurrentMonth(){
        this.clickOn(By.id("goto_current"));
    }

    public void goToTransactionsPreviousMonth(){
        this.clickOn(By.id("goto_prev"));
    }

    public boolean isPreviousMonthViewAvailable(){
        if(this.isMonthViewAvailable("goto_prev")){
            return true;
        }
        return false;
    }

    public boolean isCurrentMonthViewAvailable(){
        if(this.isMonthViewAvailable("goto_current")){
            return true;
        }
        return false;
    }

    public boolean isNextMonthViewAvailable(){
        if(this.isMonthViewAvailable("goto_next")){
            return true;
        }
        return false;
    }

    public boolean isMonthViewAvailable(String id){
        if(this.isElementPresentOnPage(By.id(id))){
            return true;
        }
        return false;
    }

    public boolean isTransactionPresent(Id id){
        if(this.isElementPresentOnPage(By.xpath("(//div[@id=\"transactions_container\"]//div[@class=\"list-group-item item\"])[" + id.value + "]"))){
            return true;
        }
        return false;
    }

    public boolean areTransactionsPresent(){
        if(this.isElementPresentOnPage(By.xpath("//*[@id=\"transactions_container\"]/div/div[@class=\"list-group-item item\"]"))){
            return true;
        }
        return false;
    }
}
