package custom_classes;

import po_utils.TestData;

public enum MenuSubItems implements TestData {
    SHOW_ALL ("All"),
    SHOW_ACTIVE ("Active");

    private final String subItem;

    MenuSubItems(String subItem){
        this.subItem = subItem;
    }

    public String value(){
        return this.subItem;
    }
}
