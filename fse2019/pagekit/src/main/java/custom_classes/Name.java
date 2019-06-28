package custom_classes;

import po_utils.TestData;

public enum Name implements TestData {

    JOHN ("john"),
    MARK ("mark");

    private final String name;

    Name(String name){
        this.name = name;
    }

    public String value(){
        return this.name;
    }
}
