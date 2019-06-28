package custom_classes;

import po_utils.TestData;

public enum NotWentWellPosts implements TestData {

    CLARITY ("Clarity on requirements"),
    LATE_WORK ("Late work nights"),
    LONG_BUILDS ("Build takes to long"),
    TERRIBLE_COFFEE ("Coffee taste terrible"),
    MEETINGS ("Too many meetings"),
    DEV_TIME ("Dev time consumed in next release estimation");

    private final String notWentWellPost;

    NotWentWellPosts(String notWentWellPost){
        this.notWentWellPost = notWentWellPost;
    }

    public String value(){
        return this.notWentWellPost;
    }
}
