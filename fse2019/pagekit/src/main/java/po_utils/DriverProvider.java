package po_utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class DriverProvider {

    private static String sessionId = "";

    public WebDriver getActiveDriver(){
        WebDriver driver = null;
        try {
            String chromedriverURL = new URL("http://localhost:"
                    + Integer.valueOf(MyProperties.getInstance().getProperty("chromedriverPort"))).toString();
            String appUrl = "http://localhost:" + Integer.valueOf(MyProperties.getInstance().getProperty("appPort"))
                    + "/pagekit/index.php/admin/login";
            boolean driverHeadless = Boolean.valueOf(MyProperties.getInstance().getProperty("driverHeadless"));
            if(sessionId.isEmpty()){
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                if(driverHeadless) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1200x600");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                }
                driver = new RemoteWebDriver(new URL(chromedriverURL), capabilities);
                sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
            } else {
                driver = this.createDriverFromSession(sessionId, chromedriverURL);
            }
            driver.get(appUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private RemoteWebDriver createDriverFromSession(String sessionId, String command_executor) throws MalformedURLException {
        CommandExecutor executor = new HttpCommandExecutor(new URL(command_executor)) {

            @Override
            public Response execute(Command command) throws IOException {
                Response response;
                if (command.getName().equals("newSession")) {
                    response = new Response();
                    response.setSessionId(sessionId);
                    response.setStatus(0);
                    response.setValue(Collections.<String, String>emptyMap());

                    try {
                        Field commandCodec;
                        commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
                        commandCodec.setAccessible(true);
                        commandCodec.set(this, new W3CHttpCommandCodec());

                        Field responseCodec;
                        responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
                        responseCodec.setAccessible(true);
                        responseCodec.set(this, new W3CHttpResponseCodec());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                } else {
                    response = super.execute(command);
                }
                return response;
            }
        };

        return new RemoteWebDriver(executor, new DesiredCapabilities());
    }
//
//    public static void resetSession() {
//        sessionId = "";
//    }


}

