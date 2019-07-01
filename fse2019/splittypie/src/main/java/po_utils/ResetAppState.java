package po_utils;

import org.openqa.selenium.WebDriver;

public class ResetAppState {

    public static void reset(WebDriver driver){
        driver.get("http://localhost:" + Integer.valueOf(MyProperties.getInstance().getProperty("appPort")));
        driver.quit();
    }

    // needed for code-coverage
    public static void quitDriver(WebDriver driver){
        driver.quit();
    }

}
