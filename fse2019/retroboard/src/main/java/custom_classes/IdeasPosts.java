package custom_classes;

import po_utils.TestData;

public enum IdeasPosts implements TestData {

    REQUIREMENTS ("Probe further with the client on requirements"),
    STORYPOINTS ("Plan for fewer storypoints"),
    FRIDAYNIGHT ("Have a fridaynight drink with the team"),
    MEETINGS ("Less meetings"),
    SUPPLIER ("Discuss with supplier"),
    DEVOPS ("Ask for edit rights at devops");

    private final String ideaPost;

    IdeasPosts(String ideaPost){
        this.ideaPost = ideaPost;
    }

    public String value(){
        return this.ideaPost;
    }
}
