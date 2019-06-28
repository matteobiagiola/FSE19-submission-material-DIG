package custom_classes;

import po_utils.TestData;

public enum LongFirstNames implements TestData {

    GEORGE ("George"),
    BETTY ("Betty"),
    EDUARDO ("Eduardo"),
    LONG ("HaroldHaroldHaroldHaroldHaroldHaroldHaroldHaroldHaroldHarold");

    private final String firstName;

    LongFirstNames(String firstName){
        this.firstName = firstName;
    }

    public String value(){
        return this.firstName;
    }
}
