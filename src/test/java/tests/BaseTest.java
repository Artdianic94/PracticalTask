package tests;

import driver.ChromeDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pages.AmazonAuthorizationPage;
import utilities.ScreenshotWatcher;

public class BaseTest {
    public WebDriver driver;
    public static ChromeDriverManager chromeDriverManager;
    AmazonAuthorizationPage amazonAuthorizationPage;

    @RegisterExtension
    public TestWatcher watchman = new TestWatcher() {

        @Override
        public void testFailed(ExtensionContext context, Throwable throwable) {
            screenshot();
        }

        @Attachment(value = "Page screenshot", type = "image/png")
        public byte[] saveScreenshot(byte[] screenShot) {
            return screenShot;
        }

        public void screenshot() {
            if (driver == null) {
                return;
            }
            saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        }
    };

    @BeforeEach
    @Step("Start the application")
    public void setUp() {
        chromeDriverManager = new ChromeDriverManager();
        driver = chromeDriverManager.getDriver();
        chromeDriverManager.maximize();
        amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        amazonAuthorizationPage.openMainPage();
        amazonAuthorizationPage.makeLogin();
        ScreenshotWatcher.setDriver(driver);
    }


    @AfterAll()
    static void quit() {
        chromeDriverManager.quitDriver();
    }
}
