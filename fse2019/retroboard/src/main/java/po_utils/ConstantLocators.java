package po_utils;

import org.openqa.selenium.By;

/*
* This class describes the locators to be used in order to wait for a page to be loaded: it should be an element that constitutes the core of the page.
* Ex: for a page with a form the locator should point to the form web element.
* */
public enum ConstantLocators {

    LOGIN (By.xpath("//button[text()=\"Let's start\"]")),
    HOME (By.xpath("//nav/label[text()=\"Create\"]")),
    MENU (By.xpath("((//body/div)[2]/div//button)[2]")),
    SHARE (By.xpath("//button[text()=\"Copy URL to Clipboard\"]")),
    RETROSPECTIVE (By.xpath("//span[contains(@class,\"SessionName__editIcon___AOR2M\")]"));

    private final By locator;

    ConstantLocators(By locator){
        this.locator = locator;
    }

    public By value(){
        return this.locator;
    }
}
