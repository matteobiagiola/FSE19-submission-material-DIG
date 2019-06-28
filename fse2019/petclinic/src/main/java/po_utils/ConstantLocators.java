package po_utils;

import org.openqa.selenium.By;

/*
* This class describes the locators to be used in order to wait for a page to be loaded: it should be an element that constitutes the core of the page.
* Ex: for a page with a form the locator should point to the form web element.
* */
public enum ConstantLocators {

    INDEX (By.xpath("//h1[text()=\"Welcome to Petclinic\"]")),
    WELCOME (By.xpath("//ul[@class=\"nav navbar-nav navbar-right\"]/li[@class=\"dropdown open\"]")),
    NEW_OWNER_PAGE (By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]")),
    NEW_PET_PAGE (By.xpath("//div[@class=\"form-group\"]//button[@type=\"submit\"]")),
    OWNER_INFO_PAGE (By.xpath("//tbody/tr/td/a[text()=\"Edit Owner\"]")),
    OWNER_INFO_PAGE_SECOND_TABLE (By.xpath("(//table)[2]/tbody/tr")),
    OWNERS_PAGE (By.xpath("//owner-list//table")),
    OWNERS_PAGE_TABLE (By.xpath("//table/tbody/tr")),
    VETS_PAGE (By.xpath("//table/tbody/tr")),
    VISITS_PAGE (By.xpath("//div[@class=\"form-group\"]/textarea"));


    private final By locator;

    ConstantLocators(By locator){
        this.locator = locator;
    }

    public By value(){
        return this.locator;
    }
}
