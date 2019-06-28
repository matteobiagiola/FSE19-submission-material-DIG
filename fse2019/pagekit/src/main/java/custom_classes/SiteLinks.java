package custom_classes;

import po_utils.TestData;

public enum SiteLinks implements TestData, SiteLinkOrPage {
    NEWS ("News"),
    PROMOTIONS ("Promotions"),
    DISCOUNTS ("Discounts");

    private final String siteLink;

    SiteLinks(String siteLink){
        this.siteLink = siteLink;
    }

    public String value(){
        return this.siteLink;
    }
}
