package custom_classes;

import po_utils.TestData;

public enum Currencies implements TestData {

    USD ("United States dollar (USD)"),
    EUR ("Euro (EUR)"),
    GBP ("Pound sterling (GBP)"),
    PLN ("Polish złoty (PLN)"),
    CHF ("Swiss franc (CHF)"),
    CZK ("Czech koruna (CZK)"),
    HRK ("Croatian kuna (HRK)"),
    RON ("Romanian leu (RON)"),
    BGN ("Bulgarian lev (BGN)"),
    RUB ("Russian ruble (RUB)");


    private final String currency;

    Currencies(String currency){
        this.currency = currency;
    }

    public String value(){
        return this.currency;
    }
}
