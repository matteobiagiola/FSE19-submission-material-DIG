package custom_classes;

import po_utils.TestData;

public enum PeopleNames implements TestData {

    JOHN ("John"),
    MIKE ("Mike"),
    MARK ("Mark");

    private final String name;

    PeopleNames(String name){
        this.name = name;
    }

    public String value(){
        return this.name;
    }
}
