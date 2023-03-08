package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.AmazonProductPage;
import pages.AmazonSearchPage;

@Tag("uiTest")
public class AmazonAddProductTest extends TestBase {
    String productName = "iPhone";
    AmazonSearchPage amazonSearchPage;

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
        AmazonProductPage amazonProductPage = new AmazonProductPage(driver);
        amazonProductPage.addProductThatHasAddBtn(amazonSearchPage.getListOfSearchProduct(productName), productName);
        Assertions.assertEquals("Added to Cart", amazonProductPage.getTextFromMessage(), "Error adding the product to the Cart");
        Assertions.assertEquals("1", amazonProductPage.getNumberOnCart(), "The actual umber on the Cart is not one");
        Assertions.assertTrue(amazonProductPage.doesTickReportAboutSuccess(), "The tick picture is incorrect");
    }
}
