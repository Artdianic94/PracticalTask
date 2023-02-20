package tests;


import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonAuthorizationPage;
import pages.AmazonSearchPage;

public class AmazonProductInCartTest extends BaseTest {
    AmazonSearchPage amazonSearchPage;
    AmazonAddToCartPage amazonAddToCartPage;

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonSearchPage.sendSearchingText();
        amazonAddToCartPage.addProductToCart();
        amazonAddToCartPage.checkForAddingToCart();
    }

    @Test
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
