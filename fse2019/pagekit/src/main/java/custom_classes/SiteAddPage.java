package custom_classes;

import po_utils.TestData;

public enum SiteAddPage implements TestData {
    LINK ("Link"),
    PAGE ("Page");

    private final String siteAddPage;

    SiteAddPage(String siteAddPage){
        this.siteAddPage = siteAddPage;
    }

    public String value(){
        return this.siteAddPage;
    }
}
