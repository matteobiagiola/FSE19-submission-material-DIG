package custom_classes;

import po_utils.TestData;

public enum CardComment implements TestData {

    EXAMPLE ("Comment example"),
    PERSONAL ("Do not comment, it is personal"),
    SHOPPING ("Very good shopping list"),
    LAUNDRY ("I don't want to wash my clothes");

    private final String cardDescription;

    CardComment(String cardDescription){
        this.cardDescription = cardDescription;
    }

    public String value(){
        return this.cardDescription;
    }
}
