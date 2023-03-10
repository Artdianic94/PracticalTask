package pages;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AmazonSearchPage extends BasePage {
    WebDriverWait wait;
    private static final Logger LOGGER = LogManager.getLogger(AmazonSearchPage.class.getName());
    private final By SEARCH_INPUT = By.id("twotabsearchtextbox");
    private final By SEARCH_BTN = By.id("nav-search-submit-button");
    private List<WebElement> listOfFoundedProducts;
    public static String foundedProductsXpath = "(//div[@class='a-section']//span[contains(text(), '%s')])";

    public AmazonSearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Send {productName} to searching field")
    public void sendSearchingText(String productName) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        driver.findElement(SEARCH_INPUT).sendKeys(productName);
        driver.findElement(SEARCH_BTN).click();
    }

    @Step("Getting list of products that contains {productName}")
    public List<WebElement> getListOfSearchProduct(String productName) {
        LOGGER.info(String.format("Get a list of all products found on request %s", productName));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LOGGER.info("Wait for a message about adding a product to the cart");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(foundedProductsXpath, productName))));
        listOfFoundedProducts = driver.findElements(By.xpath(String.format(foundedProductsXpath, productName)));
        return listOfFoundedProducts;
    }

    @Step("Checking if all results contain {productName}")
    public boolean isSearchedProductInEachItemOnPage(String productName) {
        int count = 0;
        listOfFoundedProducts = getListOfSearchProduct(productName);
        for (WebElement allIphone : listOfFoundedProducts) {
            if (allIphone.getText().contains(productName)) {
                count++;
            }
        }
        LOGGER.warn(String.format("Number of products in the list: %s.", listOfFoundedProducts.size()) + "Number of " + productName + "in this list: " + count);
        return count == listOfFoundedProducts.size();
    }
}
