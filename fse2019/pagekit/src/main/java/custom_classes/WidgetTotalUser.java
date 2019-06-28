package custom_classes;

import po_utils.TestData;

public enum WidgetTotalUser implements TestData {
    SHOW ("1"),
    HIDE ("");

    private final String totalUser;

    WidgetTotalUser(String totalUser){
        this.totalUser = totalUser;
    }

    public String value(){
        return this.totalUser;
    }
}
