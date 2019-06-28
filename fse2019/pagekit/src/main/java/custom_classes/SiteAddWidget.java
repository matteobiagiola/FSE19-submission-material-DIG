package custom_classes;

import po_utils.TestData;

public enum SiteAddWidget implements TestData {
    MENU ("Menu"),
    TEXT ("Text"),
    LOGIN ("Login");

    private final String siteAddWidget;

    SiteAddWidget(String siteAddWidget){
        this.siteAddWidget = siteAddWidget;
    }

    public String value(){
        return this.siteAddWidget;
    }
}
