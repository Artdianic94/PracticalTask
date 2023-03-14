package factorydriver;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriverManager extends DriverManager {

    private static final String HUB_URL = "http://selenoid:4444/wd/hub";

    @Override
    public void setUpDriver(){
        String remoteBrowser = System.getProperty("REMOTE_BROWSER");
        if (remoteBrowser == null) {
            throw new IllegalArgumentException("Remote browser is not specified!");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (remoteBrowser.toLowerCase()) {
            case "chrome":
                capabilities.setBrowserName("chrome");
                capabilities.setCapability("version", "latest");
                capabilities.setCapability("selenoid:options", ImmutableMap.of(
                        "enableVNC", true,
                        "enableVideo", true
                ));
                try {
                    driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "firefox":
                capabilities.setBrowserName("firefox");
                capabilities.setCapability("version", "latest");
                capabilities.setCapability("selenoid:options", ImmutableMap.of(
                        "enableVNC", true,
                        "enableVideo", true
                ));
                try {
                    driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "opera":
                capabilities.setBrowserName("opera");
                capabilities.setCapability("version", "latest");
                capabilities.setCapability("operaOptions", ImmutableMap.of("binary", "/usr/bin/opera"));
                capabilities.setCapability("selenoid:options", ImmutableMap.of(
                        "enableVNC", true,
                        "enableVideo", true
                ));
                try {
                    driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid remote browser specified: " + remoteBrowser);
        }
    }
}
