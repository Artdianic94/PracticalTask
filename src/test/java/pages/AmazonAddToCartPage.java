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
    WebDriverWait wait;
    private final By ALERT_MESSAGE_SUCCESSFUL = By.id("attachDisplayAddBaseAlert");
    private final By ANOTHER_ALERT_MESSAGE = By.xpath("//div[@id='sw-atc-details-single-container']//div[@id='NATC_SMART_WAGON_CONF_MSG_SUCCESS']");
    private final By CART_BTN = By.id("nav-cart-count");
    private final By AMAZON_LOGO = By.id("nav-logo-sprites");
    private final By TICK_ICON = By.xpath("//div[@id='attachDisplayAddBaseAlert']//i[@class='a-icon a-icon-alert']");
    private final By ANOTHER_TICK_ICON = By.xpath("//div[@id='sw-atc-confirmation']//i[@class='a-icon a-icon-alert']");
    private final By ALL_PRODUCTS_IN_CART = By.xpath("//div[@class='sc-item-content-group']//span[@class='a-truncate-full a-offscreen']");
    private final By PRODUCT_TITLE = By.id("productTitle");
    String deleteInputXpath = "(//input[@value='Delete'])";
    List<WebElement> deleteAllProductsInCart;
    StringBuilder deleteIndex = new StringBuilder();
    boolean isTickIconGreen = false;
    String iPhonesXpath = "(//div[@class='a-section']//span[contains(text(), 'iPhone')])";
    private final By ADD_TO_CART_BTN = By.id("add-to-cart-button");
    StringBuilder iphoneIndex = new StringBuilder();
    List<WebElement> allIphones;
    static String productAddedToCart;
    AmazonSearchPage amazonSearchPage;
    private static final Logger LOGGER = LogManager.getLogger(AmazonSearchPage.class.getName());

    public AmazonAddToCartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Search for the product and add it to the Cart")
    public void addProductToCart() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        amazonSearchPage = new AmazonSearchPage(driver);
        allIphones = amazonSearchPage.getListOfAllProducts();
        iphoneIndex.append(iPhonesXpath);
        for (int i = 1; i <= allIphones.size(); i++) {
            iphoneIndex.append("[").append(i).append("]");
            LOGGER.info("Search for a product that can be added to the Cart");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.valueOf(iphoneIndex))));
            driver.findElement(By.xpath(String.valueOf(iphoneIndex))).click();
            try {
                if (driver.findElement(ADD_TO_CART_BTN).isDisplayed()) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE));
                    productAddedToCart = driver.findElement(PRODUCT_TITLE).getText();
                    driver.findElement(ADD_TO_CART_BTN).click();
                    LOGGER.info(String.format("Searched product with a title %s was added in Cart", productAddedToCart));
                    break;
                }

            } catch (NoSuchElementException | TimeoutException exception) {
                LOGGER.warn("The product page doesn't contain a 'Add to Cart button'");
                driver.navigate().back();
                iphoneIndex.delete(iphoneIndex.length() - 3, iphoneIndex.length());
            }
        }
    }

    @Step("Waiting for a message about adding a product to the Cart")
    public String checkForAddingToCart() {
        String alertText;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            LOGGER.info("Wait for a message about adding a product to the cart");
            wait.until(ExpectedConditions.visibilityOfElementLocated(ALERT_MESSAGE_SUCCESSFUL));
            alertText = driver.findElement(ALERT_MESSAGE_SUCCESSFUL).getText();
            LOGGER.info(String.format("Message about adding a product to the cart: %s", alertText));
            if (driver.findElement(TICK_ICON).isDisplayed()) {
                if (alertText.contains("Added")) {
                    LOGGER.info("There is a green tick in the message.");
                    isTickIconGreen = true;
                }
            }
        } catch (NoSuchElementException | TimeoutException exception) {
            alertText = driver.findElement(ANOTHER_ALERT_MESSAGE).getText();
            LOGGER.info(String.format("Message about adding a product to the cart: %s", alertText));
            wait.until(ExpectedConditions.visibilityOfElementLocated(ANOTHER_TICK_ICON));
            if (driver.findElement(ANOTHER_TICK_ICON).isDisplayed()) {
                if (alertText.contains("Added")) {
                    LOGGER.info("There is a green tick in the message.");
                    isTickIconGreen = true;
                }
            }
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
        return isTickIconGreen;
    }

    @Step("Getting list of products that are in the Cart")
    public boolean whatInCart() {
        driver.navigate().back();
        driver.findElement(AMAZON_LOGO).click();
        driver.findElement(CART_BTN).click();
        List<WebElement> listOfProductsInCart = driver.findElements(ALL_PRODUCTS_IN_CART);
        boolean isContain = false;
        for (int i = 0; i < listOfProductsInCart.size(); i++) {
            if (productAddedToCart.contains(listOfProductsInCart.get(i).getText())) {
                isContain = true;
                LOGGER.info(String.format("Product with a title %s is in Cart", listOfProductsInCart.get(i).getText()));
                break;
            }
        }
        return isContain;
    }

    @Step("Clear the shopping Cart")
    public void cleanCart() {
        LOGGER.info("Delete all products in Cart");
        driver.findElement(CART_BTN).click();
        deleteAllProductsInCart = driver.findElements(By.xpath(deleteInputXpath));
        deleteIndex.append(deleteInputXpath);
        for (int i = 1; i <= deleteAllProductsInCart.size(); i++) {
            driver.navigate().refresh();
            deleteIndex.append("[").append(1).append("]");
            driver.findElement(By.xpath(String.valueOf(deleteIndex))).click();
            deleteIndex.delete(deleteIndex.length() - 3, deleteIndex.length());
        }
    }
}


