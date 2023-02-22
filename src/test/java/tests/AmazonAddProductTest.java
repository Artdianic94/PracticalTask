package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonAuthorizationPage;
import pages.AmazonSearchPage;


public class AmazonAddProductTest extends TestBase {
    AmazonAuthorizationPage amazonAuthorizationPage;
    AmazonSearchPage amazonSearchPage;
    AmazonAddToCartPage amazonAddToCartPage;
    String productName = "iPhone";

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        amazonSearchPage.getListOfAllProducts(productName);
    }
    @Tag("Amazon")
    @Test
    @Description(value = "The test checks that when user adds an item to Cart there" +
            " is a successful message with green tick and 1 is displayed on the Cart")
    public void checkCartTest() {
        amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonAddToCartPage.addProductToCart(productName);
        String actualProductAddMessage = amazonAddToCartPage.checkForAddingToCart();
        String actualNumberOnCart = amazonAddToCartPage.numberOnCart();
        Assertions.assertEquals("Added to Cart", actualProductAddMessage);
        Assertions.assertEquals("1", actualNumberOnCart);
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
