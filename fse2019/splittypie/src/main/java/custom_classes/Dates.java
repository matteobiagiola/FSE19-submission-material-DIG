package custom_classes;

import po_utils.TestData;

public enum Dates implements TestData {

    SPRING ("03/21"),
    WINTER ("12/21"),
    FALL ("09/21"),
    SUMMER ("06/21");

    private final String date;

    Dates(String date){
        this.date = date;
    }

    public String value(){
        return this.date;
    }
}
