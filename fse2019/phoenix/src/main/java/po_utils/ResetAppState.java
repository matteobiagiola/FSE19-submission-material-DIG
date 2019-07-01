package po_utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ResetAppState {

    public static void main(String[] args) {
        reset();
    }

    public static void reset(WebDriver driver){
        int dbPort = Integer.valueOf(MyProperties.getInstance().getProperty("dbPort"));
        clearDB("postgres","postgres","phoenix_trello_dev", dbPort);
        clearStorage(driver);
        driver.get("http://localhost:" + Integer.valueOf(MyProperties.getInstance().getProperty("appPort")));
    }

    // needed for code-coverage
    public static void quitDriver(WebDriver driver){
        driver.quit();
    }

    public static void reset(){
        int dbPort = Integer.valueOf(MyProperties.getInstance().getProperty("dbPort"));
        clearDB("postgres","postgres","phoenix_trello_dev", dbPort);
    }

    public static void resetClient(WebDriver driver){
        clearStorage(driver);
        String appUrl = "http://localhost:" + Integer.valueOf(MyProperties.getInstance().getProperty("appPort"));
        driver.get(appUrl);
    }

    private static void clearDB(String username, String password, String dbName, int port){
        MyPSqlConnection myPSqlConnection = new MyPSqlConnection();
        myPSqlConnection.reset(username, password, dbName, port,"false");
    }

    private static void clearStorage(WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format(
                "return window.localStorage.removeItem('%s');", "phoenixAuthToken"));
    }
}
