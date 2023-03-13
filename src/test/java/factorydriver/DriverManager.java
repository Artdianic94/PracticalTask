package factorydriver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
    public WebDriver driver;

    public abstract void setUpDriver();

    public WebDriver getDriver() {
        return driver;
    }
}
