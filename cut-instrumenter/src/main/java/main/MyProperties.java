package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class MyProperties {
    private static MyProperties ourInstance = new MyProperties();

    public static String home_dir = System.getProperty("user.home");
    public static String separator = System.getProperty("file.separator");
    public static String javaHome = System.getProperty("java.home");
    private Properties appProps;

    public static MyProperties getInstance() {
        return ourInstance;
    }

    private MyProperties() {
        Path currentRelativePath = Paths.get("");
        String currentDirectoryPath = currentRelativePath.toAbsolutePath().toString();
        String appPropertiesPath = currentDirectoryPath + "/src/main/resources/app.properties";
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
}
