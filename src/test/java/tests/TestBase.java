package tests;

import com.google.common.collect.ImmutableMap;
import driver.ChromeDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import pages.AmazonAddToCartPage;
import pages.AmazonAuthorizationPage;
import staticdata.WebUrls;
import utilities.AfterEachExtension;
import utilities.PropertiesManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class TestBase {
    public WebDriver driver;
    public static ChromeDriverManager chromeDriverManager;
    @RegisterExtension
    AfterEachExtension afterEachExtension = new AfterEachExtension();

    @BeforeEach
    @Step("Start the application")
    public void setUp() {
        PropertiesManager propertiesManager = new PropertiesManager();
        chromeDriverManager = new ChromeDriverManager();
        driver = chromeDriverManager.getDriver();
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
            AmazonAddToCartPage amazonAddToCartPage = new AmazonAddToCartPage(driver);
            amazonAddToCartPage.cleanCart();
        } catch (NoSuchElementException ignored) {
        }
        afterEachExtension.setDriver(driver);
    }
}
