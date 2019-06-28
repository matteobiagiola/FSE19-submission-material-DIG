package custom_classes;

import po_utils.TestData;

public enum Widgets implements TestData {
    USER ("User"),
    LOCATION ("Location"),
    FEED ("Feed");

    private final String widget;

    Widgets(String widget){
        this.widget = widget;
    }

    public String value(){
        return this.widget;
    }
}
