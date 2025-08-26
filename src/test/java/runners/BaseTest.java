package runners;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 30000;
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}