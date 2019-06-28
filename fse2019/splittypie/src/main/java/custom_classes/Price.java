package custom_classes;

import po_utils.Range;
import po_utils.TestData;

public class Price extends Range implements TestData {

    private final int lower = 1;
    private final int upper = 100;
    public final int value;

    public Price(int i){
        this.value = this.map(i, lower, upper);
        //this.value = i;
    }
}
