package custom_classes;

import po_utils.TestData;

public enum WidgetLocation implements TestData {
    TRENTO ("Trento"),
    MILANO ("Milan"),
    GENOA ("Genoa");

    private final String location;

    WidgetLocation(String location){
        this.location = location;
    }

    public String value(){
        return this.location;
    }
}
