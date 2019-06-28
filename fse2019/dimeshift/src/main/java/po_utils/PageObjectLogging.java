package po_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PageObjectLogging {

    private final static Logger logger = Logger.getLogger(PageObjectLogging.class.getName());

//    private Writer writer;

//    public static PageObjectLogging instance = new PageObjectLogging();

//    private PageObjectLogging(){
//        try {
//            this.writer = new PrintWriter(new File(MyProperties.home_dir + "/Desktop/targets.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    public static PageObjectLogging getInstance(){
//        return instance;
//    }

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

//    public void writeCoveredGoal(String targetId){
//        try {
//            this.writer.append(targetId).append("\n");
//            this.writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
