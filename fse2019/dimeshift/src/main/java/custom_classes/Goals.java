package custom_classes;

import po_utils.TestData;

public enum Goals implements TestData {

    SKI ("ski"),
    MUSEUM ("museum"),
    HOUSE ("house"),
    CAR ("car");

    private final String goal;

    Goals(String goal){
        this.goal = goal;
    }

    public String value(){
        return this.goal;
    }
}
