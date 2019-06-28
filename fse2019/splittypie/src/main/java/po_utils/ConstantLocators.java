package po_utils;

import org.openqa.selenium.By;

/*
* This class describes the locators to be used in order to wait for a page to be loaded: it should be an element that constitutes the core of the page.
* Ex: for a page with a form the locator should point to the form web element.
* */
public enum ConstantLocators {

    ERROR (By.xpath("//h1[text()=\"Something went wrong\"]")),
    QUICK_ADD (By.xpath("//div[@class=\"modal-body pull-up-30\"]//input[@placeholder=\"Example: 10 tickets\"]")),
    VIEW_TRANSFER (By.xpath("//button[@class=\"btn btn-danger delete-transfer\"]")),
    ADD_EDIT_TRANSACTION (By.xpath("//input[@placeholder=\"Example: Tickets to museum\"]")),
    HOME (By.xpath("//section[@id=\"features\"]")),
    SHARE (By.xpath("//div[@class=\"modal-body\"]//input[@readonly]")),
    EVENT_DETAILS(By.xpath("//div[contains(@class,\"liquid-child\")]")),
    CONFIRMATION (By.xpath("//div[@class=\"modal-footer\"]/button[text()=\"Yes\"]")),
    ADD_EDIT_EVENT (By.xpath("//input[@placeholder=\"Example: Trip to Barcelona\"]"));


    private final By locator;

    ConstantLocators(By locator){
        this.locator = locator;
    }

    public By value(){
        return this.locator;
    }
}
