package pages;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AmazonAddToCartPage extends BasePage {
    private final By ALERT_MESSAGE_SUCCESSFUL = By.id("attachDisplayAddBaseAlert");
    private final By ANOTHER_ALERT_MESSAGE = By.xpath("//div[@id='sw-atc-details-single-container']//div[@id='NATC_SMART_WAGON_CONF_MSG_SUCCESS']");
    private final By CART_BTN = By.id("nav-cart-count");
    private final By AMAZON_LOGO = By.id("nav-logo-sprites");
    private final By CLOSE_ALERT_BTN = By.id("attach-close_sideSheet-link");
    private final By TICK_ICON = By.xpath("//div[@id='attachDisplayAddBaseAlert']//i[@class='a-icon a-icon-alert']");
    private final By ANOTHER_TICK_ICON = By.xpath("//div[@id='sw-atc-confirmation']//i[@class='a-icon a-icon-alert']");
    private final By ALL_PRODUCTS_IN_CART = By.xpath("//div[@class='sc-item-content-group']//span[@class='a-truncate-full a-offscreen']");
    private final By PRODUCT_TITLE = By.id("productTitle");
    private final By ADD_TO_CART_BTN = By.id("add-to-cart-button");
    private final By IPHONE_IN_LIST = By.xpath("(//div[@class='a-section']//span[contains(text(), 'iPhone')])");
    private final By DELETE_PRODUCT_IN_CART = By.xpath("(//input[@value='Delete'])");
    private static String productAddedToCart;
    private String alertText;
    private static final Logger LOGGER = LogManager.getLogger(AmazonSearchPage.class.getName());

    public AmazonAddToCartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Search for the product that has add button")
    public void addProductThatHasAddBtn(List<WebElement> allIphones) {
        for (int i = 0; i < allIphones.size(); i++) {
            driver.findElements(IPHONE_IN_LIST).get(i).click();
            try {
                if (driver.findElement(ADD_TO_CART_BTN).isDisplayed()) {
                    addToCart();
                    break;
                }
            } catch (NoSuchElementException | TimeoutException exception) {
                LOGGER.warn("The product page doesn't contain a 'Add to Cart button'");
                driver.navigate().back();
            }
        }
    }

    @Step("Add product to the Cart")
    public void addToCart() {
        productAddedToCart = driver.findElement(PRODUCT_TITLE).getText();
        driver.findElement(ADD_TO_CART_BTN).click();
        LOGGER.info(String.format("Searched product with a title %s was added in Cart", productAddedToCart));

    }

    @Step("Get an alert message after adding the product to the cart")
    public void getAlertMessage() {
        alertText = driver.findElement(ALERT_MESSAGE_SUCCESSFUL).getText();
        if (driver.findElement(TICK_ICON).isDisplayed()) {
            isTickIconGreen();
        }
    }

    @Step("Get a simple message after adding the product to the cart")
    public void getAnotherTypeOfMessage() {
        alertText = driver.findElement(ANOTHER_ALERT_MESSAGE).getText();
        if (driver.findElement(ANOTHER_TICK_ICON).isDisplayed()) {
            isTickIconGreen();
        }
    }

    @Step("Waiting for a message about adding a product to the Cart")
    public String getTextFromMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ALERT_MESSAGE_SUCCESSFUL));
            getAlertMessage();
        } catch (NoSuchElementException | TimeoutException exception) {
            LOGGER.info("The message was of a different type");
            getAnotherTypeOfMessage();
        }
        return alertText;
    }

    @Step("Getting a number on the Cart")
    public String numberOnCart() {
        driver.navigate().refresh();
        String numberOnCart = driver.findElement(CART_BTN).getText();
        LOGGER.info(String.format("Number of products in cart: %s", numberOnCart));
        return numberOnCart;
    }

    @Step("Getting a colour of the tick when product was added to the Cart ")
    public boolean isTickIconGreen() {
        boolean isTickIconGreen;
        if (alertText.contains("Added")) {
            isTickIconGreen = true;
        } else {
            isTickIconGreen = false;
            LOGGER.info("Product wasn't added in Cart");
        }
        return isTickIconGreen;
    }

    @Step("Getting list of products that are in the Cart")
    public boolean doesCartContainSelectedProduct() {
        try {
            driver.findElement(CLOSE_ALERT_BTN).click();
        } catch (NoSuchElementException ignored) {
        }
        driver.navigate().back();
        driver.findElement(AMAZON_LOGO).click();
        driver.findElement(CART_BTN).click();
        List<WebElement> listOfProductsInCart = driver.findElements(ALL_PRODUCTS_IN_CART);
        boolean isContain = false;
        for (WebElement webElement : listOfProductsInCart) {
            if (productAddedToCart.contains(webElement.getText())) {
                isContain = true;
                LOGGER.info(String.format("Product with a title %s is in Cart", webElement.getText()));
                break;
            }
        }
        return isContain;
    }

    @Step("Clear the shopping Cart")
    public void cleanCart() {
        LOGGER.info("Delete all products in Cart");
        driver.findElement(CART_BTN).click();
        try {
            do {
                driver.navigate().refresh();
                driver.findElement(DELETE_PRODUCT_IN_CART).click();
            }
            while (driver.findElement(DELETE_PRODUCT_IN_CART).isDisplayed());
        } catch (NoSuchElementException ignored) {
        }
    }
}


