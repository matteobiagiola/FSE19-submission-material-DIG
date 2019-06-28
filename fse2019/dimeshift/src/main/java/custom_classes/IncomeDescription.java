package custom_classes;

import po_utils.TestData;

public enum IncomeDescription implements TestData {

    SCHOLARSHIP ("scholarship"),
    SALARY ("salary");

    private final String incomeDescription;

    IncomeDescription(String incomeDescription){
        this.incomeDescription = incomeDescription;
    }

    public String value() {
        return this.incomeDescription;
    }
}
