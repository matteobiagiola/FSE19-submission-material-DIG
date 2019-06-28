package custom_classes;

import po_utils.TestData;

public enum HideInMenu implements TestData {
    YES ("Yes"),
    NO ("No");

    private final String hide;

    HideInMenu(String hide){
        this.hide = hide;
    }

    public String value(){
        return this.hide;
    }
}
