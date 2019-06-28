package po.list.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;
import po_utils.PageObjectLogging;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BoardListComponent extends BasePageObject implements PageComponent {

    public BoardListComponent(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getLists(){
        return this.findElements(By.xpath("//div[@class=\"lists-wrapper\"]/div[@id]"));
    }

    public WebElement getListByName(String listName){
        List<WebElement> lists = this.getLists();
        for(WebElement list: lists){
            WebElement headerFour = this.findElementJSByXPathStartingFrom(list, "./div/header/h4");
            String currentListName = this.getText(headerFour);
            if(currentListName.equals(listName)){
                return list;
            }
        }
        throw new IllegalStateException("getListByName: not possible to find list with name " + listName);
    }

    public boolean existsList(){
        return this.getLists().size() > 0;
    }

    public boolean isListPresent(String listName){
        List<WebElement> lists = this.getLists();
        for(WebElement list: lists){
            WebElement headerFour = this.findElementJSByXPathStartingFrom(list, "./div/header/h4");
            String currentListName = this.getText(headerFour);
            if(currentListName.equals(listName)){
                return true;
            }
        }
        return false;
    }

    public boolean isCardPresentOnList(String listName, int cardId){
        WebElement list = this.getListByName(listName);
        List<WebElement> cards = this.findElementsJSByXPathStartingFrom(list, ".//div[@class=\"cards-wrapper\"]/div[@id]");
        if(cards.size() > 0 && cardId <= cards.size()){
            return true;
        }
        return false;
    }

    public void openListForm(){
        this.clickOn(By.xpath("//div[@class=\"list add-new\"]"));
    }

    public void closeListForm(){
        this.clickOn(By.xpath("//form[@id=\"new_list_form\"]/a[text()=\"cancel\"]"));
    }

    public void createList(String listName){
        this.type(By.xpath("//form[@id=\"new_list_form\"]/input[@id=\"list_name\"]"), listName);
        this.clickOn(By.xpath("//form[@id=\"new_list_form\"]/button[text()=\"Save list\"]"));
        long timeoutMillis = 200;
        this.waitForTimeoutExpires(timeoutMillis); // wait that the new list is added to the DOM: no other way to do it since I don't know in which position (index) the element will be added
    }

    public void updateList(String listName){
        this.type(By.xpath("//form[@id=\"new_list_form\"]/input[@id=\"list_name\"]"), listName);
        this.clickOn(By.xpath("//form[@id=\"new_list_form\"]/button[text()=\"Update list\"]"));
    }

    public void clickOnList(String listName){
        WebElement list = this.getListByName(listName);
        WebElement elementToClick = this.findElementJSByXPathStartingFrom(list, "./div/header");
        this.clickOn(elementToClick);
        /*List<WebElement> lists = this.getLists();
        for(WebElement list: lists){
            WebElement headerFour = this.findElementJSByXPathStartingFrom(list, "./div/header/h4");
            String currentListName = this.getText(headerFour);
            if(currentListName.equals(listName)){
                this.clickOn(list);
                break;
            }
        }*/
    }

    //cardId starts from 1 while list counter starts from 0
    public void clickOnCardOnList(String listName, int cardId){
        WebElement list = this.getListByName(listName);
        List<WebElement> cards = this.findElementsJSByXPathStartingFrom(list, ".//div[@class=\"cards-wrapper\"]/div[@id]");
        WebElement card = cards.get(cardId - 1);
        this.clickOn(card);
    }

    public void openNewMemberForm(){
        this.clickOn(By.xpath("//header[@class=\"view-header\"]/ul[@class=\"board-users\"]//li/a[@class=\"add-new\"]"));
    }

    public void closeNewMemberForm(){
        this.clickOn(By.xpath("//header[@class=\"view-header\"]/ul[@class=\"board-users\"]//li/ul[@class=\"drop-down active\"]//a[text()=\"cancel\"]"));
    }

    // //header[@class="view-header"]/ul[@class="board-users"]//li/ul[@class="drop-down active"]//div[@class="error"]
    public void addNewMember(String email){
        this.type(By.id("crawljax_member_email"), email);
        this.clickOn(By.xpath("//header[@class=\"view-header\"]/ul[@class=\"board-users\"]//li/ul[@class=\"drop-down active\"]//button[text()=\"Add member\"]"));
    }

    public void openNewCardForm(String listName){
        WebElement list = this.getListByName(listName);
        WebElement cardElement = this.findElementJSByXPathStartingFrom(list, "./div/footer/a[@class=\"add-new\"]");
        this.clickOn(cardElement);
    }

    public void closeNewCardForm(String listName){
        WebElement list = this.getListByName(listName);
        WebElement cancelLink = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form[@id=\"new_card_form\"]/a[text()=\"cancel\"]");
        this.clickOn(cancelLink);
    }

    public void addNewCardToList(String listName, String cardText){
        WebElement list = this.getListByName(listName);
        WebElement textArea = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form/textarea[@id=\"card_name\"]");
        WebElement addButton = this.findElementJSByXPathStartingFrom(list, "./div/footer/div/form/button[text()=\"Add\"]");
        this.type(textArea, cardText);
        this.clickOn(addButton);
        long timeoutMillis = 200;
        this.waitForTimeoutExpires(timeoutMillis);
    }

    public boolean isBoardShared(){
        return !this.isElementPresentOnPage(By.xpath("//header[@class=\"view-header\"]/ul[@class=\"board-users\"]//li/a[@class=\"add-new\"]"));
    }
}
