package po_utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ResetAppState {

    public static void reset(WebDriver driver){
        driver.quit();
    }

}
