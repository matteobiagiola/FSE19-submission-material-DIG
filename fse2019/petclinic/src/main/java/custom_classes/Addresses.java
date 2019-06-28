package custom_classes;

import po_utils.TestData;

public enum Addresses implements TestData {

    LIBERTY ("110 W. Liberty St."),
    CARDINAL ("638 Cardinal Ave."),
    COMMERCE ("2693 Commerce St."),
    FRIENDLY ("563 Friendly St.");

    private final String address;

    Addresses(String address){
        this.address = address;
    }

    public String value(){
        return this.address;
    }
}
