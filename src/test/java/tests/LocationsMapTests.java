package tests;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import runners.BaseTest;
import steps.LocationsPageSteps;
import steps.MainPageSteps;

public class LocationsMapTests  extends BaseTest {

    private final MainPageSteps mainPageSteps = new MainPageSteps();
    private final LocationsPageSteps locationsPageSteps = new LocationsPageSteps();

    @Test(priority = 1)
    public void navigateToLocationsPageFromMainNavigation() {
        mainPageSteps.openMainPage().acceptCookies()
                     .goToLocationsPage();
    }
    @Test(priority = 2,dependsOnMethods = "navigateToLocationsPageFromMainNavigation")
    public void validateLocationsMapLoadingAndDefaultState() {
        locationsPageSteps.validateMap();
    }
    @Test(priority = 3,dependsOnMethods = "navigateToLocationsPageFromMainNavigation")
    public void validateMapPanningFunctionalityInAllDirections() {
        locationsPageSteps.mapMoveSides();
    }
    @Test(priority = 4,dependsOnMethods = "navigateToLocationsPageFromMainNavigation")
    public void validateMapZoomInAndZoomOutControls() {
        locationsPageSteps.tapZoomInAndZoomOut();
    }

}
