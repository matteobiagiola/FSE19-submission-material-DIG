package po.owners.components;

import custom_classes.PetNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class OwnerInformationComponent extends BasePageObject implements PageComponent {

    public OwnerInformationComponent(WebDriver driver) {
        super(driver);
    }

    public void goToEditOwner() {
        this.clickOnEditOwner();
    }

    public void goToAddNewPet() {
        this.clickOnAddNewPet();
    }

    public void goToEditPetThroughName(PetNames petName) {
        this.clickOnPet(petName);
    }

    public void goToEditPetThroghEditLink(PetNames petName) {
        this.clickOnEditPet(petName);
    }

    public void goToVisits(PetNames petName) {
        this.clickOnAddVisit(petName);
    }

    public void goToIndex(){
        this.clickOn(By.xpath("//a[@title=\"home page\"]"));
    }

    public void clickOnEditOwner(){
//		this.clickOn(By.xpath("//tbody/tr/td/a[text()=\"Edit Owner\"]"));
        this.bruteForceClick(By.xpath("//tbody/tr/td/a[text()=\"Edit Owner\"]"),
                By.xpath("//owner-details/h2[text()=\"Owner Information\"]"), 20 , "clickOnEditOwner: failed to click button");
    }

    public void clickOnAddNewPet(){
//		this.clickOn(By.xpath("//tbody/tr/td/a[text()=\"Add New Pet\"]"));
        this.bruteForceClick(By.xpath("//tbody/tr/td/a[text()=\"Add New Pet\"]"),
                By.xpath("//owner-details/h2[text()=\"Owner Information\"]"), 20 , "clickOnAddNewPet: failed to click button");
    }

    public List<WebElement> getPetsList(){
        return this.findElements(By.xpath("(//table[@class=\"table table-striped\"])[2]/tbody/tr"));
    }

    public boolean petExists(PetNames petName){
        List<WebElement> pets = this.getPetsList();
        for (WebElement petTrTable: pets){
            WebElement aLinkWithPetName = this.findElementStartingFrom(petTrTable, By.xpath("./td/dl/dd/a"));
            String petNameCandidate = this.getText(aLinkWithPetName);
            if(petNameCandidate.equals(petName.value())){
                return true;
            }
        }
        return false;
    }

    public void clickOnPet(PetNames petName){
        List<WebElement> pets = this.getPetsList();
        for (WebElement petTrTable: pets){
            WebElement aLinkWithPetName = this.findElementStartingFrom(petTrTable, By.xpath("./td/dl/dd/a"));
            String petNameCandidate = this.getText(aLinkWithPetName);
            if(petNameCandidate.equals(petName.value())){
                this.clickOn(aLinkWithPetName);
                return;
            }
        }
        throw new IllegalStateException("clickOnPet: pet name " + petName.value() + " not found");
    }

    public void clickOnEditPet(PetNames petName){
        int petIndexToClick = this.getPetIndex(petName);
        try{
            WebElement petTrTable = this.getPetsList().get(petIndexToClick);
            WebElement aLinkWithPetName = this.findElementStartingFrom(petTrTable,
                    By.xpath("./td/table[@class=\"table-condensed\"]/tbody/tr/td/a[text()=\"Edit Pet\"]"));
            this.clickOn(aLinkWithPetName);
        }catch (IndexOutOfBoundsException ex){
            throw new IllegalStateException("clickOnEditPet: pet name " + petName.value() + " not found");
        }
    }

    public void clickOnAddVisit(PetNames petName){
        int petIndexToClick = this.getPetIndex(petName);
        try{
            WebElement petTrTable = this.getPetsList().get(petIndexToClick);
            WebElement aLinkWithPetName = this.findElementStartingFrom(petTrTable,
                    By.xpath("./td/table[@class=\"table-condensed\"]/tbody/tr/td/a[text()=\"Add Visit\"]"));
            this.clickOn(aLinkWithPetName);
        }catch (ArrayIndexOutOfBoundsException ex){
            throw new IllegalStateException("clickOnEditPet: pet name " + petName.value() + " not found");
        }
    }

    public int getPetIndex(PetNames petName){
        List<WebElement> pets = this.getPetsList();
        int index = 0;
        for (WebElement petTrTable: pets){
            WebElement aLinkWithPetName = this.findElementStartingFrom(petTrTable, By.xpath("./td/dl/dd/a"));
            String petNameCandidate = this.getText(aLinkWithPetName);
            if(petNameCandidate.equals(petName.value())){
                return index;
            }
            index++;
        }
        throw new IllegalStateException("getPetIndex pet name : " + petName.value() + " not found");
    }
}
