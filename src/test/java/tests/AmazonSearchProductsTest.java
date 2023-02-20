package tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.AmazonSearchPage;

public class AmazonSearchProductsTest extends BaseTest {
    AmazonSearchPage amazonSearchPage;

    @Test
    public void searchProductsTest(){
        amazonSearchPage = new AmazonSearchPage(driver);
        amazonSearchPage.sendSearchingText();
        boolean onlyIphoneOnPage = amazonSearchPage.getSearchedProduct();
        Assertions.assertTrue(onlyIphoneOnPage);
    }

}
