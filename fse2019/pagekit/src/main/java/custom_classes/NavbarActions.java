package custom_classes;

import po_utils.TestData;

public enum NavbarActions implements TestData {
    LIST ("list"),
    PERMISSIONS ("Permissions"),
    ROLES ("Roles"),
    USER_SETTINGS ("User settings"),
    PAGES ("Pages"),
    WIDGETS ("Widgets");

    private final String action;

    NavbarActions(String action){
        this.action = action;
    }

    public String value(){
        return this.action;
    }
}
