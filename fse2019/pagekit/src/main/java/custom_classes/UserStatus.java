package custom_classes;

import po_utils.TestData;

public enum UserStatus implements TestData {

    ACTIVE ("1"),
    BLOCKED ("0");

    private final String status;

    UserStatus(String status){
        this.status = status;
    }

    public String value(){
        return this.status;
    }
}
