package po_utils;

import org.openqa.selenium.By;

/*
* This class describes the locators to be used in order to wait for a page to be loaded: it should be an element that constitutes the core of the page.
* Ex: for a page with a form the locator should point to the form web element.
* */
public enum ConstantLocators {

    LOGIN (By.id("user_password")),
    SIGNUP (By.id("user_email")),
    BOARDS (By.xpath("//div[@class=\"board add-new\"]")),
    BOARDS_LIST_MODAL_OPEN(By.xpath("//nav[@id=\"boards_nav\"]//div[@class=\"dropdown\"]/ul[3]/li/a[text()=\"View all boards\"]")),
    LIST (By.xpath("//div[@class=\"list add-new\"]")),
    BOARD_OPEN (By.xpath("//input[@id=\"board_name\"]")),
    CARD_DETAILS (By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//form//textarea[@placeholder=\"Write a comment...\"]"));

    private final By locator;

    ConstantLocators(By locator){
        this.locator = locator;
    }

    public By value(){
        return this.locator;
    }
}
