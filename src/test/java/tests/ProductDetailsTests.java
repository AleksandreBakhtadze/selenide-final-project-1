package tests;

import constans.DataSupplier;
import constans.TBCContants;
import org.testng.annotations.Test;
import runners.BaseTest;
import steps.MainPageSteps;
import steps.ProductPageSteps;

public class ProductDetailsTests extends BaseTest {

    private final MainPageSteps mainPageSteps = new MainPageSteps();
    private final ProductPageSteps productPageSteps = new ProductPageSteps();
    private final TBCContants tbcContants = new TBCContants();

    @Test(priority = 1)
    public void navigateToProductPageAndValidateTitle(){
        mainPageSteps.openMainPage().acceptCookies()
                .goToProductsPage();
        productPageSteps.validateTitle(tbcContants.productTitle);
    }

    @Test(priority = 2,dependsOnMethods = "navigateToProductPageAndValidateTitle")
    public void validatePrimaryCTAButtonsPresence(){
        productPageSteps.validateCTAButtonsPresence();
    }

    @Test(priority = 3,dataProvider = "currencies", dataProviderClass = DataSupplier.class,dependsOnMethods = "navigateToProductPageAndValidateTitle")
    public void validateSecondaryCTAButtonsAndLinks(String currency){
        productPageSteps.validateConverting(currency);
    }

    @Test(priority = 4,dependsOnMethods = "navigateToProductPageAndValidateTitle")
    public void validateExternalLinksAndCommissionCalculator(){
        productPageSteps.validateSwapButton()
                .secondaryLinksPresence()
                .openCommissionCalculator();

    }

    @Test(priority = 5,dataProvider = "countries", dataProviderClass = DataSupplier.class,dependsOnMethods = "navigateToProductPageAndValidateTitle")
    public void validateCommissionCalculationByCountry(String country){
        productPageSteps.validateCommission(country);
    }


    @Test(priority = 6 , dependsOnMethods = "navigateToProductPageAndValidateTitle")
    public void validateExternalLinksRedirection(){
        productPageSteps.clickOnExternalLink();
    }

    @Test(priority = 7 , dependsOnMethods = "navigateToProductPageAndValidateTitle")
    public void verifyProductInformationCompleteness(){
        productPageSteps.productOtherInformation();
    }

}
