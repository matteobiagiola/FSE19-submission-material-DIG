package custom_classes;

import po_utils.TestData;

public enum BoardNames implements TestData {

    MY_RETROSPECTIVE ("My Retrospective"),
    WORK ("Work"),
    PROJECT_X ("Project X"),
    PROJECT_Y ("Project Y");

    private final String boardName;

    BoardNames(String boardName){
        this.boardName = boardName;
    }

    public String value(){
        return this.boardName;
    }
}
