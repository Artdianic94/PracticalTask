package tests;


import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonAuthorizationPage;
import pages.AmazonSearchPage;

@Tag("amazonProductInCartTest")
public class AmazonProductInCartTest extends BaseTest {
    AmazonSearchPage amazonSearchPage;
    AmazonAddToCartPage amazonAddToCartPage;
    String productName = "iPhone";

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        amazonAddToCartPage.addProductToCart(productName);
        amazonAddToCartPage.checkForAddingToCart();
    }

    @Test
    @Description(value = "The test checks that the Cart contains the added Phone")
    public void checkProductsInCartTest() {
        boolean actualProduct = amazonAddToCartPage.whatInCart();
        Assertions.assertTrue(actualProduct, "Cart doesn't contain added product");
    }

    @AfterEach
    public void cleanData() {
        AmazonAuthorizationPage amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        amazonAuthorizationPage.openMainPage();
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonAddToCartPage.cleanCart();
    }
}
