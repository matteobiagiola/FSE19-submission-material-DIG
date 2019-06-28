package custom_classes;

import po_utils.TestData;

public enum Password implements TestData {

    ASD("asdfghjkl123"),
    //FOO("asdfghjkl456"),
    //NAME("asdfghjkl789"),
    COMPANY("asdfghkl100");
    //TOOSHORT("asdf");

    private final String password;

    Password(String password) {
        this.password = password;
    }

    public String value() {
        return this.password;
    }
}
