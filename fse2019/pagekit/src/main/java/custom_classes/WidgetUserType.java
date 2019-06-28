package custom_classes;

import po_utils.TestData;

public enum WidgetUserType implements TestData {
    LOGGEDIN ("login"),
    LASTREGISTERED ("registered");

    private final String userType;

    WidgetUserType(String userType){
        this.userType = userType;
    }

    public String value(){
        return this.userType;
    }
}
