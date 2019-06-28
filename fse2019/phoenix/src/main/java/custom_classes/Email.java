package custom_classes;

import po_utils.TestData;

public enum Email implements TestData {

    JOHNDOE ("john@phoenix-trello.com"),
    ASD ("asd@foo.com");

    private final String email;

    Email(String email){
        this.email = email;
    }

    public String value(){
        return this.email;
    }
}
