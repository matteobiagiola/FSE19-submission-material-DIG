package custom_classes;

import po_utils.Range;
import po_utils.TestData;

public class Id extends Range implements TestData {

    private final int lower = 1;
    private final int upper = 2;
    public final int value;

    public Id(int i){
        this.value = this.map(i, lower, upper);
        //this.value = i;
    }
}
