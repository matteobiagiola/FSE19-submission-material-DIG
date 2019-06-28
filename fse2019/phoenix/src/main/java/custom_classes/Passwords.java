package custom_classes;

import po_utils.TestData;

public enum Passwords implements TestData {

    JOHNDOE ("12345678"),
    ASD ("Foobar");

    private final String password;

    Passwords(String password){
        this.password = password;
    }

    public String value(){
        return this.password;
    }
}
