package custom_classes;

import po_utils.FormValue;
import po_utils.TestData;

public enum SiteMenus implements TestData, FormValue {

    MAIN ("Main"),
    NOT_LINKED ("Not Linked"),
    TRASH ("Trash"),
    SIDE_MENU ("Side Menu"),
    OTHER_MENU ("Other Menu");

    private final String siteMenu;

    SiteMenus(String siteMenu){
        this.siteMenu = siteMenu;
    }

    public String value(){
        return this.siteMenu;
    }
}
