package custom_classes;

import po_utils.TestData;

public enum LongTelephones implements TestData {

    LONG_PHONE("60855510230093423423948029384"),
    PHONE1 ("6085551023"),
    PHONE2 ("6085551749"),
    PHONE3 ("6085558763");

    private final String telephone;

    LongTelephones(String telephone){
        this.telephone = telephone;
    }

    public String value(){
        return this.telephone;
    }
}
