package custom_classes;

import po_utils.TestData;

public enum WidgetMenuTitles implements TestData, Widget {
    SITE ("Site"),
    BLOG ("Blog"),
    PRODUCTS ("Products");

    private final String widgetMenu;

    WidgetMenuTitles(String widgetMenu){
        this.widgetMenu = widgetMenu;
    }

    public String value(){
        return this.widgetMenu;
    }
}
