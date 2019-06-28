package custom_classes;

import po_utils.TestData;

public enum Transactions implements TestData {

    SHOPPING ("Shopping"),
    TICKETS ("Tickets"),
    MUSEUM ("Museum"),
    LUNCH ("Lunch"),
    DINNER ("Dinner"),
    DISCO ("Disco");

    private final String transaction;

    Transactions(String transaction){
        this.transaction = transaction;
    }

    public String value(){
        return this.transaction;
    }
}
