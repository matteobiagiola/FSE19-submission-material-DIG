package po_utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ExceptionUtils {

    public static String getStackTrace(Exception ex){
        String message = ex.getMessage();
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        String result = Arrays.stream(stackTraceElements).map(stackTraceElement -> {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            int lineNumber = stackTraceElement.getLineNumber();
            return lineNumber + "-" + className + "." + methodName + "()";
        }).collect(Collectors.joining("\n"));
        return "Message: " + message + "\n Stacktrace: " + result;
    }
}
