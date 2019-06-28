package custom_classes;

import po_utils.TestData;

public enum UserPassword implements TestData {

    ADMIN("asdfghjkl123"),
    FOO("foobar");

    private final String password;

    UserPassword(String password) {
        this.password = password;
    }

    public String value() {
        return this.password;
    }
}
