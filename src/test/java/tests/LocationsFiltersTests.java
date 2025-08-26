package tests;

import jdk.jfr.Description;
import org.testng.annotations.Test;
import runners.BaseTest;
import steps.LocationsPageSteps;
import steps.MainPageSteps;

@Description("FP1-T2")
public class LocationsFiltersTests extends BaseTest {

    private final MainPageSteps mainPageSteps = new MainPageSteps();
    private final LocationsPageSteps locationsPageSteps = new LocationsPageSteps();


    @Test(priority = 1)
    public void navigateToLocationsPageAndValidateDefaultTabsAndFilters() {
        mainPageSteps.openMainPage().acceptCookies()
                     .goToLocationsPage();
        locationsPageSteps.validateMap()
                          .validateFilters();
    }

    @Test(priority = 2,dependsOnMethods = "navigateToLocationsPageAndValidateDefaultTabsAndFilters")
    public void validateTabSwitchingBetweenATMAndBranches() {
        locationsPageSteps.clickAtmFilter()
                          .clickBranchFilter();
    }
    @Test(priority = 3,dependsOnMethods = "navigateToLocationsPageAndValidateDefaultTabsAndFilters")
    public void validateSubFilterFunctionalityFor24_7Locations() {
        locationsPageSteps.clickSubFilter24_7();
    }

    @Test(priority = 4,dependsOnMethods = "navigateToLocationsPageAndValidateDefaultTabsAndFilters")
    public void validateSubFilterFunctionalityForOpenNowLocations() {
        locationsPageSteps.clickSubFilterOpenNow();
    }

}
