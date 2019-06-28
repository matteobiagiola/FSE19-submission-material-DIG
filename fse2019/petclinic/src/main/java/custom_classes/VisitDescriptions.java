package custom_classes;

import po_utils.TestData;

public enum VisitDescriptions implements TestData {

    ONE ("I don't know"),
    TWO ("Should be ok soon"),
    THREE ("He is not fine isn't he"),
    FOUR ("I should check");

    private final String visitDescription;

    VisitDescriptions(String visitDescription){
        this.visitDescription = visitDescription;
    }

    public String value(){
        return this.visitDescription;
    }
}
