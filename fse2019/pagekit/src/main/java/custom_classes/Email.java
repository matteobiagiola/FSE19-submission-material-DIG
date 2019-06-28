package custom_classes;

import po_utils.TestData;

public enum Email implements TestData {

    ASD ("asd@asd.com"),
    FOO ("foo@bar.com");

    private final String email;

    Email(String email){
        this.email = email;
    }

    public String value(){
        return this.email;
    }
}
