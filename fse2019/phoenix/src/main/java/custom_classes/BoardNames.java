package custom_classes;

import po_utils.TestData;

public enum BoardNames implements TestData {

    MY_BOARD ("My board"),
    ANOTHER_BOARD ("Another board"),
    PERSONAL_BOARD ("Personal board"),
    WORK_BOARD ("Work board");

    private final String boardName;

    BoardNames(String boardName){
        this.boardName = boardName;
    }

    public String value(){
        return this.boardName;
    }
}
