package custom_classes;

import po_utils.TestData;

public enum SitePages implements TestData, SiteLinkOrPage {
    HOME ("Home"),
    SPECIAL_POSTS ("Posts"),
    DAILY_SECTION ("Daily section");

    private final String sitePage;

    SitePages(String sitePage){
        this.sitePage = sitePage;
    }

    public String value(){
        return this.sitePage;
    }
}
