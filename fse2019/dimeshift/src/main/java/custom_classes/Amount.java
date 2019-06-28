package custom_classes;

import po_utils.IntRange;
import po_utils.Range;
import po_utils.TestData;

public class Amount extends Range implements IntRange, TestData {

    private final int lower = 1;
    private final int upper = 1000;
    public final int value;

    public Amount(int i){
        this.value = this.map(i, lower, upper);
    }
}
