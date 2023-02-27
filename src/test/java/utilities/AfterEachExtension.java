package utilities;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.Optional;

public class AfterEachExtension implements AfterEachCallback {
    private WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            saveUrlAndScreenShot();
        }
        driver.quit();
    }

    public void saveUrlAndScreenShot() {
        String currentURL = driver.getCurrentUrl();
        Allure.addAttachment("URL","text/uri-list", currentURL);

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
}