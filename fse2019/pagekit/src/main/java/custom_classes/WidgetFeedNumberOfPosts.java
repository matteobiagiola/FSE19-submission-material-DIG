package custom_classes;

import po_utils.Range;
import po_utils.TestData;

public class WidgetFeedNumberOfPosts extends Range implements TestData {

    private final int lower = 1;
    private final int upper = 10;
    public final int value;

    public WidgetFeedNumberOfPosts(int i){
        this.value = this.map(i, lower, upper);
        //this.value = i;
    }

    public String value(){
        return String.valueOf(this.value);
    }
}
