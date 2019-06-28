package po.event.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

import java.util.List;

public class EventDetailsComponent extends BasePageObject implements PageComponent {

    public EventDetailsComponent(WebDriver driver) {
        super(driver);
    }

    public void addTransaction(){
        this.clickOn(By.xpath("//a[@title=\"Add New Transaction\"]"));
    }

    public List<WebElement> getSettlementList(){
        return this.findElements(By.xpath("//div[@class=\"panel panel-default\"]/div[@class=\"panel-body\"]/li[@class=\"settlement-item\"]"));
    }

    public List<WebElement> getTransactionList(){
        return this.findElements(By.xpath("//div[@class=\"list-group ember-view\"]/div[contains(@class, \"list-group-item\")]"));
    }

    public boolean isSettlementPresent(int id){
        List<WebElement> settlementList = this.getSettlementList();
        if(id <= settlementList.size()) return true; // id starts from 1
        return false;
    }

    public void settleUp(int id){
        List<WebElement> settlementList = this.getSettlementList();
        WebElement settleElement = settlementList.get(id - 1); // id starts from 1
        WebElement settleUpButton = this.findElementJSByXPathStartingFrom(settleElement, "./button");
        this.clickOn(settleUpButton);
    }

    public boolean isSettlementListPresent(){
        if(this.isElementPresentOnPage(By.xpath("//div[@role=\"alert\"]"))){
            WebElement divContainer = this.findElement(By.xpath("//div[@role=\"alert\"]"));
            String text = this.getText(divContainer);
            if(text != null && !text.isEmpty()){
                if(text.equals("There is no need to settle anything.")) return false;
                throw new IllegalStateException("isSettlementListPresent: unknown text of div alert " + text);
            }else{
                throw new IllegalStateException("isSettlementListPresent: text of div alert must not be null nor empty");
            }
        }
        return true;
    }

    public boolean isTransactionListPresent(){
        if(this.isElementPresentOnPage(By.xpath("//div[@role=\"alert\"]"))){
            WebElement divContainer = this.findElement(By.xpath("//div[@role=\"alert\"]"));
            String text = this.getText(divContainer);
            if(text != null && !text.isEmpty()){
                if(text.equals("There are no transactions yet.")) return false;
                throw new IllegalStateException("isTransactionListPresent: unknown text of div alert " + text);
            }else{
                throw new IllegalStateException("isTransactionListPresent: text of div alert must not be null nor empty");
            }
        }
        return true;
    }

    public boolean isTransactionPresent(int id){
        List<WebElement> transactionList = this.getTransactionList();
        if(id <= transactionList.size()) {
            return true; //id starts from one
        }
        return false;
    }

    public boolean isTransaction(int id){
        List<WebElement> transactionList = this.getTransactionList();
        WebElement transaction = transactionList.get(id - 1); //id starts from 1
        WebElement transactionDescriptionElement = this.findElementJSByXPathStartingFrom(transaction, "./div[@class=\"transaction-list-item-description\"]");
        String description = this.getText(transactionDescriptionElement);
        //PageObjectLogging.logInfo("Description: " + description);
        if(description != null && !description.isEmpty()){
            if(description.contains("paid for")) return true;
            return false;
        }else{
            throw new IllegalStateException("isTransaction: description must not be null nor empty");
        }
    }

    public boolean isSettlementTransaction(int id){
        List<WebElement> transactionList = this.getTransactionList();
        WebElement transaction = transactionList.get(id - 1); //id starts from one
        WebElement transactionDescriptionElement = this.findElementJSByXPathStartingFrom(transaction, "./div[@class=\"transaction-list-item-description\"]");
        String description = this.getText(transactionDescriptionElement);
        //PageObjectLogging.logInfo("Description: " + description);
        if(description != null && !description.isEmpty()){
            if(description.contains("settled up with")) {
                return true;
            }
            return false;
        }else{
            throw new IllegalStateException("isSettlementTransaction: description must not be null nor empty");
        }
    }

    public void clickOnTransaction(int id){
        List<WebElement> transactionList = this.getTransactionList();
        WebElement transaction = transactionList.get(id - 1); //id starts from one
        this.clickOn(transaction);
    }
}
