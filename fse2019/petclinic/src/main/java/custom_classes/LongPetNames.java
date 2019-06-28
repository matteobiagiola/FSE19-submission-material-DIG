package custom_classes;

import po_utils.TestData;

public enum LongPetNames implements TestData {

    LEO ("Leo"),
    BASIL ("Basil"),
    ROSY ("Rosy"),
    JEWEL ("JewelJewelJewelJewelJewelJewelJewelJewelJewelJewelJewel");

    private final String petName;

    LongPetNames(String petName){
        this.petName = petName;
    }

    public String value(){
        return this.petName;
    }
}
