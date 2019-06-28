package custom_classes;

import po_utils.TestData;

public enum LinkTypes implements TestData {
    LINK ("Link"),
    URL_ALIAS ("URL Alias"),
    REDIRECT ("Redirect");

    private final String linkType;

    LinkTypes(String linkType){
        this.linkType = linkType;
    }

    public String value(){
        return this.linkType;
    }
}
