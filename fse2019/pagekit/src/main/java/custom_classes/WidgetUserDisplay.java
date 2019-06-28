package custom_classes;

import po_utils.TestData;

public enum WidgetUserDisplay implements TestData {
    THUMBNAIL ("thumbnail"),
    LIST ("list");

    private final String userDisplay;

    WidgetUserDisplay(String userDisplay){
        this.userDisplay = userDisplay;
    }

    public String value(){
        return this.userDisplay;
    }
}
