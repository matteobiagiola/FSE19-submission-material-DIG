package custom_classes;

import po_utils.TestData;

public enum PetNames implements TestData {

    LEO ("Leo"),
    BASIL ("Basil"),
    ROSY ("Rosy"),
    JEWEL ("Jewel");

    private final String petName;

    PetNames(String petName){
        this.petName = petName;
    }

    public String value(){
        return this.petName;
    }
}
