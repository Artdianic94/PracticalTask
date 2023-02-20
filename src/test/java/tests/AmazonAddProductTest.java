package tests;

import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonAuthorizationPage;
import pages.AmazonSearchPage;

public class AmazonAddProductTest extends BaseTest {
    AmazonAuthorizationPage amazonAuthorizationPage;
    AmazonSearchPage amazonSearchPage;
    AmazonAddToCartPage amazonAddToCartPage;

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonSearchPage.sendSearchingText();
        amazonSearchPage.getListOfAllProducts();
    }

    @Test
    public void checkCartTest() {
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonAddToCartPage.addProductToCart();
        String actualProductAddMessage = amazonAddToCartPage.checkForAddingToCart();
        String actualNumberOnCart = amazonAddToCartPage.numberOnCart();
        Assertions.assertEquals("Added to Cart", actualProductAddMessage);
        Assertions.assertEquals("1",actualNumberOnCart);
        Assertions.assertTrue(amazonAddToCartPage.isTickIconGreen());
    }

    @AfterEach
    public void cleanData() {
        amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        amazonAuthorizationPage.openMainPage();
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonAddToCartPage.cleanCart();
    }
}
