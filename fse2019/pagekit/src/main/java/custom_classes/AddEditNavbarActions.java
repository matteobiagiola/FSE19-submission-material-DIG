package custom_classes;

import po_utils.TestData;

public enum AddEditNavbarActions implements TestData {
    //Page
    PAGE_CONTENT ("Page content"),
    PAGE_META ("Page meta"),

    //Link
    LINK_SETTINGS ("Link settings"),
    LINK_META ("Link meta"),

    //Menu
    MENU_SETTINGS ("Menu settings"),
    MENU_VISIBILITY ("Menu visibility"),

    //Text
    TEXT_SETTINGS ("Text settings"),
    TEXT_VISIBILITY ("Text visibility"),

    //Login
    LOGIN_SETTINGS ("Login settings"),
    LOGIN_VISIBILITY ("Login visibility");

    private final String action;

    AddEditNavbarActions(String action){
        this.action = action;
    }

    public String value(){
        return this.action;
    }
}
