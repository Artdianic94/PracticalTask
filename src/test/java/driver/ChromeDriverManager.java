package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import staticdata.WebTimeouts;

import java.net.MalformedURLException;
import java.net.URL;


public class ChromeDriverManager {

    private WebDriver driver;

    public ChromeDriverManager() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-notifications");
        driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setTimeout() {
        driver.manage().timeouts().scriptTimeout(WebTimeouts.SCRIPT_TIMEOUT);
        driver.manage().timeouts().pageLoadTimeout(WebTimeouts.PAGE_LOAD_TIMEOUT);
        driver.manage().timeouts().implicitlyWait(WebTimeouts.IMPLICIT_TIMEOUT);
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void quitDriver() {
        driver.quit();
    }
}
