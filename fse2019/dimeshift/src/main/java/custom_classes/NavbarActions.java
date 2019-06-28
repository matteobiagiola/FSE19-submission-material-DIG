package custom_classes;

import po_utils.TestData;

public enum NavbarActions implements TestData {
    SETTINGS ("settings"),
    WALLETS ("wallets"),
    GOALS ("goals"),
    HOME ("home"),
    REGISTER ("register"),
    SIGNIN ("signin"),
    LOGOUT ("logout");

    private final String action;

    NavbarActions(String action){
        this.action = action;
    }

    public String value(){
        return this.action;
    }
}
