package custom_classes;

import po_utils.TestData;

public enum WidgetNumberOfUsers implements TestData {
    SIX ("6"),
    TWELVE ("12"),
    EIGHTEEN ("18"),
    TWENTYFOUR ("24");

    private final String numberOfUsers;

    WidgetNumberOfUsers(String numberOfUsers){
        this.numberOfUsers = numberOfUsers;
    }

    public String value(){
        return this.numberOfUsers;
    }
}
