package custom_classes;

import po_utils.TestData;

public enum Dates implements TestData {

    DATE1 ("10/01/2012"),
    DATE2 ("20/02/2013"),
    DATE3 ("11/12/2017"),
    DATE4 ("12/11/2018");

    private final String date;

    Dates(String date){
        this.date = date;
    }

    public String value(){
        return this.date;
    }
}
