package pages;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static pages.AmazonSearchPage.foundedProductsXpath;

public class AmazonProductPage extends BasePage {
    private final By ALERT_MESSAGE_SUCCESSFUL = By.id("attachDisplayAddBaseAlert");
    private final By ANOTHER_ALERT_MESSAGE = By.xpath("//div[@id='sw-atc-details-single-container']//div[@id='NATC_SMART_WAGON_CONF_MSG_SUCCESS']");
    private final By CART_BTN = By.id("nav-cart-count");
    private final By AMAZON_LOGO = By.id("nav-logo-sprites");
    private final By CLOSE_ALERT_BTN = By.id("attach-close_sideSheet-link");
    private final By ALL_PRODUCTS_IN_CART = By.xpath("//div[@class='sc-item-content-group']//span[@class='a-truncate-full a-offscreen']");
    private final By PRODUCT_TITLE = By.id("productTitle");
    private final By ADD_TO_CART_BTN = By.id("add-to-cart-button");
    private final By DELETE_PRODUCT_IN_CART = By.xpath("(//input[@value='Delete'])");
    private final By TICK_ICON_IN_ALERT = By.xpath("//div[@id='attachDisplayAddBaseAlert']//i[@class='a-icon a-icon-alert']");
    private final By TICK_ICON_ON_PAGE = By.xpath("//div[@class='a-box a-alert-inline a-alert-inline-success sw-atc-message']");
    private static String productAddedToCart;
    private String alertText;
    private static final Logger LOGGER = LogManager.getLogger(AmazonSearchPage.class.getName());

    public AmazonProductPage(WebDriver driver) {
        super(driver);
    }

    @Step("Search for the product that has add button")
    public String addProductThatHasAddBtn(List<WebElement> listOfFoundedProducts, String productName) {
        for (int i = 1; i <= listOfFoundedProducts.size(); i++) {
            driver.findElement(By.xpath(String.format(foundedProductsXpath.concat("[%s]"), productName, i))).click();
            try {
                if (driver.findElement(ADD_TO_CART_BTN).isDisplayed()) {
                    productAddedToCart = getProductThatWasAddedInCart();
                    addToCart();
                    break;
                }
            } catch (NoSuchElementException | TimeoutException exception) {
                LOGGER.warn("The product page doesn't contain a 'Add to Cart button'");
                driver.navigate().back();
            }
        }
        return productAddedToCart;
    }

    @Step("Getting the name of the product that was added to the Cart")
    private String getProductThatWasAddedInCart() {
        return driver.findElement(PRODUCT_TITLE).getText();
    }

    @Step("Add product to the Cart")
    public void addToCart() {
        driver.findElement(ADD_TO_CART_BTN).click();
    }

    @Step("Get an alert message after adding the product to the cart")
    private void setAlertMessage() {
        alertText = driver.findElement(ALERT_MESSAGE_SUCCESSFUL).getText();
    }

    @Step("Get a simple message after adding the product to the cart")
    private void setAnotherTypeOfMessage() {
        alertText = driver.findElement(ANOTHER_ALERT_MESSAGE).getText();
    }

    @Step("Waiting for a message about adding a product to the Cart")
    public String getTextFromMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ALERT_MESSAGE_SUCCESSFUL));
            setAlertMessage();
        } catch (NoSuchElementException | TimeoutException exception) {
            LOGGER.info("The message was of a different type");
            setAnotherTypeOfMessage();
        }
        return alertText;
    }

    @Step("Getting a number on the Cart")
    public String getNumberOnCart() {
        driver.navigate().refresh();
        String numberOnCart = driver.findElement(CART_BTN).getText();
        LOGGER.info(String.format("Number of products in cart: %s", numberOnCart));
        return numberOnCart;
    }

    @Step("Getting an area of the image with a tick")
    public boolean doesTickReportAboutSuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(TICK_ICON_IN_ALERT));
            LOGGER.info("The classes that the tick has are " + driver.findElement(TICK_ICON_IN_ALERT).getAttribute("class"));
            return driver.findElement(TICK_ICON_IN_ALERT).getAttribute("class").contains("success");
        } catch (NoSuchElementException | TimeoutException e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(TICK_ICON_ON_PAGE));
            LOGGER.info("The classes that the tick has are " + driver.findElement(TICK_ICON_ON_PAGE).getAttribute("class"));
            return driver.findElement(TICK_ICON_ON_PAGE).getAttribute("class").contains("success");
        }
    }

    @Step("Return to the main Page")
    private void returnToMainPage() {
        try {
            driver.findElement(CLOSE_ALERT_BTN).click();
        } catch (NoSuchElementException ignored) {
        }
        driver.navigate().back();
        driver.findElement(AMAZON_LOGO).click();
    }

    @Step("Checking whether the Cart contains the added product")
    public boolean doesCartContainSelectedProduct(String productAddedToCart) {
        returnToMainPage();
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


