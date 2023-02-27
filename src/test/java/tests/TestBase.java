package tests;

import com.google.common.collect.ImmutableMap;
import driver.ChromeDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import pages.AmazonAuthorizationPage;
import staticdata.WebUrls;
import utilities.AfterEachExtension;


import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class TestBase {
    public WebDriver driver;
    public static ChromeDriverManager chromeDriverManager;
    AmazonAuthorizationPage amazonAuthorizationPage;

    @RegisterExtension
    AfterEachExtension afterEachExtension = new AfterEachExtension();

    @BeforeEach
    @Step("Start the application")
    public void setUp() {
        chromeDriverManager = new ChromeDriverManager();
        driver = chromeDriverManager.getDriver();
        chromeDriverManager.maximize();
        amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        amazonAuthorizationPage.openMainPage();
        amazonAuthorizationPage.makeLogin();
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
        afterEachExtension.setDriver(driver);
    }
}
