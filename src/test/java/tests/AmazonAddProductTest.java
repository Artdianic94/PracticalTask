package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonAddToCartPage;
import pages.AmazonSearchPage;

@Tag("uiTest")
public class AmazonAddProductTest extends TestBase {
    String productName = "iPhone";
    AmazonSearchPage amazonSearchPage;

    public void startBrowser() {
        super.setUp("remote");
    }

    @BeforeEach
    public void loginAndOpenProduct() {
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        amazonSearchPage.getListOfSearchProduct(productName);
    }

    @Test
    @Description(value = "The test checks that when user adds an item to Cart there" +
            " is a successful message with green tick and 1 is displayed on the Cart")
    public void checkCartTest() {
        String imageAreaWithGreenTick = "-84px -138px";
        AmazonAddToCartPage amazonAddToCartPage = new AmazonAddToCartPage(driver);
        amazonAddToCartPage.addProductThatHasAddBtn(amazonSearchPage.getListOfSearchProduct(productName));
        String actualProductAddMessage = amazonAddToCartPage.getTextFromMessage();
        String actualImageAreaWithTick = amazonAddToCartPage.getImageAreaWithTick();
        String actualNumberOnCart = amazonAddToCartPage.numberOnCart();
        Assertions.assertEquals("Added to Cart", actualProductAddMessage);
        Assertions.assertEquals("1", actualNumberOnCart);
        Assertions.assertEquals(imageAreaWithGreenTick, actualImageAreaWithTick, "The tick picture is incorrect");
    }
}
