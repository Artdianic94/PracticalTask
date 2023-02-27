package pages;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import staticdata.AmazonLoginCredentials;
import staticdata.WebUrls;

import java.time.Duration;


public class AmazonAuthorizationPage extends BasePage {
    WebDriverWait wait;
    private final By SIGN_IN_ACCOUNT_LIST = By.id("nav-link-accountList");
    private final By SIGN_IN_BTN = By.id("nav-flyout-ya-signin");
    private final By EMAIL_INPUT = By.id("ap_email");
    private final By CONTINUE_BTN = By.id("continue");
    private final By PASSWORD_INPUT = By.id("ap_password");
    private final By SIGN_IN_SUBMIT_BTN = By.id("signInSubmit");
    private final By USER_WELCOME_STRING = By.id("nav-link-accountList-nav-line-1");
    private static final Logger LOGGER = LogManager.getLogger(AmazonAuthorizationPage.class.getName());

    public AmazonAuthorizationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open main page " + WebUrls.AMAZON_URL)
    public void openMainPage() {
        LOGGER.info(String.format("Open website: %s", WebUrls.AMAZON_URL));
        driver.get(WebUrls.AMAZON_URL);
    }

    @Step("Send login and password to the login form")
    public void makeLogin() {
        Actions actions = new Actions(driver);
        LOGGER.info("Move the cursor over 'Account & Lists' and wait for the tab to open.");
        actions.moveToElement(driver.findElement(SIGN_IN_ACCOUNT_LIST));
        actions.perform();
        LOGGER.info("Click 'Sign In' in the tab that opens.");
        driver.findElement(SIGN_IN_BTN).click();
        LOGGER.error("Enter login and password.There may be additional confirmation requests.");
        driver.findElement(EMAIL_INPUT).sendKeys(AmazonLoginCredentials.email);
        driver.findElement(CONTINUE_BTN).click();
        driver.findElement(PASSWORD_INPUT).sendKeys(AmazonLoginCredentials.password);
        driver.findElement(SIGN_IN_SUBMIT_BTN).click();
    }

    @Step("Getting user greeting inscription")
    public String getSignInResult() {
        try {
            LOGGER.info("Get user greeting inscription");
//            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(USER_WELCOME_STRING));
            return driver.findElement(USER_WELCOME_STRING).getText();
        }catch (NoSuchElementException e){
            LOGGER.error("You have to enter the characters manually");
            return "Greeting inscription not found";
        }
    }
}
