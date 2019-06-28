package custom_classes;

import po_utils.TestData;

public enum MetaDescriptions implements TestData {
    HOME ("Home page"),
    IMAGE_TITLE ("Image with title"),
    IMAGE_TITLE_PRICE ("Image with title and price");

    private final String metaDescription;

    MetaDescriptions(String metaDescription){
        this.metaDescription = metaDescription;
    }

    public String value(){
        return this.metaDescription;
    }
}
