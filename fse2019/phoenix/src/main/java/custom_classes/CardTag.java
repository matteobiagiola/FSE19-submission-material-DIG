package custom_classes;

import po_utils.TestData;

public enum CardTag implements TestData {

    GREEN ("tag green"),
    YELLOW ("tag yellow"),
    ORANGE ("tag orange"),
    RED ("tag red"),
    PURPLE ("tag purple"),
    BLUE ("tag blue");

    private final String cardTag;

    CardTag(String cardTag){
        this.cardTag = cardTag;
    }

    public String value(){
        return this.cardTag;
    }
}
