package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.AmazonAuthorizationPage;

public class AmazonAuthorizationTest extends BaseTest{
    AmazonAuthorizationPage amazonAuthorizationPage;
   @Test
    public void authorizationTest(){
        amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        String actualStringOnLogInBTN = amazonAuthorizationPage.getSignInResult();
        String expectedStringOnLogInBTN = "Hello, Diana";
        Assertions.assertEquals(expectedStringOnLogInBTN,actualStringOnLogInBTN);
    }
}
