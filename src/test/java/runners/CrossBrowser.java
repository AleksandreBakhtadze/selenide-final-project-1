package runners;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

public class CrossBrowser {
    WebDriver driver;

    @BeforeTest
    @Parameters({"browserType"})
    public void setUp(String browserType) {
        if (browserType.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browserType.equals("firefox")) {
            driver = new FirefoxDriver();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("general.smoothScroll", false);
        } else {
            driver = new EdgeDriver();
        }

        WebDriverRunner.setWebDriver(driver);

        driver.manage().window().maximize();

        Configuration.browser = browserType;

        Configuration.timeout = 10000;

    }


    @AfterTest
    public void afterTest() {
        System.out.println("=== [AfterTest] Closing browser ===");
        if (driver != null) {
            driver.quit();
        }
    }

}