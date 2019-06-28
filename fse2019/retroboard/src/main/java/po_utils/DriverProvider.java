package po_utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverProvider {

    public WebDriver getActiveDriver(){
        WebDriver driver = null;
        try {
            String chromedriverURL = new URL("http://localhost:"
                    + Integer.valueOf(MyProperties.getInstance().getProperty("chromedriverPort"))).toString();
            String appUrl = "http://localhost:" + Integer.valueOf(MyProperties.getInstance().getProperty("appPort"));
            boolean driverHeadless = Boolean.valueOf(MyProperties.getInstance().getProperty("driverHeadless"));
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            if(driverHeadless) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1200x600");
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            }
            driver = new RemoteWebDriver(new URL(chromedriverURL), capabilities);
            driver.get(appUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
