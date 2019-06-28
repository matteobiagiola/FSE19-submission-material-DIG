package custom_classes;

import po_utils.TestData;

public enum PetTypes implements TestData {

    BIRD ("bird"),
    CAT ("cat"),
    DOG ("dog"),
    HAMSTER ("hamster"),
    LIZARD ("lizard"),
    SNAKE ("snake");

    private final String petType;

    PetTypes(String petType){
        this.petType = petType;
    }

    public String value(){
        return this.petType;
    }
}
