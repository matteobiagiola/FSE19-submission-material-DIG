package custom_classes;

import po_utils.TestData;

public enum WidgetUnit implements TestData {
    METRIC ("metric"),
    IMPERIAL ("imperial");

    private final String unit;

    WidgetUnit(String unit){
        this.unit = unit;
    }

    public String value(){
        return this.unit;
    }
}
