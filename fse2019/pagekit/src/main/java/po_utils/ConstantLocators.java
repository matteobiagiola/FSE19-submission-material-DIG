package po_utils;

import org.openqa.selenium.By;

/*
* This class describes the locators to be used in order to wait for a page to be loaded: it should be an element that constitutes the core of the page.
* Ex: for a page with a form the locator should point to the form web element.
* */
public enum ConstantLocators {

    DASHBOARD (By.xpath("//a[text()=\"Add Widget\"]")),
    DELETE_ITEM (By.xpath("//div[@class='uk-modal uk-open']/div[@class='uk-modal-dialog']//div[@class=\"uk-margin uk-modal-content\"]")),
    ADD_EDIT_USER (By.xpath("//form[@id=\"user-edit\"]//h2")),
    USER_LIST (By.xpath("//table/tbody")),
    PERMISSIONS (By.id("permissions")),
    ROLES (By.id("roles")),
    ADD_ITEM (By.id("form-name")),
    SELECT_LINK (By.id("form-style")),
    SELECT_IMAGE (By.xpath("//div[@class=\"uk-modal uk-open\"]//div[@class=\"uk-form\"]//table/tbody/tr[1]/td[3]")), //it is a bit weak because it relies on an exiting element: test cases don't modify this element so it always exists but it is not a good practice
    PAGES (By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li/a[text()=\"Not Linked\"]")),
    ADD_EDIT_PAGE (By.id("textareahtml")),
    ADD_EDIT_LINK (By.xpath("//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\" and text()=\"Select\"]")),
    WIDGETS_SPAN (By.xpath("//ul[@class=\"uk-nav uk-nav-side\"]/li/a[text()=\"Unassigned\"]/span")),
    ADD_EDIT_TEXT (By.id("textareahtml")),
    ADD_EDIT_VISIBILITY (By.xpath("//ul[@class=\"uk-list uk-margin-top-remove\"]")),
    ADD_EDIT_VISIBILITY_BACKUP (By.xpath("//label[text()=\"All Pages\"]")),
    ADD_EDIT_MENU (By.id("form-level")),
    ADD_EDIT_LOGIN (By.xpath("//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\"]"));


    private final By locator;

    ConstantLocators(By locator){
        this.locator = locator;
    }

    public By value(){
        return this.locator;
    }
}