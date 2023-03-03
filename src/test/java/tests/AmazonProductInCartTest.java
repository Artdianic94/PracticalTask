package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonSearchPage;

public class AmazonProductInCartTest extends TestBase {
    AmazonSearchPage amazonSearchPage;
    AmazonAddToCartPage amazonAddToCartPage;
    String productName = "iPhone";
    String actualProductThatWasAdded;

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        actualProductThatWasAdded = amazonAddToCartPage.addProductThatHasAddBtn(amazonSearchPage.getListOfSearchProduct(productName));
        amazonAddToCartPage.getTextFromMessage();
    }

    @Test
    @Description(value = "The test checks that the Cart contains the added IPhone")
    public void checkProductsInCartTest() {
        Assertions.assertTrue(amazonAddToCartPage.doesCartContainSelectedProduct(actualProductThatWasAdded), "Cart doesn't contain added product");
    }
}
