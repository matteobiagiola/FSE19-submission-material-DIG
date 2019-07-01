package po_utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    private static MyProperties ourInstance = new MyProperties();

    public static String home_dir = System.getProperty("user.home");
    public static String separator = System.getProperty("file.separator");
    public static String javaHome = System.getProperty("java.home");
    private String pathToESTestSuite;
    private Properties appProps;
    private String projectPath;

    public static MyProperties getInstance() {
        return ourInstance;
    }

    private MyProperties() {
    }

    public void loadProperties(String projectPath){
        String appPropertiesPath = projectPath + "/src/main/resources/app.properties";
        try {
            FileInputStream fileInputStream = new FileInputStream(appPropertiesPath);
            this.appProps = new Properties();
            this.appProps.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String propertyName){
        String value = this.appProps.getProperty(propertyName);
        if(value == null) throw new IllegalStateException("getProperty: property with name " + propertyName + " does not exist.");
        else return value;
    }

    public String getProperty(String propertyName, String defaultValue){
        return this.appProps.getProperty(propertyName, defaultValue);
    }

    public String getPathToESTestSuite() {
        return pathToESTestSuite;
    }

    public void setPathToESTestSuite(String pathToESTestSuite) {
        this.pathToESTestSuite = pathToESTestSuite;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
