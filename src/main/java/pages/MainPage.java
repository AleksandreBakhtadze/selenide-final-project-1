package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    public SelenideElement forMe = $(".tbcx-pw-navigation-item").$(byText("ჩემთვის"));

    public SelenideElement cookiesAcceptButton = $(".tbcx-pw-cookie-consent .primary.size-m.state-initial");

    public SelenideElement locationsLink = $x("//span[contains(text(),'მისამართები')]");

    public SelenideElement productLink = $x("//span[contains(text(),'ფულადი გზავნილები')]");

    public SelenideElement slider = $("tbcx-pw-hero-slider");

    public SelenideElement sliderNextButton = $x("//button[contains(@class, 'tbcx-pw-hero-slider-section') and contains(@class, 'button_next')]");

    public SelenideElement getCurrentSlide() {
        return slider.$(".tbcx-pw-hero-slider-section__slide.ng-trigger-slideAnimation");
    }

    public ElementsCollection h1s = $$x("//h1");

    public SelenideElement mainTitle = $x("//div[contains(@class,'tbcx-pw-featured-text-section__content')]");

    public ElementsCollection sectionNextButtons =
            $$x("//button[@tbcxbutton and contains(@class, 'carousel-triggers--next')]");

    public ElementsCollection sectionBackButtons =
            $$x("//button[@tbcxbutton and contains(@class, 'carousel-triggers--prev')]");

    public ElementsCollection sections = $$x("//div[@class='tbcx-pw-section-with-title']");

    //Mobile elements

    public SelenideElement hamburgerMenuButton = $x("//div[contains(@class,'tbcx-pw-hamburger-menu')]//button");

    public ElementsCollection megaMenuMainNavigationItems = $$x("//div[contains(@class,'tbcx-pw-mega-menu-navigation__items')]//button");

    public ElementsCollection subNavigationItems = $$x("//tbcx-pw-mega-menu-sub-navigation[contains(@class,'ng-star-inserted')]//tbcx-pw-mega-menu-sub-group");

    public ElementsCollection navigationDropDownItems = $$x("//tbcx-pw-mega-menu-sub-navigation[contains(@class,'ng-star-inserted')]//tbcx-pw-mega-menu-sub-group//tbcx-pw-mega-menu-sub-item[contains(@class,'ng-tns-c2478649229-44')]");

    public SelenideElement header = $x("//div[contains(@class,'header-wrapper')]");

    public ElementsCollection keyCTAButtons = $$x("//tbcx-pw-button");


}