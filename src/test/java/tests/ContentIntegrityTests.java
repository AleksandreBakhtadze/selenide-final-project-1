package tests;

import constans.DataSupplier;
import jdk.jfr.Description;
import steps.MainPageSteps;
import org.testng.annotations.Test;
import runners.BaseTest;

@Description("FP1-T4")
public class ContentIntegrityTests extends BaseTest {

    private final MainPageSteps mainPageSteps = new MainPageSteps();

    @Test(priority = 1)
    public void navigateToMainPageAndValidatePageTitles(){
        mainPageSteps.openMainPage().acceptCookies().checkTitles();

    }

    @Test(priority = 2,dependsOnMethods = "navigateToMainPageAndValidatePageTitles")
    public void validateSliderActionButtonsPresence(){
        mainPageSteps.validateSlider();

    }

    @Test(priority = 3,dataProvider = "sections", dataProviderClass = DataSupplier.class,dependsOnMethods = "navigateToMainPageAndValidatePageTitles")
    public void validateSectionContentForDuplicateHeadingsAndTextBlocks(String sectionName,int index){
        mainPageSteps.checkSectionTitles(sectionName,index)
                .checkSectionUrls(sectionName,index);
    }
}
