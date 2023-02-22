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
    private static final Logger LOGGER = LogManager.getLogger(AmazonSearchPage.class.getName());
    private final By SEARCH_INPUT = By.id("twotabsearchtextbox");
    private final By SEARCH_BTN = By.id("nav-search-submit-button");
    String iPhonesXpath = "(//div[@class='a-section']//span[contains(text(), 'iPhone')])";
    List<WebElement> allIphones;
    String productName = "iPhone";

    public AmazonSearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Send '{productName}' to searching field")
    public void sendSearchingText() {

        LOGGER.info(String.format("Sending %s to searching field", productName));
        driver.findElement(SEARCH_INPUT).sendKeys(productName);
        driver.findElement(SEARCH_BTN).click();
    }
    @Step("Getting list of all searching results")
    public List<WebElement> getListOfAllProducts() {
        LOGGER.info(String.format("Get a list of all products found on request %s", productName));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LOGGER.info("Wait for a message about adding a product to the cart");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(iPhonesXpath)));
        allIphones = driver.findElements(By.xpath(iPhonesXpath));
        return allIphones;
    }
    @Step("Cheking if all results contain '{productName}'")
    public boolean getSearchedProduct() {
        int count = 0;
        allIphones = getListOfAllProducts();
        for (WebElement allIphone : allIphones) {
            if (allIphone.getText().contains("iPhone")) {
                count++;
            }
        }
        LOGGER.warn(String.format("Number of products in the list: %s.", allIphones.size()) + "Number of " + productName + "in this list: " + count);
        return count == allIphones.size();
    }


}
