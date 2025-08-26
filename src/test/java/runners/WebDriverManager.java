package runners;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void initializeDriver() {
        if (driverThreadLocal.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            WebDriver driver = new ChromeDriver(options);
            driverThreadLocal.set(driver);
            WebDriverRunner.setWebDriver(driver);
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void closeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            WebDriverRunner.closeWebDriver();
        }
    }
}