package utilities;


import java.util.Optional;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;


public class ScreenshotWatcher implements TestWatcher {

    private static WebDriver driver;

    public static void setDriver(WebDriver driver) {
        ScreenshotWatcher.driver = driver;
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable throwable) {

    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> optional) {
        // do something
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        saveUrlAndScreenShot();
    }

    public void saveUrlAndScreenShot() {
        String currentURL = driver.getCurrentUrl();
        Allure.addAttachment("URL", currentURL);

        getScreenshotBytes(driver).ifPresent(bytes -> Allure.getLifecycle()
                .addAttachment("Screenshot", "image/png", "png", bytes));
    }

    private Optional<byte[]> getScreenshotBytes(WebDriver driver) {
        try {
            return Optional.of(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (WebDriverException e) {
            return Optional.empty();
        }
    }


    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        // do something
    }

}

