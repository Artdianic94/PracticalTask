package factorydriver;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriverManager extends DriverManager {

    private static final String HUB_URL = "http://selenoid:4444/wd/hub";

    @Override
    public void setUpDriver() {
        selectBrowser(System.getProperty("REMOTE_BROWSER"));
    }

    public void selectBrowser(String remoteBrowser) {
        if (remoteBrowser == null) {
            throw new IllegalArgumentException("Remote browser is not specified!");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (remoteBrowser.toLowerCase()) {
            case "chrome":
                setChromeCapabilities(capabilities);
                break;
            case "firefox":
                setFirefoxCapabilities(capabilities);
                break;
            default:
                throw new IllegalArgumentException("Invalid remote browser specified: " + remoteBrowser);
        }

        try {
            driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setCommonCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("selenoid:options", ImmutableMap.of(
                "sessionTimeout", "15m",
                "enableVNC", false,
                "enableVideo", false
        ));
    }

    private void setChromeCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName("chrome");
        capabilities.setCapability("version", "latest");
        setCommonCapabilities(capabilities);
    }

    private void setFirefoxCapabilities(DesiredCapabilities capabilities) {
        capabilities.setBrowserName("firefox");
        capabilities.setCapability("version", "110.0");
        setCommonCapabilities(capabilities);
    }
}