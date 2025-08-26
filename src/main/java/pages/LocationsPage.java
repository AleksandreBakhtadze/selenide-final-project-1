package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class LocationsPage {

    public SelenideElement map = $x("//div[@role='region' and @aria-label='Map']");
    public SelenideElement mapContainer = $x("//div[@class='map-container']");
    public ElementsCollection mapPins = $$("gmp-advanced-marker");

    public SelenideElement allFilter = $x("//button[contains(@class,'tbcx-pw-tab-menu__item')]//span[text()='ყველა']/..");
    public SelenideElement atmFilter = $x("//button[contains(@class,'tbcx-pw-tab-menu__item')]//span[text()='ბანკომატები']/..");
    public SelenideElement branchesFilter = $x("//button[contains(@class,'tbcx-pw-tab-menu__item')]//span[text()='ფილიალები']/..");

    public SelenideElement subFilter27_4 = $$("label.tbcx-pw-chip")
            .findBy(Condition.text("24/7"));
    public SelenideElement subFilterOpenNow = $$("label.tbcx-pw-chip")
            .findBy(Condition.text(" ღიაა "));

    public ElementsCollection optionsList = $$("app-atm-branches-section-list-item");


}
