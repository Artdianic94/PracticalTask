package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonProductPage;
import pages.AmazonSearchPage;

@Tag("uiTestCart")
public class AmazonProductInCartTest extends TestBase {
    AmazonSearchPage amazonSearchPage;
    AmazonProductPage amazonProductPage;
    String productName = "iPhone";
    String actualProductThatWasAdded;

    public void startBrowser() {
        super.setUp("chrome");
    }

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonProductPage = new AmazonProductPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        actualProductThatWasAdded = amazonProductPage.addProductThatHasAddBtn(amazonSearchPage.getListOfSearchProduct(productName), productName);
        amazonProductPage.getTextFromMessage();
    }

    @Test
    @Description(value = "The test checks that the Cart contains the added IPhone")
    public void checkProductsInCartTest() {
        Assertions.assertTrue(amazonProductPage.doesCartContainSelectedProduct(actualProductThatWasAdded), "Cart doesn't contain added product");
    }
}
