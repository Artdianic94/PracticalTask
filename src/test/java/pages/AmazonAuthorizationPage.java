package pages;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import utilities.CaptchaException;

public class AmazonAuthorizationPage extends BasePage {
    private final By YOUR_ACCOUNT = By.xpath("//a[contains(.,'Your Account')]");
    private final By SIGN_IN_ACCOUNT_LIST = By.id("nav-link-accountList");
    private final By SIGN_IN_BTN = By.id("nav-link-accountList");
    private final By EMAIL_INPUT = By.id("ap_email");
    private final By CONTINUE_BTN = By.id("continue");
    private final By PASSWORD_INPUT = By.id("ap_password");
    private final By SIGN_IN_SUBMIT_BTN = By.id("signInSubmit");
    private final By USER_WELCOME_STRING = By.id("nav-link-accountList-nav-line-1");
    private final By TEXT_ON_PAGE = By.xpath("//h4[@class='a-alert-heading']");
    private static final Logger LOGGER = LogManager.getLogger(AmazonAuthorizationPage.class.getName());

    public AmazonAuthorizationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Send login and password to the login form")
    public void makeLogin(String email, String password) {
        try {
            driver.findElement(YOUR_ACCOUNT).click();
            LOGGER.warn("Another type of Amazon home page has been opened.");
        } catch (NoSuchElementException ignored) {
        }
        moveOverAccountAndSignIn();
        driver.findElement(EMAIL_INPUT).sendKeys(email);
        driver.findElement(CONTINUE_BTN).click();
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(SIGN_IN_SUBMIT_BTN).click();
    }

    @Step("Move the cursor over 'Account & Lists' and click 'Sign In'")
    public void moveOverAccountAndSignIn() {
        Actions actions = new Actions(driver).moveToElement(driver.findElement(SIGN_IN_ACCOUNT_LIST));
        actions.perform();
        LOGGER.info("Click 'Sign In' in the tab that opens.");
        driver.findElement(SIGN_IN_BTN).click();
    }

    @Step("Getting user greeting inscription")
    public String getSignInResult() throws CaptchaException {
        try {
            return driver.findElement(USER_WELCOME_STRING).getText();
        } catch (NoSuchElementException ex) {
            throw new CaptchaException("Text on the page:" + driver.findElement(TEXT_ON_PAGE).getText());
        }
    }
}
