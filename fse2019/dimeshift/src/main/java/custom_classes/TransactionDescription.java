package custom_classes;

import po_utils.TestData;

public enum TransactionDescription implements TestData {

    AMAZON ("amazon"),
    EBAY ("ebay");

    private final String transactionDescription;

    TransactionDescription(String transactionDescription){
        this.transactionDescription = transactionDescription;
    }

    public String value() {
        return this.transactionDescription;
    }
}
