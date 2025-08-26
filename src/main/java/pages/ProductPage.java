package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class ProductPage {

    public final ElementsCollection titles = $$x("//h1");

    public final ElementsCollection CTAButtons = $$x("//button[contains(@class, 'primary') and contains(@class, 'size-l')]");

    public final SelenideElement popUpDetails = $x("//div[contains(@class, 'tbcx-pw-dialog__content-wrapper')]");
    public final SelenideElement closePopUpDetails = $x("//div[contains(@class, 'tbcx-pw-dialog__header-close')]//button");

    public final ElementsCollection additionalButtons = $$x("//div[contains(@class, 'tbcx-pw-tab-menu')]//button");

    public final SelenideElement currentExchange = $x("//div[contains(@class, 'tbcx-pw-exchange-rates-calculator') and contains(@class, 'description')]");
    public final ElementsCollection convertFields = $$x("//div[contains(@class, 'input-with-label')]//input[@type='number']");


    public final SelenideElement leftCurrencyDropdown = $x("//tbcx-dropdown-selector[@formcontrolname='iso1Currency']");
    public final SelenideElement rightCurrencyDropdown = $x("//tbcx-dropdown-selector[@formcontrolname='iso2Currency']");

    public final ElementsCollection currencyOptions = $$x("//div[contains(@class, 'tbcx-dropdown-popover-item__title')]");

    public final SelenideElement swapButton = $x("//div[contains(@class, 'tbcx-pw-exchange-rates-calculator__swap')]//button");

    public final SelenideElement transactionField = $x("//div[contains(@class, 'input-with-label')]//input[@id='tbcx-text-input-1']");

    public final ElementsCollection transactionDropDowns =
            $$x("//*[name()='tbcx-dropdown-selector']//div[contains(@class, 'tbcx-field')]");
    public final ElementsCollection transactionCountries = $$x("//div[@class='tbcx-item-list']//tbcx-dropdown-popover-item");

    // loaded cards (what you actually want)
    public final ElementsCollection loadedTransferCards =
            $$("tbcx-pw-money-transfer-system-card");

    // loading placeholders (skeletons)
    public final ElementsCollection loadingPlaceholders =
            $$("tbcx-pw-money-transfer-system-card-loading");

    public final ElementsCollection secondaryLinks = $$x("//div[contains(@class, 'pane-container')]//div[contains(@class,'tbcx-pw-carousel__card')]");

    public final SelenideElement externalLink = $x("//tbcx-pw-button[contains(@class, 'tbcx-pw-cta-section__info__button')]//a");

}


