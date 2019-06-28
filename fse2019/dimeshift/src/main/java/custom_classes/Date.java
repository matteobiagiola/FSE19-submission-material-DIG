package custom_classes;

import po_utils.TestData;

public enum Date implements TestData {

    WINTER ("21/12/2018"),
    FALL ("21/9/2018"),
    SUMMER ("21/6/2018"),
    SPRING ("21/3/2018");

    private final String date;

    Date(String date){
        this.date = date;
    }

    public String value(){
        return this.date;
    }
}
