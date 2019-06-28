package custom_classes;

import po_utils.TestData;

public enum WidgetFeedPostContent implements TestData {
    DONTSHOW (""),
    SHOWALLPOSTS ("1"),
    SHOWFIRSTPOST ("2");

    private final String postContent;

    WidgetFeedPostContent(String postContent){
        this.postContent = postContent;
    }

    public String value(){
        return this.postContent;
    }
}
