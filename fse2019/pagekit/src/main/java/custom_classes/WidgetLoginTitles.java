package custom_classes;

import po_utils.TestData;

public enum WidgetLoginTitles implements TestData, Widget {
    LOGIN ("Login"),
    LOGOUT ("Logout"),
    LOGGED_IN ("Logged In"),
    LOGGED_OUT ("Logged Out");

    private final String widgetLogin;

    WidgetLoginTitles(String widgetLogin){
        this.widgetLogin = widgetLogin;
    }

    public String value(){
        return this.widgetLogin;
    }
}
