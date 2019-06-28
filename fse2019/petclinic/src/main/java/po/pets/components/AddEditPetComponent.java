package po.pets.components;

import custom_classes.Dates;
import custom_classes.PetNames;
import custom_classes.PetTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AddEditPetComponent extends BasePageObject implements PageComponent {

    public AddEditPetComponent(WebDriver driver) {
        super(driver);
    }

    public void addNewPet(String petName, String date, String petType){
        int maxAttemptsRefresh = 5;
        boolean exceptionThrown = true;
        while(exceptionThrown && maxAttemptsRefresh > 0){
            try{
                this.selectOptionInDropdown(By.xpath("//div[@class=\"form-group\"]/div/select"), petType);
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

        this.type(By.name("name"), petName);
        this.handleDate(date);

        if(petName.length() > 40){
            this.clickOn(By.xpath("//div[@class=\"form-group\"]//button[@type=\"submit\"]"));
        }else{
            this.bruteForceClick(By.xpath("//div[@class=\"form-group\"]//button[@type=\"submit\"]"),
                    By.xpath("//pet-form/h2[text()=\"Pet\"]"), 20 , "submit newPet: failed to click submit button");
        }
    }

    /* --------- added */

    public void handleDate(String date){
        this.typeWithoutClear(By.xpath("//div[@class=\"form-group\"]//input[@type=\"date\"]"), date);
    }
}
