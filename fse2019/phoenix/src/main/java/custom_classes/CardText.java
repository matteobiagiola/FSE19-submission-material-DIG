package custom_classes;

import po_utils.TestData;

public enum CardText implements TestData {

    EXAMPLE ("Example"),
    PERSONAL ("Personal"),
    SHOPPING ("Shopping"),
    LAUNDRY ("Laundry");

    private final String cardText;

    CardText(String cardText){
        this.cardText = cardText;
    }

    public String value(){
        return this.cardText;
    }
}
