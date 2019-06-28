package custom_classes;

import po_utils.TestData;

public enum Extension implements TestData {

    PAGE ("Page"),
    STORAGE ("Storage"),
    USER ("User");

    private final String extension;

    Extension(String extension){
        this.extension = extension;
    }

    public String value(){
        return this.extension;
    }
}
