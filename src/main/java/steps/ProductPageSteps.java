package steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import constans.TBCContants;
import pages.ProductPage;
import utils.HelperFunctions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class ProductPageSteps {
    private final ProductPage productPage = new ProductPage();
    private final HelperFunctions helperFunctions = new HelperFunctions();
    private final TBCContants tbcContants = new TBCContants();

    public ProductPageSteps validateTitle(String title) {
        productPage.titles.get(0).shouldHave(Condition.text(title));
        return this;
    }

    public ProductPageSteps validateCTAButtonsPresence(){
        productPage.CTAButtons.forEach(button ->{
            button.shouldHave(Condition.anyOf(
                    "should have blue background color",
                    Condition.cssValue("background-color", "rgba(0, 173, 238, 1)"),  // Chrome
                    Condition.cssValue("background-color", "rgb(0, 173, 238)")       // Firefox
            ));
            button.shouldHave(Condition.and("should be available",Condition.clickable,Condition.enabled,Condition.text("დეტალურად")));
        });
        //second button has external link
        productPage.CTAButtons.get(1).click();
        productPage.popUpDetails.shouldBe(Condition.visible);
        productPage.closePopUpDetails.shouldBe(Condition.clickable).click();
        sleep(200);
        return this;
    }

    public ProductPageSteps validateConverting(String currency){
        productPage.additionalButtons.get(0).shouldHave(Condition.text("გზავნილის კონვერტაცია"));
        productPage.additionalButtons.get(0).shouldHave(Condition.cssClass("active"));
        productPage.leftCurrencyDropdown.click();

        productPage.currencyOptions.filterBy(Condition.text(currency)).first().click();

        for(int i =0; i <tbcContants.currencies.length ;i++) {
            productPage.rightCurrencyDropdown.click();

            productPage.currencyOptions.filterBy(Condition.text(tbcContants.currencies[i])).first().click();

            sleep(1000);

            double exchange = helperFunctions.getExchangeRate(productPage.currentExchange.getText());
            productPage.convertFields.get(0).clear();
            productPage.convertFields.get(0).setValue("100");

            String expectedValue = helperFunctions.formatExpectedValue(100 * exchange);
            productPage.convertFields.get(1).shouldHave(Condition.value(expectedValue));
        }

        return this;
    }
    public ProductPageSteps validateSwapButton(){
        String left = productPage.leftCurrencyDropdown.getText();
        String right = productPage.rightCurrencyDropdown.getText();
        productPage.swapButton.shouldBe(Condition.clickable).click();
        productPage.leftCurrencyDropdown.shouldHave(Condition.text(right));
        productPage.rightCurrencyDropdown.shouldHave(Condition.text(left));
        return this;
    }

    public ProductPageSteps openCommissionCalculator(){
        productPage.additionalButtons.get(1).shouldHave(Condition.text("გზავნილის გაგზავნის საკომისიო"));
        productPage.additionalButtons.get(1).click();
        productPage.additionalButtons.get(1).shouldHave(Condition.cssClass("active"));
        return this;
    }

    public ProductPageSteps validateCommission(String countryName){

        productPage.transactionDropDowns.get(0).click();
        productPage.currencyOptions.filterBy(Condition.text("USD")).first().click();

        productPage.transactionField.setValue("100");

        productPage.transactionDropDowns.get(1).click();
        productPage.transactionCountries.filterBy(Condition.text(countryName)).first().click();

        sleep(1500);
        // Wait until loaders (if any) disappear
        productPage.loadingPlaceholders.shouldHave(CollectionCondition.size(0));

        // Then wait for real cards to appear size must be more than 7 , 7 cards are default displayed
        productPage.loadedTransferCards.shouldHave(CollectionCondition.sizeGreaterThan(7));
        productPage.loadedTransferCards.last().shouldBe(Condition.visible);
        for(int i = 7;i<productPage.loadedTransferCards.size();i++) {
            productPage.loadedTransferCards.get(i).shouldHave(Condition.text(tbcContants.countriesCommissions.get(countryName)[i-7]));
        }
        return this;
    }

    public ProductPageSteps secondaryLinksPresence(){
        productPage.secondaryLinks.forEach(link ->{
            link.shouldBe(Condition.clickable);
        });
        for(int i = 0 ; i < productPage.secondaryLinks.size() ; i ++){
            productPage.secondaryLinks.get(i).shouldHave(Condition.text(tbcContants.productSecondaryTitles[i]));
        }
        return this;
    }

    public ProductPageSteps clickOnExternalLink(){
        productPage.externalLink.shouldBe(Condition.visible);
        productPage.externalLink.shouldHave(Condition.attribute("target", "_blank"));
        actions().moveToElement(productPage.externalLink).perform();
        productPage.externalLink.shouldBe(Condition.clickable).click();
        switchTo().window(1);

        // Validate URL (Selenide way)
        webdriver().shouldHave(url("https://tbconline.ge/tbcrd/login"));

        // Close tab and switch back (Selenide way)
        closeWindow();
        switchTo().window(0);
        return this;
    }

    public void productOtherInformation(){
        for(int i = 0 ; i < tbcContants.transferSystems.length ; i ++){
            productPage.loadedTransferCards.get(i).shouldHave(Condition.text(tbcContants.transferSystems[i]));
        }
    }

}
