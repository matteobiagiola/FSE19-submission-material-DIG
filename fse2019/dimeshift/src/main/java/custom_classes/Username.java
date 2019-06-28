package custom_classes;

import po_utils.TestData;

public enum Username implements TestData {

    ASD ("asdasd"),
    //FOOBAR ("foobar"),
    //NAME ("namesurname"),
    COMPANY ("namecompany");

    private final String username;

    Username(String username){
        this.username = username;
    }

    public String value(){
        return this.username;
    }
}
