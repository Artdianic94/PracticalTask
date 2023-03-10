package factorydriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteDriverManager extends DriverManager {

    private static final String HUB_URL = "http://localhost:4444//wd/hub";
    private static final String SELENOID_TIMEZONE = "TZ=UTC";
    private static final boolean SELENOID_ENABLE_VIDEO = true;

    private static void setSelenoidOptions(DesiredCapabilities capabilities) {
        HashMap<String, Object> selenoidOptions = new HashMap<String, Object>();
        selenoidOptions.put("env", new ArrayList<String>() {{
            add(SELENOID_TIMEZONE);
        }});
        selenoidOptions.put("enableVideo", SELENOID_ENABLE_VIDEO);
        capabilities.setCapability("selenoid:options", selenoidOptions);
    }

    private static ChromeOptions getChromeOptions(DesiredCapabilities capabilities) {
        ChromeOptions options = new ChromeOptions();
        setSelenoidOptions(capabilities);
        options.merge(capabilities);
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(DesiredCapabilities capabilities) {
        FirefoxOptions options = new FirefoxOptions();
        setSelenoidOptions(capabilities);
        options.merge(capabilities);
        return options;
    }

    private static RemoteWebDriver createRemoteWebDriver(String browser, DesiredCapabilities capabilities) throws MalformedURLException {
        RemoteWebDriver driver = null;
        switch (browser) {
            case "chrome":
                driver = new RemoteWebDriver(new URL(HUB_URL), getChromeOptions(capabilities));
                break;
            case "firefox":
                driver = new RemoteWebDriver(new URL(HUB_URL), getFirefoxOptions(capabilities));
                break;
            case "opera":
                ChromeOptions operaOptions = new ChromeOptions();
                operaOptions.setBinary("/usr/bin/opera");
                setSelenoidOptions(capabilities);
                operaOptions.merge(capabilities);
                driver = new RemoteWebDriver(new URL(HUB_URL), operaOptions);
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        return driver;
    }

    public void selectBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", Platform.LINUX);
        capabilities.setCapability("version", "latest");
        driver = createRemoteWebDriver(browser, capabilities);
    }

    @Override
    public void setUpDriver() {
        String remoteBrowser = System.getProperty("REMOTE_BROWSER");
        if (remoteBrowser == null || remoteBrowser.isEmpty()) {
            throw new RuntimeException("Remote browser not specified");
        }
        try {
            selectBrowser(remoteBrowser);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
