package custom_classes;

import po_utils.TestData;

public enum Telephones implements TestData {

    PHONE1 ("6085551023"),
    PHONE2 ("6085551749"),
    PHONE3 ("6085558763"),
    PHONE4 ("6085553198");

    private final String telephone;

    Telephones(String telephone){
        this.telephone = telephone;
    }

    public String value(){
        return this.telephone;
    }
}
