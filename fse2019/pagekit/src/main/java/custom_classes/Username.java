package custom_classes;

import po_utils.TestData;

public enum Username implements TestData {

    ADMIN ("admin"),
    FOO ("foo");

    private final String username;

    Username(String username){
        this.username = username;
    }

    public String value(){
        return this.username;
    }
}
