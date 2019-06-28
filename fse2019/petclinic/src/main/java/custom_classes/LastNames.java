package custom_classes;

import po_utils.TestData;

public enum LastNames implements TestData {

    FRANKLIN ("Franklin"),
    DAVIS ("Davis"),
    RODRIQUEZ ("Rodriquez"),
    BLACK ("Black");

    private final String lastName;

    LastNames(String lastName){
        this.lastName = lastName;
    }

    public String value(){
        return this.lastName;
    }
}
