package tests;

import com.codeborne.selenide.WebDriverRunner;
import constans.TBCContants;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import runners.BaseTest;
import steps.MainPageSteps;

import static com.codeborne.selenide.Selenide.open;

@Description("FP1-T5")
public class MobileSpecificTests extends BaseTest {

    private final MainPageSteps mainPageSteps = new MainPageSteps();
    private TBCContants tbcContants = new TBCContants();


    @Test(priority = 1)
    public void setupMobileResolutionAndNavigateToPage(){
        mainPageSteps.openMainPage().acceptCookies().setMobileResolution();

    }


    @Test(priority = 2,dependsOnMethods = "setupMobileResolutionAndNavigateToPage")
    public void validateHamburgerMenuNavigationAndSubItems(){
        if (!WebDriverRunner.hasWebDriverStarted()) {
            com.codeborne.selenide.Selenide.closeWebDriver();
            open(tbcContants.mainPageLink);
            mainPageSteps.setMobileResolution();
        }
        mainPageSteps.selectAndOpenHamburgerMenu()
                .getMegaMenuMainItems()
                .validateSubNavigationItems()
                .closeHamburgerMenu();
    }

    @Test(priority = 3,dependsOnMethods = "setupMobileResolutionAndNavigateToPage")
    public void validateStickyHeaderAndHamburgerMenuFunctionality(){
        mainPageSteps.selectHeader()
                .scrollIntoView()
                .selectHeader()
                .selectAndOpenHamburgerMenu()
                .closeHamburgerMenu();
    }

    @Test(priority = 4,dependsOnMethods = "setupMobileResolutionAndNavigateToPage")
    public void validateKeyCTAButtonsVisibilityInMobileViewport(){
        mainPageSteps.getAllKeyCTAButtons();
    }

}
