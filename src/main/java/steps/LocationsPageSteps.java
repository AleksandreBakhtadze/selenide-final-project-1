package steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LocationsPage;
import utils.HelperFunctions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.*;

public class LocationsPageSteps {

    private final LocationsPage locationsPage = new LocationsPage();
    private final HelperFunctions helperFunctions = new HelperFunctions();

    public LocationsPageSteps validateMap(){
        sleep(3000);
        //validate map should be visible
        locationsPage.mapContainer.shouldHave(Condition.visible,Duration.ofSeconds(10));
        //all filter is selected by default
        locationsPage.allFilter.shouldBe(Condition.visible)
                .shouldHave(Condition.cssClass("active"));
        //locations are pined
        locationsPage.mapPins.shouldHave(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    public LocationsPageSteps validateFilters(){

        locationsPage.atmFilter.shouldBe(Condition.visible)
                .shouldNotHave(Condition.cssClass("active"));
        locationsPage.branchesFilter.shouldBe(Condition.visible)
                .shouldNotHave(Condition.cssClass("active"));
        locationsPage.subFilter27_4.shouldBe(Condition.visible);
        locationsPage.subFilterOpenNow.shouldBe(Condition.visible);

        return this;
    }
    public LocationsPageSteps clickAtmFilter(){
        //pins change
        int originalPinsCount = locationsPage.mapPins.size();
        locationsPage.atmFilter.click();
        locationsPage.atmFilter.shouldHave(Condition.cssClass("active"));
        locationsPage.mapPins.shouldHave(CollectionCondition.sizeNotEqual(originalPinsCount));
        //option list changes
        locationsPage.optionsList.forEach(option -> {
            option.shouldHave(Condition.text(" ბანკომატი "));
        });
        return this;
    }

    public LocationsPageSteps clickBranchFilter(){
        //pins change
        int originalPinsCount = locationsPage.mapPins.size();
        locationsPage.branchesFilter.click();
        locationsPage.branchesFilter.shouldHave(Condition.cssClass("active"));
        locationsPage.mapPins.shouldHave(CollectionCondition.sizeNotEqual(originalPinsCount));
        //option list changes
        locationsPage.optionsList.forEach(option -> {
            option.shouldNotHave(Condition.text(" ბანკომატი "));
        });
        return this;
    }
    public LocationsPageSteps clickSubFilter24_7(){
        locationsPage.subFilter27_4.click();
        sleep(3000);
        //check if it is highlighted
        locationsPage.subFilter27_4.$("span").shouldHave(Condition.anyOf(
                "should have blue background color",
                Condition.cssValue("background-color", "rgba(0, 173, 238, 1)"),  // Chrome
                Condition.cssValue("background-color", "rgb(0, 173, 238)")       // Firefox
        ));
        locationsPage.optionsList.forEach(option -> {
           option.shouldHave(Condition.text("24/7"));
        });
        //uncheck filter
        locationsPage.subFilter27_4.click();
        return this;
    }

    public LocationsPageSteps clickSubFilterOpenNow(){
        locationsPage.subFilterOpenNow.click();
        sleep(3000);
        //check if it is highlighted
        locationsPage.subFilterOpenNow.$("span").shouldHave(Condition.anyOf(
                "should have blue background color",
                Condition.cssValue("background-color", "rgba(0, 173, 238, 1)"),  // Chrome
                Condition.cssValue("background-color", "rgb(0, 173, 238)")       // Firefox
        ));        //check filtered list
        locationsPage.optionsList.forEach(option -> {
            String optionText = option.getText();
            assertTrue(helperFunctions.validateTime(optionText));
        });
        //uncheck filter
        locationsPage.subFilterOpenNow.click();

        return this;
    }

    public LocationsPageSteps tapZoomInAndZoomOut(){
        sleep(1500);
        actions().moveToElement(locationsPage.map).click().perform();
        locationsPage.mapPins.last().shouldBe(Condition.visible);
        int originalPinsCount = locationsPage.mapPins.size();

        locationsPage.map.sendKeys(Keys.ADD);
        locationsPage.mapPins.shouldHave(CollectionCondition.sizeNotEqual(originalPinsCount));
        locationsPage.map.sendKeys(Keys.SUBTRACT);
        sleep(1500);
        try {
            locationsPage.mapPins.shouldHave(CollectionCondition.size(originalPinsCount));
        } catch (AssertionError e) {
            int currentCount = locationsPage.mapPins.size();
            System.out.println("Zoom test failed: Expected " + originalPinsCount +
                    " pins, but found " + currentCount + " pins, map did not load correctly");
            throw e;
        }
        return this;
    }

    public LocationsPageSteps mapMoveSides(){
        actions().moveToElement(locationsPage.map).click().perform();
        sleep(1500);
        locationsPage.mapPins.last().shouldBe(Condition.visible);
        int originalPinsCount = locationsPage.mapPins.size();
        for(int i = 0 ; i < 100; i ++ )locationsPage.map.sendKeys(Keys.ARROW_LEFT);
        for(int i = 0 ; i < 100; i ++ )locationsPage.map.sendKeys(Keys.ARROW_RIGHT);
        for(int i = 0 ; i < 100; i ++ )locationsPage.map.sendKeys(Keys.ARROW_DOWN);
        for(int i = 0 ; i < 100; i ++ )locationsPage.map.sendKeys(Keys.ARROW_UP);
        sleep(1500);
        try {
            locationsPage.mapPins.shouldHave(CollectionCondition.size(originalPinsCount));
        } catch (AssertionError e) {
            int currentCount = locationsPage.mapPins.size();
            System.out.println("Map movement test failed: Expected " + originalPinsCount +
                    " pins, but found " + currentCount + " pins, map did not load correctly");
            throw e;
        }
        return this;
    }

}
