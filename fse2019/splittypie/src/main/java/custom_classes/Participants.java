package custom_classes;

import po_utils.TestData;

public enum Participants implements TestData {

    MATTEO ("Matteo"),
    MARCO ("Marco"),
    JOHN ("John"),
    MIKE ("Mike"),
    MARK ("Mark"),
    LUKE ("Luke");

    private final String participant;

    Participants(String participant){
        this.participant = participant;
    }

    public String value(){
        return this.participant;
    }
}
