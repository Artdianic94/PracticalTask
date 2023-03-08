package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pages.AmazonSearchPage;

public class AmazonSearchProductsTest extends TestBase {
    String productName = "iPhone";

    @Test
    @Description(value = "The test checks that only searched iPhones are displayed on the page")
    public void searchProductsTest() {
        AmazonSearchPage amazonSearchPage = new AmazonSearchPage(driver);
        amazonSearchPage.sendSearchingText(productName);
        boolean onlyIphoneOnPage = amazonSearchPage.isSearchedProductInEachItemOnPage(productName);
        Assertions.assertTrue(onlyIphoneOnPage, "The page with the founded products is incorrect");
    }
}
