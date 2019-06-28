package custom_classes;

import po_utils.TestData;

public enum BreadcrumbActions implements TestData {
    WALLETS ("wallets"),
    HOME ("home"),
    GOALS("plan_your_expenses");

    private final String action;

    BreadcrumbActions(String action){
        this.action = action;
    }

    public String value(){
        return this.action;
    }
}
