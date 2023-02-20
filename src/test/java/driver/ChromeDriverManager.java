package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class ChromeDriverManager {

    private WebDriver driver;

    public ChromeDriverManager() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void quitDriver() {
        driver.quit();
    }
}
