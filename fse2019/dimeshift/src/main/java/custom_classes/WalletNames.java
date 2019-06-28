package custom_classes;

import po_utils.TestData;

public enum WalletNames implements TestData {

    PERSONAL ("Personal"),
    COMPANY ("Company"),
    PRIVATE ("Private");

    private final String walletName;

    WalletNames(String walletName){
        this.walletName = walletName;
    }

    public String value(){
        return this.walletName;
    }
}
