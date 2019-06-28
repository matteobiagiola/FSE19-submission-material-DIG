package custom_classes;

import po_utils.TestData;

public enum View implements TestData {

    LOGIN ("User Login"),
    LOGOUT ("User Logout"),
    REGISTRATION ("User Registration"),
    PROFILE ("User Profile"),
    PASSWORD_RESET ("User Password Reset");

    private final String view;

    View(String view){
        this.view = view;
    }

    public String value(){
        return this.view;
    }
}
