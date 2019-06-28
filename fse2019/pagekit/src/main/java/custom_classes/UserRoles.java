package custom_classes;

import po_utils.FormValue;
import po_utils.TestData;

public enum UserRoles implements TestData, FormValue {

    ANONYMOUS ("Anonymous"),
    AUTHENTICATED ("Authenticated"),
    SYSTEM_ADMIN ("System admin"),
    PUBLIC_USER ("Public user");

    private final String userRole;

    UserRoles(String userRole){
        this.userRole = userRole;
    }

    public String value(){
        return this.userRole;
    }
}
