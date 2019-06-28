package po_utils;

public class CheckCondition {

    public static void checkState(boolean condition, String message){
        if(!condition) throw new IllegalStateException(message);
    }

    public static void checkArgument(boolean condition, String message){
        if(!condition) throw new IllegalArgumentException(message);
    }
}
