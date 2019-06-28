package custom_classes;

import po_utils.TestData;

public enum WidgetMenus implements TestData, Widget {
    ALL ("All"),
    UNASSIGNED ("Unassigned");

    private final String widgetMenu;

    WidgetMenus(String widgetMenu){
        this.widgetMenu = widgetMenu;
    }

    public String value(){
        return this.widgetMenu;
    }
}
