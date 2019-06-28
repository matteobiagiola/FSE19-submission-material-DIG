package po_utils;

import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

public class ResetAppState {

    public static void main(String[] args) {
        reset();
    }

    public static void reset(){
        int port = Integer.valueOf(MyProperties.getInstance().getProperty("dbPort"));
        resetDB("root","root","pagekit", port,"src/main/resources/pagekit_db.sql");
    }

    public static void resetClient(WebDriver driver) {
        clearCookies(driver);
    }

    private static void resetDB(String username, String password, String dbName, int port, String aSQLScriptFilePath){
        MySqlConnection mySqlConnection = new MySqlConnection();
        mySqlConnection.resetUsingSqlScript(username,password,dbName,port,aSQLScriptFilePath);
    }

    private static void clearCookies(WebDriver driver){
        if(driver.manage().getCookieNamed("pagekit_auth") != null){
            driver.manage().deleteCookieNamed("pagekit_auth");
        }
        if(driver.manage().getCookieNamed("pagekit_session") != null){
            driver.manage().deleteCookieNamed("pagekit_session");
        }
    }


}
