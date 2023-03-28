package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.AmazonAuthorizationPage;
import utilities.CaptchaException;

@Tag("uiTest")
public class AmazonAuthorizationTest extends TestBase {

    @Test
    @Description(value = "The test checks for a greeting on the page for an authorized user")
    public void authorizationTest() throws CaptchaException {
        AmazonAuthorizationPage amazonAuthorizationPage = new AmazonAuthorizationPage(driver);
        String actualStringOnLogInBTN = amazonAuthorizationPage.getSignInResult();
        String expectedStringOnLogInBTN = "Hello, Diana";
        Assertions.assertEquals(expectedStringOnLogInBTN, actualStringOnLogInBTN, "Greeting inscription not found");
    }
}
