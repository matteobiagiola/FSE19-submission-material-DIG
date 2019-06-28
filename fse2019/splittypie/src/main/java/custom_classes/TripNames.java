package custom_classes;

import po_utils.TestData;

public enum TripNames implements TestData {

    BARCELONA ("Trip To Barcelona"),
    ROME ("Trip to Rome"),
    AMUSEMENT_PARK ("Mirabilandia"),
    RESTAURANT ("Restaurant");

    private final String tripName;

    TripNames(String tripName){
        this.tripName = tripName;
    }

    public String value(){
        return this.tripName;
    }
}
