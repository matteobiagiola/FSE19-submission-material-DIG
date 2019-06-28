package custom_classes;

import po_utils.TestData;

public enum AddEditNavbarPoCallees implements TestData {
    PAGE_LINK ("AddEditPageLink"),
    MENU_TEXT_LOGIN ("AddEditMenuTextLogin");

    private final String poCallee;

    AddEditNavbarPoCallees(String poCallee){
        this.poCallee = poCallee;
    }

    public String value(){
        return this.poCallee;
    }
}
