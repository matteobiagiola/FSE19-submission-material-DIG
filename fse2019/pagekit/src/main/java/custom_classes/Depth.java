package custom_classes;

import po_utils.TestData;

public enum Depth implements TestData {
    NO_LIMIT ("No Limit"),
    ONE ("1"),
    TWO ("2"),
    THREE ("3"),
    FOUR ("4"),
    FIVE ("5"),
    SIX ("6"),
    SEVEN ("7"),
    EIGHT ("8"),
    NINE ("9"),
    TEN ("10");

    private final String depth;

    Depth(String depth){
        this.depth = depth;
    }

    public String value(){
        return this.depth;
    }
}
