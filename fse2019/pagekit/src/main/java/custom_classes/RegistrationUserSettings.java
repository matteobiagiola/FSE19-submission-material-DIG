package custom_classes;

import po_utils.TestData;

public enum RegistrationUserSettings implements TestData {
    DISABLED ("admin"),
    ENABLED ("guest"),
    ENABLED_BUT_APPROVAL ("approval");

    private final String registrationUserSetting;

    RegistrationUserSettings(String registrationUserSetting){
        this.registrationUserSetting = registrationUserSetting;
    }

    public String value(){
        return this.registrationUserSetting;
    }
}
