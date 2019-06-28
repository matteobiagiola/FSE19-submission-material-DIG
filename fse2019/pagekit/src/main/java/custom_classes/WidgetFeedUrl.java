package custom_classes;

import po_utils.TestData;

public enum WidgetFeedUrl implements TestData {
    PAGEKIT ("http://pagekit.com/blog/feed"),
    WEATHER ("http://rss.weatherzone.com.au/?u=12994-1285&lt=aploc&lc=5594&obs=1&fc=1&warn=1"),
    SPORT ("https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU&tag=soccer");

    private final String feedUrl;

    WidgetFeedUrl(String feedUrl){
        this.feedUrl = feedUrl;
    }

    public String value(){
        return this.feedUrl;
    }
}
