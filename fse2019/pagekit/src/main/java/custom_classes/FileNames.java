package custom_classes;

import po_utils.TestData;

public enum FileNames implements TestData {

    PAGEKIT_LOGO ("pagekit-logo.svg"),
    PAGEKIT_LOGO_CONTRAST ("pagekit-logo-contrast.svg");

    private final String fileName;

    FileNames(String fileName) {
        this.fileName = fileName;
    }

    public String value() {
        return this.fileName;
    }
}
