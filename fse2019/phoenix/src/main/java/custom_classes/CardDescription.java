package custom_classes;

import po_utils.TestData;

public enum CardDescription implements TestData {

    EXAMPLE ("This is an example of a text"),
    PERSONAL ("Strictly personal"),
    SHOPPING ("Let's buy some food"),
    LAUNDRY ("Text for laundry");

    private final String cardDescription;

    CardDescription(String cardDescription){
        this.cardDescription = cardDescription;
    }

    public String value(){
        return this.cardDescription;
    }
}
