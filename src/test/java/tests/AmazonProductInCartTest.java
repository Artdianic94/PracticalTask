package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonSearchPage;

public class AmazonProductInCartTest extends TestBase {
    AmazonSearchPage amazonSearchPage;
    AmazonAddToCartPage amazonAddToCartPage;
    String productName = "iPhone";

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        amazonAddToCartPage.addProductThatHasAddBtn(amazonSearchPage.getListOfSearchProduct(productName));
        amazonAddToCartPage.getTextFromMessage();
    }

    @Test
    @Description(value = "The test checks that the Cart contains the added Phone")
    public void checkProductsInCartTest() {
        boolean actualProduct = amazonAddToCartPage.doesCartContainSelectedProduct();
        Assertions.assertTrue(actualProduct, "Cart doesn't contain added product");
    }
}
