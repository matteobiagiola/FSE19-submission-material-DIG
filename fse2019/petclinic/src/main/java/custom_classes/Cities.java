package custom_classes;

import po_utils.TestData;

public enum Cities implements TestData {

    MADISON ("Madison"),
    MCFARLAND ("McFarland"),
    WINDSOR ("Windsor"),
    MONONA ("Monona");

    private final String city;

    Cities(String city){
        this.city = city;
    }

    public String value(){
        return this.city;
    }
}
