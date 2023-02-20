package pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import staticdata.AmazonLoginCredentials;
import staticdata.WebUrls;


public class AmazonAuthorizationPage extends BasePage {
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

    public void openMainPage() {
        LOGGER.info(String.format("Open website: %s", WebUrls.AMAZON_URL));
        driver.get(WebUrls.AMAZON_URL);
    }

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

    public String getSignInResult() {
        LOGGER.info("Get user greeting inscription");
        return driver.findElement(USER_WELCOME_STRING).getText();
    }
}
