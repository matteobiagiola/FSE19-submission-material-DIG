package po_utils;

import org.openqa.selenium.By;

/*
* This class describes the locators to be used in order to wait for a page to be loaded
* */
public enum ConstantLocators {

    CREATE_GOAL_FIRST_STEP (By.id("button_step1_next")),
    CREATE_GOAL_SECOND_STEP (By.id("button_step2_save")),
    GOAL_DETAILS (By.id("reload_stats_button")),
    GOALS_MANAGER (By.id("button_create_new")),
    REGISTER (By.id("input_login")),
    SIGNIN (By.id("input_username")),
    HOME (By.id("demo_signup")),
    CONFIRMATION (By.xpath("//div[@class=\"modal-footer\"]//input[@class=\"process_button btn btn-danger pull-left\"]")),
    ADD_INCOME (By.id("input_amount")),
    ADD_WALLET (By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_name\"]")),
    SET_TOTAL_INCOME (By.id("input_total")),
    TRANSACTION_DETAILS (By.id("remove_transaction_button")),
    WALLET_ACCESS_MANAGER (By.xpath("//div[@class=\"modal-body modal-body-default\"]//input[@id=\"input_email\"]")),
    TRANSACTIONS_MANAGER (By.id("transactions_container")),
    WALLETS_MANAGER (By.xpath("//*[@class=\"filter_menu\"]")),
    LOADER (By.id("preloader"));

    private final By locator;

    ConstantLocators(By locator){
        this.locator = locator;
    }

    public By value(){
        return this.locator;
    }
}