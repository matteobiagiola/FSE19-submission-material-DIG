package custom_classes;

import po_utils.TestData;

public enum WidgetFeedTitle implements TestData {
    PAGEKITNEWS ("Pagekit news"),
    WEATHER ("Weather"),
    SPORTNEWS ("Sport news");

    private final String feedTitle;

    WidgetFeedTitle(String feedTitle){
        this.feedTitle = feedTitle;
    }

    public String value(){
        return this.feedTitle;
    }
}
