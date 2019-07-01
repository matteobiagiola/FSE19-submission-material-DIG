package code.utils;

public class CheckCondition {

    private enum CheckType{
        STATE,
        ARGUMENT
    }

    public static <T> void checkNotNull(T object, String errorMessageIfNull){
        if(object == null) throw new NullPointerException(errorMessageIfNull);
    }

    public static void checkState(boolean condition, String errorMessageIfFalse){
        check(condition, errorMessageIfFalse, CheckType.STATE);
    }

    public static void checkArgument(boolean condition, String errorMessageIfFalse){
        check(condition, errorMessageIfFalse, CheckType.ARGUMENT);
    }

    private static void check(boolean condition, String errorMessageIfFalse, CheckType type){
        if(!condition){
            if(type.equals(CheckType.STATE)){
                throw new IllegalStateException(errorMessageIfFalse);
            }else if(type.equals(CheckType.ARGUMENT)){
                throw new IllegalArgumentException(errorMessageIfFalse);
            }
        }
    }
}
