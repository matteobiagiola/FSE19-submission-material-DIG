package po_utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageObjectLogging {

    private final static Logger logger = Logger.getLogger(PageObjectLogging.class.getName());

    public static void logError(String message){
        logger.log(Level.SEVERE, message + "\n\n");
    }

    public static void logInfo(String message){
        logger.log(Level.INFO, message + "\n\n");
    }

    public static void logInfoPreconditionFailure(String methodName, List<String> parameterNames, List<Object> parameters, List<String> conditionDescriptions, List<Boolean> conditionValues){
        StringBuilder builder = new StringBuilder();
        builder.append("Method: " + methodName + ". Parameters [");
        for (int i = 0; i < parameterNames.size(); i++) {
            String parameterName = parameterNames.get(i);
            String parameter = parameters.get(i).toString();
            builder.append(parameterName + "-" + parameter + " ");
        }
        builder.append("]. Conditions: [");
        for (int i = 0; i < conditionDescriptions.size(); i++) {
            String conditionDescription = conditionDescriptions.get(i);
            Boolean conditionValue = conditionValues.get(i);
            builder.append(conditionDescription + "-" + conditionValue + " ");
        }
        builder.append("]");
        logInfo(builder.toString());
    }
}
