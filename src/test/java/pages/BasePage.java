package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open page {url}")
    public void openPage(String url) {
        driver.get(url);
    }
}
