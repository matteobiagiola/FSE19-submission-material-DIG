package po_utils;

public class Range {

    public int map(int x, int lower, int upper){
        int temp = x % (upper - lower);
        if(temp < 0) temp += upper - lower;
        temp += lower;
        return temp;
    }
}
