package custom_classes;

import po_utils.TestData;

public enum MenuActions implements TestData {
    DASHBOARD ("dashboard"),
    USERS ("users"),
    SITE ("site");

    private final String action;

    MenuActions(String action){
        this.action = action;
    }

    public String value(){
        return this.action;
    }
}
