package tests;

import com.google.common.collect.ImmutableMap;
import factorydriver.DriverFactory;
import factorydriver.DriverManager;
import factorydriver.DriverType;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import pages.AmazonProductPage;
import pages.AmazonAuthorizationPage;
import staticdata.WebUrls;
import utilities.AfterEachExtension;
import utilities.PropertiesManager;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class TestBase {
    WebDriver driver;
    DriverManager driverManager;
    @RegisterExtension
    AfterEachExtension afterEachExtension = new AfterEachExtension();

    public void setUp(String browser) {
        DriverFactory factory = new DriverFactory();
        DriverType driverType = switch (browser) {
            case "chrome" -> DriverType.CHROME;
            case "firefox" -> DriverType.FIREFOX;
            case "remote" -> DriverType.REMOTE;
            default -> null;
        };
        driverManager = factory.getManager(driverType);
        driverManager.setUpDriver();
        driver = driverManager.getDriver();
    }

    public void startBrowser() {
        setUp("firefox");
    }

    @BeforeEach
    @Step("Start the application")
    public void beforeTestActions() {
        startBrowser();
        PropertiesManager propertiesManager = new PropertiesManager();
        AmazonAuthorizationPage amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        amazonAuthorizationPage.openPage(WebUrls.AMAZON_URL);
        String email = propertiesManager.get("Username");
        String password = propertiesManager.get("Password");
        if (email == null & password == null) {
            email = System.getenv("Username");
            password = System.getenv("Password");
        }
        amazonAuthorizationPage.makeLogin(email, password);
    }

    @BeforeEach
    void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", "Chrome")
                        .put("Browser.Version", "110.0.5481.100")
                        .put("URL", WebUrls.AMAZON_URL)
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }

    @AfterEach()
    @Step("Stop the application")
    public void quit() {
        try {
            AmazonProductPage amazonProductPage = new AmazonProductPage(driver);
            amazonProductPage.cleanCart();
        } catch (NoSuchElementException ignored) {
        }
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        afterEachExtension.setDriver(driver);
    }
}
