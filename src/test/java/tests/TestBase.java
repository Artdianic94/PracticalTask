package tests;

import driver.ChromeDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import pages.AmazonAuthorizationPage;
import utilities.AfterEachExtension;

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


    @AfterEach()
    @Step("Stop the application")
    public void quit() {
        afterEachExtension.setDriver(driver);
    }
}
