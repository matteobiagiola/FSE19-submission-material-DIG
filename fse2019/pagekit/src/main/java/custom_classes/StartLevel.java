package custom_classes;

import po_utils.Range;
import po_utils.TestData;

public class StartLevel extends Range implements TestData {

    private final int lower = 1;
    private final int upper = 11;
    public final int value;

    public StartLevel(int i){
        this.value = this.map(i, lower, upper);
        //this.value = i;
    }
}
