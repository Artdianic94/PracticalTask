package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open main page {amazonUrl}")
    public void openMainPage(String amazonUrl) {
        driver.get(amazonUrl);
    }
}
