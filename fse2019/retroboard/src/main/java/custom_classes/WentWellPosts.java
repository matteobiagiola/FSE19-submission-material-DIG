package custom_classes;

import po_utils.TestData;

public enum WentWellPosts implements TestData {

    TEAM_BONDING ("Team bonding"),
    DELIVERING ("Delivering on time"),
    QUICK_CLIENT ("The client is reviewing work delivery quickly, usually within 24 hours"),
    PROBLEM_X ("We're on track to solve Problem X within the budget"),
    GOOD_TEAM ("The team is sending over work that needs few revisions"),
    DESIGN ("I think the design is going to really delight the users of the website");

    private final String wentWellPost;

    WentWellPosts(String wentWellPost){
        this.wentWellPost = wentWellPost;
    }

    public String value(){
        return this.wentWellPost;
    }
}
