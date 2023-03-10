package factorydriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteDriverManager extends DriverManager {

    private void setSelenoidOptions(DesiredCapabilities capabilities) {
        capabilities.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("enableVideo", true);
        }});
    }

    public void selectBrowser(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                setSelenoidOptions(capabilities);
                chromeOptions.merge(capabilities);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                setSelenoidOptions(capabilities);
                firefoxOptions.merge(capabilities);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "opera":
                ChromeOptions operaOptions = new ChromeOptions();
                operaOptions.setBinary("/usr/bin/opera");
                setSelenoidOptions(capabilities);
                operaOptions.merge(capabilities);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), operaOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
    }

    @Override
    public void setUpDriver() {
        selectBrowser(System.getProperty("REMOTE_BROWSER"));
    }
}