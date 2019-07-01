package code.utils;

import code.main.ApplicationName;

public class MyProperties {
    private static MyProperties ourInstance = new MyProperties();

    public static MyProperties getInstance() {
        return ourInstance;
    }

    public static String home_dir = System.getProperty("user.home");
    public static String separator = System.getProperty("file.separator");
    public static String javaHome = System.getProperty("java.home");

    private ApplicationName applicationName;
    private String classpath = System.getProperty("java.class.path");
    private int expressServerPort = 6969;

    private MyProperties() {
    }

    public void setApplicationName(ApplicationName applicationName) {
        this.applicationName = applicationName;
    }

    public ApplicationName getApplicationName() {
        return applicationName;
    }

    public int getExpressServerPort() {
        return expressServerPort;
    }

    public void setExpressServerPort(int expressServerPort) {
        this.expressServerPort = expressServerPort;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        System.setProperty("java.class.path", classpath);
        this.classpath = classpath;
    }
}
