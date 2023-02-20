package tests;

import driver.ChromeDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import pages.AmazonAuthorizationPage;


public class BaseTest {
    WebDriver driver;
    ChromeDriverManager chromeDriverManager;
    AmazonAuthorizationPage amazonAuthorizationPage;

    @BeforeEach
    public void setUp(){
        chromeDriverManager = new ChromeDriverManager();
        driver = chromeDriverManager.getDriver();
        chromeDriverManager.maximize();
        amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        amazonAuthorizationPage.openMainPage();
        amazonAuthorizationPage.makeLogin();
    }

    @AfterEach
    public void quit() {
        chromeDriverManager.quitDriver();
    }
}
