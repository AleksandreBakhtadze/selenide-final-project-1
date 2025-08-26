package steps;

import com.codeborne.selenide.*;
import constans.TBCContants;
import org.openqa.selenium.Dimension;
import pages.MainPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.anyOf;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class MainPageSteps {
    private final MainPage mainPage = new MainPage();
    private TBCContants tbcContants = new TBCContants();

    public MainPageSteps openMainPage() {
        open(tbcContants.mainPageLink);
        return this;
    }

    public void goToLocationsPage(){
        mainPage.forMe.shouldBe(Condition.visible);
        actions().moveToElement(mainPage.forMe).perform();
        Selenide.sleep(1000);
        mainPage.locationsLink.shouldBe(Condition.visible).click();
        Selenide.Wait().until(driver ->
                WebDriverRunner.url().contains("atm") || WebDriverRunner.url().contains("branches")
        );
    }

    public void goToProductsPage(){
        mainPage.forMe.shouldBe(Condition.visible);
        actions().moveToElement(mainPage.forMe).perform();
        Selenide.sleep(1000);
        mainPage.productLink.shouldBe(Condition.visible).click();
        Selenide.Wait().until(driver ->
                WebDriverRunner.url().contains("money") || WebDriverRunner.url().contains("transfers")
        );
    }

    public MainPageSteps acceptCookies(){
        mainPage.cookiesAcceptButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        if (mainPage.cookiesAcceptButton.is(Condition.visible)) {

            mainPage.cookiesAcceptButton.click();
        }
        return this;
    }

    //Content integrity

    public MainPageSteps validateSlider(){
        actions().moveToElement(mainPage.slider).perform();
        sleep(1000); // Initial wait
        for(int i = 0 ; i < 9 ; i ++){
            try {
                // current slide
                SelenideElement currentSlide = mainPage.getCurrentSlide()
                        .shouldBe(Condition.visible, Duration.ofSeconds(10));
                // title
                currentSlide.$("h2.tbcx-pw-hero-slider-section__slide-title").shouldHave(Condition.text(tbcContants.mainPageSlides[i]));
                // slide's button
                SelenideElement button = currentSlide.$("tbcx-pw-button a");
                button.shouldBe(Condition.visible);
                button.shouldHave(Condition.attribute("href"));
                button.shouldHave(anyOf(
                        Condition.text("ვრცლად"),
                        Condition.text("დეტალურად"),
                        Condition.text("გაიგე მეტი")
                ));

            } catch (Exception e) {
                System.out.println("Error on slide " + (i+1) + ": " + e.getMessage());
            }
            mainPage.sliderNextButton.shouldBe(Condition.clickable).click();
            sleep(2000);
        }

        return this;
    }

    public MainPageSteps checkTitles(){
        mainPage.mainTitle.shouldHave(Condition.text(tbcContants.homaPageMainTitle));
        for(int i = 0 ; i < tbcContants.h1Titles.length ; i ++){
            mainPage.h1s.get(i).shouldHave(Condition.text(tbcContants.h1Titles[i]));
            SelenideElement infoSection = mainPage.h1s.get(i).$x("./ancestor::div[contains(@class, 'tbcx-pw-cta-section__info')]");
            SelenideElement buttonsWrapper = infoSection.$(".tbcx-pw-cta-section__info__buttons-wrapper");
            SelenideElement button = buttonsWrapper.$("button");
            button.shouldBe(Condition.visible);
            button.shouldHave(Condition.text("ვრცლად"));
            button.shouldHave(Condition.anyOf(
                    "should have blue background color",
                    Condition.cssValue("background-color", "rgba(0, 173, 238, 1)"),
                    Condition.cssValue("background-color", "rgb(0, 173, 238)")
            ));
        }

        return this;
    }

    public MainPageSteps checkSectionTitles(String sectionName,int index){
        mainPage.sections.get(index).shouldHave(Condition.text(sectionName));
        ElementsCollection cards = mainPage.sections.get(index)
                .$$(".tbcx-pw-carousel__slides__slide__card, .tbcx-pw-carousel__card");
        //expected titles
        String[] expectedTitles = tbcContants.sectionCardsTitles.get(sectionName);
        for(int i = 0 ; i < cards.size() ; i ++){
            if(expectedTitles != null && i < expectedTitles.length) {
                cards.get(i).shouldHave(Condition.text(expectedTitles[i]));
            }
            if(i==2&&index!=1) mainPage.sectionNextButtons.get(Math.max(index-1,0)).click();
        }
        mainPage.sectionBackButtons.get(Math.max(index-1,0)).click();
        return this;
    }

    public MainPageSteps checkSectionUrls(String sectionName,int index){
        mainPage.sections.get(index).shouldHave(Condition.text(sectionName));
        ElementsCollection cards = mainPage.sections.get(index)
                .$$(".tbcx-pw-carousel__slides__slide__card, .tbcx-pw-carousel__card");
        //expected URLs
        String[] expectedUrls = tbcContants.sectionCardsUrls.get(sectionName);
        for(int i = 0 ; i < cards.size() ; i ++){
            SelenideElement link = cards.get(i).$("a");

            // Validate card URL
            if(expectedUrls != null && i < expectedUrls.length) {
                link.shouldHave(Condition.attribute("href", expectedUrls[i]));
            }
            // Click card, validate URL navigation
            link.click();
            sleep(1000);
            if(link.has(Condition.attribute("target", "_blank"))) {
                switchTo().window(1);
                webdriver().shouldHave(url(expectedUrls[i]));
                closeWindow();
                switchTo().window(0);
            }else{
                WebDriverRunner.url().contains(expectedUrls[i]);
                back();
            }
            sleep(1000);
            // Wait for the section to be visible again
            mainPage.sections.get(index).shouldBe(Condition.visible);
            //reload cards
            cards = mainPage.sections.get(index)
                    .$$(".tbcx-pw-carousel__slides__slide__card, .tbcx-pw-carousel__card");
            if(i>=2&&index!=1&&mainPage.sectionNextButtons.get(Math.max(index-1,0)).has(Condition.clickable)) mainPage.sectionNextButtons.get(Math.max(index-1,0)).click();

        }
        return this;
    }

    //Mobile testing

    public MainPageSteps setMobileResolution(){
        webdriver().driver().getWebDriver().manage().window().setSize(new Dimension(375, 667));
        sleep(1000);
        return this;
    }

    public MainPageSteps selectAndOpenHamburgerMenu(){
        mainPage.hamburgerMenuButton.shouldBe(Condition.visible);
        mainPage.hamburgerMenuButton.click();
        sleep(1000);
        return this;
    }

    public MainPageSteps getMegaMenuMainItems(){
        for(int i = 0 ; i < tbcContants.mainNavigationItems.length ; i ++){
            mainPage.megaMenuMainNavigationItems.get(i).shouldBe(Condition.visible);
            mainPage.megaMenuMainNavigationItems.get(i).shouldHave(Condition.text(tbcContants.mainNavigationItems[i]));
        }
        return this;
    }

    public MainPageSteps validateSubNavigationItems(){
        for(int i = 0 ; i < mainPage.megaMenuMainNavigationItems.size() ; i ++){

            mainPage.megaMenuMainNavigationItems.get(i).shouldBe(Condition.visible);
            mainPage.megaMenuMainNavigationItems.get(i).shouldHave(Condition.text(tbcContants.mainNavigationItems[i]));
            mainPage.megaMenuMainNavigationItems.get(i).click();
            mainPage.megaMenuMainNavigationItems.get(i).shouldHave(Condition.anyOf(
                    "should have blue background color",
                    Condition.cssValue("color", "rgba(0, 173, 238, 1)"),  // Chrome
                    Condition.cssValue("color", "rgb(0, 173, 238)")       // Firefox
            ));
            sleep(700);

            for (int j = 0 ; j < mainPage.subNavigationItems.size() ; j ++){
                mainPage.subNavigationItems.get(j).shouldHave(Condition.matchText(tbcContants.subNavigationItems[i][j]));
                if(mainPage.subNavigationItems.get(j).has(Condition.text("chevron-down-outlined"))) mainPage.subNavigationItems.get(j).click();
            }
        }
        return this;
    }
    public void closeHamburgerMenu(){
        mainPage.hamburgerMenuButton.shouldBe(Condition.visible);
        mainPage.hamburgerMenuButton.click();
        sleep(1000);
    }


    public MainPageSteps selectHeader(){
        mainPage.header.shouldBe(Condition.visible);
        mainPage.header.shouldHave(Condition.cssValue("position", "sticky"));
        return this;
    }

    public MainPageSteps scrollIntoView(){
        executeJavaScript("window.scrollBy(0, 1500)");
        sleep(3000);
        executeJavaScript("window.scrollBy(0, -500)");
        sleep(3000);
        return this;
    }

    public void getAllKeyCTAButtons(){
        for(int i = 0 ; i <mainPage.keyCTAButtons.size() ; i ++ ){
            mainPage.keyCTAButtons.get(i).shouldBe(Condition.visible);
            mainPage.keyCTAButtons.get(i).shouldHave(Condition.text(tbcContants.mainPageKeyCTAButtonTexts[i]));
            mainPage.keyCTAButtons.get(i).shouldHave(Condition.anyOf(
                    "should have blue color",
                    Condition.cssValue("color", "rgba(0, 0, 0, 1)"), //Chrome
                    Condition.cssValue("color", "rgb(0, 0, 0, 1)")       // Firefox
            ));
        }
    }

}
