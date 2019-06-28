package po_apogen;

import custom_classes.Dates;
import custom_classes.PetNames;
import custom_classes.PetTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class NewPetComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for NewPet (state10)
	 */
	public NewPetComponent(WebDriver driver) {
		super(driver);
	}

	public void submit(PetNames petName, Dates date, PetTypes petType){
		int maxAttemptsRefresh = 5;
		boolean exceptionThrown = true;
		while(exceptionThrown && maxAttemptsRefresh > 0){
			try{
				this.selectOptionInDropdown(By.xpath("//div[@class=\"form-group\"]/div/select"), petType.value());
				exceptionThrown = false;
			}catch (IllegalStateException e){
				this.waitForTimeoutExpires(500);
				this.getDriver().navigate().refresh();
			}
			maxAttemptsRefresh--;
		}
		if(maxAttemptsRefresh == 0){
			throw new IllegalStateException("Error in NewPetPage dropdown empty after 5 attempts");
		}

		this.type(By.name("name"), petName.value());
		this.handleDate(date);

		this.bruteForceClick(By.xpath("//div[@class=\"form-group\"]//button[@type=\"submit\"]"),
				By.xpath("//pet-form/h2[text()=\"Pet\"]"), 20 , "submit newPet: failed to click submit button");
	}

	/* --------- added */

	public void handleDate(Dates date){
		this.typeWithoutClear(By.xpath("//div[@class=\"form-group\"]//input[@type=\"date\"]"), date.value());
	}

//	public void pickDateFromCalendar(Dates date){
//		String[] dayMonthYear = date.value().split("/");
//		String day = dayMonthYear[0];
//		String month = dayMonthYear[1];
//		String year = dayMonthYear[2];
//		this.clickOn(By.xpath("//input[contains(@class, \"transaction-date\")]"));
//		this.handleMonth(month);
//		this.handleDay(day);
//		if(!this.waitForElementBeingInvisibleOnPage(By.xpath("//div[@class=\"pika-lendar\"]"))){
//			throw new IllegalStateException("pickDateFromCalendar: calendar notification not handled properly");
//		}
//	}
//
//	public void handleDay(String day){
//		this.clickOnSelenium(By.xpath("//div[@class=\"pika-lendar\"]//tbody/tr/td[@data-day=" + "\"" + day + "\"" + "]"));
//	}
//
//	public void handleMonth(String month){
//		WebElement currentMonthElement = this.findElement(By.xpath("(//div[@class=\"pika-title\"]/div)[1]"));
//		String currentMonth = this.getText(currentMonthElement);
//		Pattern pattern = Pattern.compile("([A-Z][a-z]+)");
//		Matcher matcher = pattern.matcher(currentMonth);
//		if(matcher.find()){
//			currentMonth = matcher.group(1); //take only the first match
//		}else{
//			throw new IllegalStateException("handleMonth: no match regex month");
//		}
//		int currentMonthNumber = this.toMonthNumber(currentMonth);
//		int desiredMonthNumber = this.toMonthNumber(month);
//		int diff = currentMonthNumber - desiredMonthNumber;
//		if(diff < 0){
//			// go forward
//			while(diff < 0){
//				this.clickOnSelenium(By.xpath("//button[@class=\"pika-next\"]"));
//				diff++;
//			}
//		}else if(diff > 0){
//			// go back
//			while (diff > 0){
//				this.clickOnSelenium(By.xpath("//button[@class=\"pika-prev\"]"));
//				diff--;
//			}
//		}
//	}
//
//	public int toMonthNumber(String monthName){
//		switch (monthName){
//			case "January":
//				return 1;
//			case "01":
//				return 1;
//			case "February":
//				return 2;
//			case "02":
//				return 2;
//			case "March":
//				return 3;
//			case "03":
//				return 3;
//			case "April":
//				return 4;
//			case "04":
//				return 4;
//			case "May":
//				return 5;
//			case "05":
//				return 5;
//			case "June":
//				return 6;
//			case "06":
//				return 6;
//			case "July":
//				return 7;
//			case "07":
//				return 7;
//			case "August":
//				return 8;
//			case "08":
//				return 8;
//			case "September":
//				return 9;
//			case "09":
//				return 9;
//			case "October":
//				return 10;
//			case "10":
//				return 10;
//			case "November":
//				return 11;
//			case "11":
//				return 11;
//			case "December":
//				return 12;
//			case "12":
//				return 12;
//			default:
//				throw new IllegalStateException("toMonthNumber: unknown month name " + monthName);
//		}
//	}

}
