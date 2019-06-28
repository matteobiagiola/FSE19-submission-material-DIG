package custom_classes;

import po_utils.TestData;

public enum FirstNames implements TestData {

    GEORGE ("George"),
    BETTY ("Betty"),
    EDUARDO ("Eduardo"),
    HAROLD ("Harold");

    private final String firstName;

    FirstNames(String firstName){
        this.firstName = firstName;
    }

    public String value(){
        return this.firstName;
    }
}
