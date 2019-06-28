package custom_classes;

import po_utils.TestData;

public enum ListNames implements TestData {

    MY_LIST ("My list"),
    ANOTHER_LIST ("Another list"),
    PERSONAL_LIST ("Personal list"),
    WORK_LIST ("Work list");

    private final String listName;

    ListNames(String listName){
        this.listName = listName;
    }

    public String value(){
        return this.listName;
    }
}
