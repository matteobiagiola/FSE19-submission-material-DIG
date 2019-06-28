package po.goals.components;

import custom_classes.Amount;
import custom_classes.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GoalSettingsComponent extends BasePageObject implements PageComponent {

    private Map<String,Integer> mapMonthToNumber = new LinkedHashMap<String, Integer>();

    private enum Month{
        JANUARY ("January"),
        FEBRUARY ("February"),
        MARCH ("March"),
        APRIL ("April"),
        MAY ("May"),
        JUNE ("June"),
        JULY ("July"),
        AUGUST ("August"),
        SEPTEMBER ("September"),
        OCTOBER ("October"),
        NOVEMBER ("November"),
        DECEMBER ("December");

        private final String month;

        Month(String month){
            this.month = month;
        }

        public String value(){
            return this.month;
        }
    }

    public GoalSettingsComponent(WebDriver driver){
        super(driver);
        for (int i = 0; i < Month.values().length; i++) {
            String month = Month.values()[i].value();
            this.mapMonthToNumber.put(month, i + 1);
        }
    }

    // Branches in this method are not branches to cover: activeDay is selected anyway in the worst case
    public void addFurtherDetails(Amount toKeep, Date date){
        String today = this.getWebElementText(By.xpath("//td[contains(@class, \"day today\") or contains(@class, \"day old today\")]")); // it may be 'day (old) today' or 'day (old) today weekend'
        String activeDay = this.getWebElementText(By.xpath("//td[contains(@class, \"day active\")]")); // it may be 'day active' or 'day active weekend'
        String currentMonth = this.getCurrentMonth();
        String currentYear = this.getCurrentYear();
        String[] dateDayMonthYear = date.value().split("/");
        String dateDay = dateDayMonthYear[0];
        String dateMonth = dateDayMonthYear[1];
        String dateYear = dateDayMonthYear[2];
        if(Integer.valueOf(dateDay) < Integer.valueOf(today)){
            dateDay = activeDay;
        }
        WebElement nextMonthButton = this.findElement(By.xpath("//th[@class=\"next\"]/span"));
        if(Integer.valueOf(currentYear) < Integer.valueOf(dateYear)){ //it is not a branch to cover: activeDay is selected anyway in the worst case
            int diff = Integer.valueOf(dateYear) - Integer.valueOf(currentYear);
            int currentMonthNumber = this.mapMonthToNumber.get(currentMonth);
            int indexMonth = currentMonthNumber;
            for (int i = 0; i < diff; i++) {
                for (int j = indexMonth; j < 13; j++) {
                    this.clickOn(nextMonthButton);
                }
                indexMonth = 0;
            }
        }
        currentMonth = this.getCurrentMonth();
        int currentMonthNumber = this.mapMonthToNumber.get(currentMonth);
        if(currentMonthNumber < Integer.valueOf(dateMonth)){
            for (int i = 1; i < Integer.valueOf(dateMonth); i++) {
                this.clickOn(nextMonthButton);
            }
        }
        List<WebElement> webElementDays = this.findElements(By.xpath("//tbody/tr//td[@data-action]"));
        for (int i = 0; i < webElementDays.size(); i++) {
            WebElement webElementDay = webElementDays.get(i);
            if(this.getAttribute(webElementDay,"data-day").equals(date.value())){
                this.clickOn(webElementDay);
                break;
            }
        }
        this.typeJS(By.id("input_goal_balance"),String.valueOf(toKeep.value));
        this.clickOn(By.id("button_step2_save"));
    }

    public void goBackToPreviousStep(){
        this.clickOn(By.id("button_step2_back"));
    }

    private String getCurrentMonth(){
        String[] monthAndYear = this.extractCurrentMonthAndYear();
        return monthAndYear[0];
    }

    private String getCurrentYear(){
        String[] monthAndYear = this.extractCurrentMonthAndYear();
        return monthAndYear[1];
    }

    private String[] extractCurrentMonthAndYear(){
        String currentMonthAndYear = this.getWebElementText(By.xpath("//th[@title=\"Select Month\"]"));
        return currentMonthAndYear.split("\\s+");
    }
}
