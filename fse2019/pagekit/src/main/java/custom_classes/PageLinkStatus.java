package custom_classes;

import po_utils.TestData;

public enum PageLinkStatus implements TestData {
    ENABLED ("Enabled"),
    DISABLED ("Disabled");

    private final String status;

    PageLinkStatus(String status){
        this.status = status;
    }

    public String value(){
        return this.status;
    }
}
