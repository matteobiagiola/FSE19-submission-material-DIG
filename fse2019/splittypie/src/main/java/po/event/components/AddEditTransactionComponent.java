package po.event.components;

import custom_classes.Dates;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageComponent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditTransactionComponent extends BasePageObject implements PageComponent {

    public AddEditTransactionComponent(WebDriver driver) {
        super(driver);
    }

    public void selectParticipant(String participant){
        this.selectOptionInDropdown(By.xpath("//div[contains(@class, \"transaction-payer\")]//select"), participant);
    }

    public List<WebElement> getParticipants(){
        return this.findElements(By.xpath("//div[contains(@class, \"transaction-payer\")]//select/option[not(text()=\"Select payer...\")]"));
    }

    public boolean isParticipantPresent(String participant){
        List<WebElement> participantOptions = this.getParticipants();
        for(WebElement participantOption: participantOptions){
            String optionText = this.getText(participantOption);
            if(optionText != null && !optionText.isEmpty()){
                if(optionText.equals(participant)) return true;
            }else{
                throw new IllegalStateException("isParticipantPresent: option text must not be null nor empty");
            }
        }
        return false;
    }

    public void typeTransactionName(String transactionName){
        this.type(By.xpath("//input[contains(@class, \"transaction-name\")]"), transactionName);
    }

    public void typeAmount(int amount){
        this.type(By.xpath("//input[contains(@class, \"transaction-amount\")]"), String.valueOf(amount));
    }

    public boolean isEdit(){
        return this.isElementPresentOnPage(By.xpath("//button[contains(@class,\"delete-transaction\")]"));
    }

    public void save(){
        this.clickOn(By.xpath("//button[contains(@class,\"save-transaction\") and text()=\"Save\"]"));
        int timeout = 500;
        if(!this.waitForElementBeingPresentOnPage(ConstantLocators.EVENT_DETAILS.value(), timeout, TimeUnit.MILLISECONDS)){
            // we are still in AddEditEventContainerPage so save button should still be on the page
            if(this.isElementPresentOnPage(By.xpath("//button[contains(@class,\"save-transaction\") and text()=\"Save\"]"))){
                // if it is on the page repeat the process
                this.save();
            }else{
                throw new IllegalStateException("save button not present in the page");
            }
        }
    }

    public void create(){
        this.clickOnSelenium(By.xpath("//button[contains(@class,\"save-transaction\") and text()=\"Create\"]"));
    }

    public void createOrSave(){
        this.save();
    }

    public void excludeFromSharing(String participant){
        List<WebElement> participantLabels = this.findElements(By.xpath("//div[contains(@class, \"transaction-participants\")]/label"));
        for(WebElement participantLabel: participantLabels){
            String participantLabelText = this.getText(participantLabel);
            if(participantLabelText.equals(participant)){
                WebElement inputParticipant = this.findElementJSByXPathStartingFrom(participantLabel, "./input");
                this.clickOn(inputParticipant);
            }
        }
    }

    public void pickDateFromCalendar(Dates date){
        String[] monthAndDay = date.value().split("/");
        String month = monthAndDay[0];
        String day = monthAndDay[1];
        this.clickOn(By.xpath("//input[contains(@class, \"transaction-date\")]"));
        this.handleMonth(month);
        this.handleDay(day);
        if(!this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"pika-lendar\"]"))){
            throw new IllegalStateException("pickDateFromCalendar: calendar notification not handled properly");
        }
    }

    public void handleDay(String day){
        this.clickOnSelenium(By.xpath("//div[@class=\"pika-lendar\"]//tbody/tr/td[@data-day=" + "\"" + day + "\"" + "]"));
    }

    public void handleMonth(String month){
        WebElement currentMonthElement = this.findElement(By.xpath("(//div[@class=\"pika-title\"]/div)[1]"));
        String currentMonth = this.getText(currentMonthElement);
        Pattern pattern = Pattern.compile("([A-Z][a-z]+)");
        Matcher matcher = pattern.matcher(currentMonth);
        if(matcher.find()){
            currentMonth = matcher.group(1); //take only the first match
        }else{
            throw new IllegalStateException("handleMonth: no match regex month");
        }
        int currentMonthNumber = this.toMonthNumber(currentMonth);
        int desiredMonthNumber = this.toMonthNumber(month);
        int diff = Math.abs(currentMonthNumber - desiredMonthNumber);
        while(diff > 0){
            this.clickOnSelenium(By.xpath("//button[@class=\"pika-next\"]"));
            diff--;
        }
    }

    public int toMonthNumber(String monthName){
        switch (monthName){
            case "January":
                return 1;
            case "01":
                return 1;
            case "February":
                return 2;
            case "02":
                return 2;
            case "March":
                return 3;
            case "03":
                return 3;
            case "April":
                return 4;
            case "04":
                return 4;
            case "May":
                return 5;
            case "05":
                return 5;
            case "June":
                return 6;
            case "06":
                return 6;
            case "July":
                return 7;
            case "07":
                return 7;
            case "August":
                return 8;
            case "08":
                return 8;
            case "September":
                return 9;
            case "09":
                return 9;
            case "October":
                return 10;
            case "10":
                return 10;
            case "November":
                return 11;
            case "11":
                return 11;
            case "December":
                return 12;
            case "12":
                return 12;
            default:
                throw new IllegalStateException("toMonthNumber: unknown month name " + monthName);
        }
    }

    public void cancel(){
        this.clickOn(By.xpath("//div[@class=\"form-group hidden-xs\"]/button[contains(@class,\"btn btn-link\")]"));
    }

    public void deleteTransaction(){
        this.clickOnSelenium(By.xpath("//button[contains(@class, \"delete-transaction\")]"));
    }
}
